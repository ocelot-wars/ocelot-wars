package com.github.ocelotwars.service;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ocelotwars.engine.Player;

import io.vertx.core.http.ServerWebSocket;

public class DefaultMessageInterpreter implements MessageInterpreter {

	private ServerWebSocket socket;
	private GameControl gameControl;
	private ObjectMapper mapper;
	
	private String playerName;

	public DefaultMessageInterpreter(ServerWebSocket socket, GameControl gameControl) {
		this.socket = socket;
		this.gameControl = gameControl;
		this.mapper = new ObjectMapper();
	}

	@Override
	public void visitRegister(Register register) {
		gameControl.unregister(playerName);
		playerName = register.getPlayerName();
		gameControl.register(playerName);
		gameControl.invites()
			.filter(player -> player.getName().equals(playerName))
			.subscribe(this::invite);
	}

	public void invite(Player player) {
		try {
			System.out.println("inviting " + player.getName());
			OutMessage message = new Invite();
			String frame = mapper.writeValueAsString(message);
			socket.writeFinalTextFrame(frame);
		} catch (IOException e) {
			e.printStackTrace();
			return;
		}
	}

	@Override
	public void visitAccept(Accept accept) {
		System.out.println("accepting");
		gameControl.currentGame().subscribe(game -> game.accept(playerName));
	}

}
