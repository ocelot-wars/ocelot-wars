package com.github.ocelotwars.service;

import java.util.List;

public class Run implements GameControlMessage {

	private List<SocketPlayer> players;

	public Run(List<SocketPlayer> players) {
		this.players = players;
	}

	public List<SocketPlayer> getPlayers() {
		return players;
	}
}
