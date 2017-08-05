package com.github.ocelotwars.service;

import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;

import java.util.ArrayList;
import java.util.List;

import com.github.ocelotwars.engine.Player;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.ext.web.RoutingContext;

public class Host extends AbstractVerticle {

	private List<Player> registeredPlayers = new ArrayList<>();

	@Override
	public void start() {
		HttpServer server = vertx.createHttpServer();
		server
			.websocketHandler(this::websocket)
			.listen(8080);
	}
	
	public void websocket(ServerWebSocket socket) {
		socket.frameHandler(frame -> {
			socket.write(Buffer.buffer(frame.textData()));
		});
	}

	protected void fail(RoutingContext context) {
		context.response().setStatusCode(INTERNAL_SERVER_ERROR.code()).end();
	}

	public List<Player> getPlayers() {
		return registeredPlayers;
	}


}
