package com.github.ocelotwars.service;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;
import java.util.concurrent.ScheduledFuture;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.runners.MockitoJUnitRunner;

import com.github.ocelotwars.engine.Player;

import io.vertx.ext.web.RoutingContext;

@RunWith(MockitoJUnitRunner.class)
public class HostTest {
	private static final String CLIENT_NAME = "player1";
	private static final String CLIENT_HOST = "player1Url";
	private static final int CLIENT_PORT = 8080;

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Mock
	private GameInviter gameInviter = Mockito.mock(GameInviter.class);

	@Mock
	private ScheduledFuture<?> waitForPlayersJob = Mockito.mock(ScheduledFuture.class);

	@InjectMocks
	private Host host;

	@Test
	public void testRegisterPlayer() {
		RoutingContext context = createRoutingContext(CLIENT_NAME, CLIENT_HOST, CLIENT_PORT);
		host.register(context);

		assertThat(host.getPlayers(), contains(new Player(CLIENT_NAME, CLIENT_HOST, CLIENT_PORT)));
	}

	@Test
	public void testRegisterMultiplePlayers() {
		host.register(createRoutingContext(CLIENT_NAME, CLIENT_HOST, CLIENT_PORT));
		host.register(createRoutingContext("player2Url", "player2", 8080));

		List<Player> players = host.getPlayers();
		assertThat(players, containsInAnyOrder(new Player(CLIENT_NAME, CLIENT_HOST, CLIENT_PORT),
				new Player("player2Url", "player2", 8080)));
	}

	@Test
	public void testRegisterPlayerPreventsDuplicates() {
		host.register(createRoutingContext(CLIENT_NAME, CLIENT_HOST, CLIENT_PORT));
		host.register(createRoutingContext(CLIENT_NAME, CLIENT_HOST, CLIENT_PORT));

		assertThat(host.getPlayers(), contains(new Player(CLIENT_NAME, CLIENT_HOST, CLIENT_PORT)));
	}

	@Test
	public void testRegisterPlayerAllowsNameChange() {
		host.register(createRoutingContext(CLIENT_NAME, CLIENT_HOST, CLIENT_PORT));
		host.register(createRoutingContext("other name", CLIENT_HOST, CLIENT_PORT));

		assertThat(host.getPlayers(), contains(new Player("other name", CLIENT_HOST, CLIENT_PORT)));
	}

	private RoutingContext createRoutingContext(String clientName, String clientHost, int clientPort) {
		RoutingContext context = Mockito.mock(RoutingContext.class, Mockito.RETURNS_DEEP_STUBS);
		when(context.request().getParam("name")).thenReturn(clientName);
		when(context.request().remoteAddress().host()).thenReturn(clientHost);
		when(context.request().getParam("port")).thenReturn(String.valueOf(clientPort));
		return context;
	}

	@Test
	public void testCheckForEnoughPlayers_notEnoughPlayers() throws Exception {
		host.checkForEnoughPlayers();

		Mockito.verifyZeroInteractions(gameInviter, waitForPlayersJob);
	}

	@Test
	public void testCheckForEnoughPlayers_enoughPlayers() throws Exception {
		host.register(createRoutingContext(CLIENT_NAME, CLIENT_HOST, CLIENT_PORT));

		host.checkForEnoughPlayers();

		verify(gameInviter).inviteToGame(Mockito.anyListOf(Player.class));
		verify(waitForPlayersJob).cancel(false);
	}

}
