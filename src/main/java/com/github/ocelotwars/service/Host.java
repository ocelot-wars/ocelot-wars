package com.github.ocelotwars.service;

import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import com.github.ocelotwars.engine.Player;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class Host extends AbstractVerticle {

	private static final String ACCEPT = "accept";
	private static final int DEFAULT_MINIMAL_REGISTERED_PLAYER_COUNT = 1;
	private List<Player> registeredPlayers = new ArrayList<>();
	private ScheduledFuture<?> waitForPlayersJob;
	private ScheduledExecutorService executor;
	private GameInviter gameInviter;
	private int minimalRegisteredPlayerCount = DEFAULT_MINIMAL_REGISTERED_PLAYER_COUNT;

	@Override
	public void start() {
		executor = Executors.newScheduledThreadPool(1);
		gameInviter = new GameInviter(vertx.createHttpClient(), this::waitForPlayers);

		Router router = Router.router(vertx);
		router.post("/register/:name/:port").handler(this::register);
		router.route().failureHandler(this::fail);

		HttpServer server = vertx.createHttpServer();
		server.requestHandler(router::accept).listen(8080);

		waitForPlayers();
	}

	private void waitForPlayers() {
		waitForPlayersJob = executor.scheduleWithFixedDelay(this::checkForEnoughPlayers, 5, 1, TimeUnit.SECONDS);
	}

	protected void checkForEnoughPlayers() {
		// debugging-output
		System.out.println(registeredPlayers.size());
		if (registeredPlayers.size() >= minimalRegisteredPlayerCount) {
			gameInviter.inviteToGame(registeredPlayers);
			waitForPlayersJob.cancel(false);
		}
	}

	protected void register(RoutingContext context) {
		HttpServerRequest request = context.request();
		String name = request.getParam("name");
		String host = request.remoteAddress().host();
		int port = Integer.parseInt(request.getParam("port"));
		registeredPlayers.removeIf(player -> player.getHost().equals(host) && player.getPort() == port);
		registeredPlayers.add(new Player(name, host, port));

		context.response().setStatusCode(OK.code()).setStatusMessage(ACCEPT).end();
	}

	protected void fail(RoutingContext context) {
		context.response().setStatusCode(INTERNAL_SERVER_ERROR.code()).end();
	}

	public List<Player> getPlayers() {
		return registeredPlayers;
	}

	public void setMinimalRegisteredPlayerCount(int minimalRegisteredPlayerCount) {
		this.minimalRegisteredPlayerCount = minimalRegisteredPlayerCount;
	}

}
