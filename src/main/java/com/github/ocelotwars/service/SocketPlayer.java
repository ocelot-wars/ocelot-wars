package com.github.ocelotwars.service;

import com.github.ocelotwars.engine.Player;

import io.vertx.core.http.ServerWebSocket;

public class SocketPlayer {

	private Player player;
	private ServerWebSocket socket;

	public SocketPlayer(String name, ServerWebSocket socket) {
		this.player = new Player(name);
		this.socket = socket;
	}
	
	public Player getPlayer() {
		return player;
	}

	public String getName() {
		return player.getName();
	}
	
	public ServerWebSocket getSocket() {
		return socket;
	}

}
