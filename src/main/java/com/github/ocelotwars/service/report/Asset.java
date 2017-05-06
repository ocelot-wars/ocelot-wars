package com.github.ocelotwars.service.report;

public abstract class Asset {

	private String player;

	public void setPlayer(String player) {
		this.player = player;
	}
	
	public String getPlayer() {
		return player;
	}
}
