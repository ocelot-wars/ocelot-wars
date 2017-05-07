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
	private static final int DEFAULT_MINIMAL_PLAYER_COUNT = 1;

	private GameRunner gameRunner;
	private HttpClient httpClient;
	private ScheduledExecutorService executor;
	private Runnable waitForPlayersRunnable;

	private List<Player> participatingPlayers = new ArrayList<>();
	private List<Player> registeredPlayers = new ArrayList<>();
	private int minimalPlayerCount = DEFAULT_MINIMAL_PLAYER_COUNT;

	protected GameInviter(HttpClient client, Runnable waitForPlayersRunnable, ScheduledExecutorService executor, GameRunner gameRunner) {
		this(client, waitForPlayersRunnable);
		this.executor = executor;
		this.gameRunner = gameRunner;
	}

	public GameInviter(HttpClient client, Runnable waitForPlayersRunnable) {
		this.httpClient = client;
		this.waitForPlayersRunnable = waitForPlayersRunnable;
		executor = Executors.newScheduledThreadPool(1);
		gameRunner = new GameRunner();
	}

	public void inviteToGame(List<Player> registeredPlayers) {
		// debugging-output
		System.out.println("invitation started");
		this.registeredPlayers = new ArrayList<>(registeredPlayers);
		participatingPlayers = new ArrayList<>();
		registeredPlayers.stream().forEach(player -> {
			httpClient
				.post(player.getPort(), player.getHost(), "/invite", this::invitationResponse).end();
		});
		executor.schedule(this::checkStartGame, 5, TimeUnit.SECONDS);
	}

	protected void checkStartGame() {
		// debugging-output
		System.out.println("participating players: " + participatingPlayers.size());
		if (participatingPlayers.size() >= minimalPlayerCount) {
			gameRunner.start(participatingPlayers);
		} else {
			waitForPlayersRunnable.run();
		}
	}

	protected void invitationResponse(HttpClientResponse response) {
		if (response.statusCode() == OK.code() && response.statusMessage().equals(ACCEPT)) {
			SocketAddress remoteAddress = response.netSocket().remoteAddress();
			Optional<Player> player = getPlayerByAddress(remoteAddress);
			if (player.isPresent()) {
				// TODO: überprüfen, dass der Player nicht schon da ist
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

	protected List<Player> getParticipatingPlayers() {
		return participatingPlayers;
	}

	protected void setMinimalPlayerCount(int minimalPlayerCount) {
		this.minimalPlayerCount = minimalPlayerCount;
	}

}
