package com.github.ocelotwars.engine;

import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class HostTest {
    private static final String CLIENT_NAME = "player1";
    private static final String CLIENT_URL = "player1Url";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Host host = new Host();

    @Test
    public void registerPlayer() {
        Player player = host.registerPlayer(new PlayerRegistration(CLIENT_URL, CLIENT_NAME));

        assertThat(player, is(notNullValue()));
        assertThat(player.getUrl(), is(CLIENT_URL));
        assertThat(player.getName(), is(CLIENT_NAME));
    }

    @Test
    public void getPlayers() {
        Player player1 = host.registerPlayer(new PlayerRegistration(CLIENT_URL, CLIENT_NAME));
        Player player2 = host.registerPlayer(new PlayerRegistration("player2Url", "player2"));

        List<Player> players = host.getPlayers();
        assertThat(players, containsInAnyOrder(player1, player2));
    }

    @Test
    public void registerPlayerPreventsDuplicateClientUrl() {
        host.registerPlayer(new PlayerRegistration(CLIENT_URL, CLIENT_NAME));

        thrown.expect(DuplicatePlayerException.class);
        host.registerPlayer(new PlayerRegistration(CLIENT_URL, "newPlayerName"));
    }
}
