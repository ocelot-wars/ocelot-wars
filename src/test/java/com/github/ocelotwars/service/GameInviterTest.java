package com.github.ocelotwars.service;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import com.github.ocelotwars.engine.Player;

import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientRequest;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.net.impl.SocketAddressImpl;

@RunWith(MockitoJUnitRunner.class)
public class GameInviterTest {

	private static final String OTHER_HOST = "other_host";
	private static final long TIMEOUT_WAIT_FOR_INVITATIONS = 5L;
	private static final String NAME = "name";
	private static final String HOST = "host";
	private static final int PORT = 1234;

	@Mock
	private HttpClient httpClient;

	@Mock
	private ScheduledExecutorService executor;

	@Mock
	private Runnable waitForPlayersRunnable;

	@Mock
	private GameRunner gameRunner;

	private GameInviter gameInviter;

	@Before
	public void setUp() {
		gameInviter = new GameInviter(httpClient, waitForPlayersRunnable, executor, gameRunner);
	}

	@Test
	public void testInviteToGame() throws Exception {
		Player player = new Player(NAME, HOST, PORT);
		HttpClientRequest httpClientRequest = setupMocksHttpClient();

		gameInviter.inviteToGame(asList(player));

		verify(httpClient).post(Mockito.eq(PORT), Mockito.eq(HOST), Mockito.eq("/invite"), Mockito.any());
		verify(httpClientRequest).end();
		verify(executor).schedule(Mockito.any(Runnable.class), Mockito.eq(TIMEOUT_WAIT_FOR_INVITATIONS), Mockito.eq(TimeUnit.SECONDS));
	}

	private HttpClientRequest setupMocksHttpClient() {
		HttpClientRequest httpClientRequest = Mockito.mock(HttpClientRequest.class);
		when(httpClient.post(Mockito.eq(PORT), Mockito.eq(HOST), Mockito.eq("/invite"), Mockito.any())).thenReturn(httpClientRequest);
		return httpClientRequest;
	}

	@Test
	public void testInvitationResponse_wrongResponseCode() throws Exception {
		HttpClientResponse httpClientResponse = Mockito.mock(HttpClientResponse.class);
		when(httpClientResponse.statusCode()).thenReturn(404);

		gameInviter.invitationResponse(httpClientResponse);

		assertThat(gameInviter.getParticipatingPlayers(), Matchers.is(Matchers.empty()));
	}

	@Test
	public void testInvitationResponse_wrongResponseMessage() throws Exception {
		HttpClientResponse httpClientResponse = Mockito.mock(HttpClientResponse.class);
		when(httpClientResponse.statusCode()).thenReturn(200);
		when(httpClientResponse.statusMessage()).thenReturn("wrong");

		gameInviter.invitationResponse(httpClientResponse);

		assertThat(gameInviter.getParticipatingPlayers(), Matchers.is(Matchers.empty()));
	}

	@Test
	public void testInvitationResponse_playerNotFound() throws Exception {
		HttpClientResponse httpClientResponse = Mockito.mock(HttpClientResponse.class, Mockito.RETURNS_DEEP_STUBS);
		when(httpClientResponse.statusCode()).thenReturn(200);
		when(httpClientResponse.statusMessage()).thenReturn("accept");
		when(httpClientResponse.netSocket().remoteAddress()).thenReturn(new SocketAddressImpl(PORT, OTHER_HOST));

		gameInviter.invitationResponse(httpClientResponse);

		assertThat(gameInviter.getParticipatingPlayers(), Matchers.is(Matchers.empty()));
	}

	@Test
	public void testInvitationResponse_playerFound() throws Exception {
		Player player = new Player(NAME, HOST, PORT);
		setupMocksHttpClient();
		gameInviter.inviteToGame(asList(player));

		HttpClientResponse httpClientResponse = Mockito.mock(HttpClientResponse.class, Mockito.RETURNS_DEEP_STUBS);
		when(httpClientResponse.statusCode()).thenReturn(200);
		when(httpClientResponse.statusMessage()).thenReturn("accept");
		when(httpClientResponse.netSocket().remoteAddress()).thenReturn(new SocketAddressImpl(PORT, HOST));

		gameInviter.invitationResponse(httpClientResponse);

		assertThat(gameInviter.getParticipatingPlayers(), Matchers.containsInAnyOrder(player));
	}

	@Test
	public void testCheckStartGame_notEnoughPlayers() throws Exception {
		gameInviter.setMinimalPlayerCount(1);

		gameInviter.checkStartGame();

		verify(waitForPlayersRunnable).run();
	}

	@Test
	public void testCheckStartGame_enoughPlayers() throws Exception {
		gameInviter.setMinimalPlayerCount(0);

		gameInviter.checkStartGame();

		verify(gameRunner).start(Collections.emptyList());
		verifyZeroInteractions(waitForPlayersRunnable);
	}

}
