package com.github.ocelotwars.service;

import java.util.LinkedList;
import java.util.List;

import io.vertx.core.http.ServerWebSocket;

public class PlayerRegistry  {

	private List<SocketPlayer> players;
	
	public PlayerRegistry() {
		this.players = new LinkedList<>();
	}
	
	public void register(String name, ServerWebSocket socket) {
		players.removeIf(player -> player.getSocket() == socket);
		players.add(new SocketPlayer(name, socket));
	}

	public List<SocketPlayer> getPlayers() {
		return players;
	}

}
