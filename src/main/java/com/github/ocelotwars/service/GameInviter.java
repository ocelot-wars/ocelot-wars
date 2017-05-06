package com.github.ocelotwars.service;

import static io.netty.handler.codec.http.HttpResponseStatus.OK;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.github.ocelotwars.engine.Player;

import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.net.SocketAddress;

public class GameInviter {
	private static final String ACCEPT = "accept";
	private static final int MINIMAL_PLAYER_COUNT = 1;
	private HttpClient httpClient;
	private List<Player> participatingPlayers;
	private ScheduledExecutorService executor;
	private List<Player> registeredPlayers;
	private Runnable waitForPlayersRunnable;

	public GameInviter(HttpClient client, Runnable waitForPlayersRunnable) {
		this.httpClient = client;
		this.waitForPlayersRunnable = waitForPlayersRunnable;
		executor = Executors.newScheduledThreadPool(1);
	}

	public void inviteToGame(List<Player> registeredPlayers) {
		// debugging-output
		System.out.println("invitation started");
		this.registeredPlayers = new ArrayList<>(registeredPlayers);
		participatingPlayers = new ArrayList<>();
		registeredPlayers.stream().forEach(player -> httpClient
				.post(player.getPort(), player.getHost(), "/invite", this::invitationResponse).end());
		executor.schedule(this::checkStartGame, 5, TimeUnit.SECONDS);
	}

	private void checkStartGame() {
		// debugging-output
		System.out.println("participating players: " + participatingPlayers.size());
		if (participatingPlayers.size() > MINIMAL_PLAYER_COUNT) {
			new GameRunner(participatingPlayers).start();
		} else {
			waitForPlayersRunnable.run();
		}
	}

	private void invitationResponse(HttpClientResponse response) {
		if (response.statusCode() == OK.code() && response.statusMessage().equals(ACCEPT)) {
			SocketAddress remoteAddress = response.netSocket().remoteAddress();
			Optional<Player> player = getPlayerByAddress(remoteAddress);
			if (player.isPresent()) {
				participatingPlayers.add(player.get());
			}
		}
	}

	private Optional<Player> getPlayerByAddress(SocketAddress remoteAddress) {
		String playerHost = remoteAddress.host();
		int playerPort = remoteAddress.port();
		return registeredPlayers.stream()
				.filter(player -> player.getHost().equals(playerHost) && player.getPort() == playerPort).findFirst();
	}

}
