package com.github.ocelotwars.service;

import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static rx.Observable.timer;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.http.WebSocketFrame;
import io.vertx.ext.web.RoutingContext;

public class Host extends AbstractVerticle {

	private GameControl gameControl;

	public Host() {
		gameControl = new GameControl();
		
		timer(5, TimeUnit.SECONDS).first().map(value -> new Game()).subscribe(gameControl);

	}

	@Override
	public void start() {
		HttpServer server = vertx.createHttpServer();
		server.websocketHandler(this::websocket).listen(8080);
	}

	public void websocket(ServerWebSocket socket) {
		MessageInterpreter interpreter = new DefaultMessageInterpreter(socket, gameControl);
		socket.frameHandler(frame -> message(frame, socket, interpreter));
	}

	public void message(WebSocketFrame frame, ServerWebSocket socket, MessageInterpreter interpreter) {
		Message m = mapMessage(frame.textData());

		m.apply(interpreter);
	}

	protected Message mapMessage(String message) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.readValue(message, Message.class);
		} catch (IOException e) {
			return null;
		}
	}

	protected void fail(RoutingContext context) {
		context.response().setStatusCode(INTERNAL_SERVER_ERROR.code()).end();
	}

}
