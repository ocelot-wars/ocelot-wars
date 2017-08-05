package com.github.ocelotwars.service;

public class Register implements Message {

	private String playerName;
	
	@Override
	public void apply(MessageInterpreter messageInterpreter) {
		messageInterpreter.visitRegister(this);
	}

	public String getPlayerName() {
		return playerName;
	}
}
