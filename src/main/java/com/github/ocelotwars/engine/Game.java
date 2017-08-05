package com.github.ocelotwars.engine;

public class Game {

	private PlaygroundBuilder builder;

	public Game(PlaygroundBuilder builder) {
		this.builder = builder;
	}

	public Playground initialize() {
		Playground playground = builder.create();
		return playground;
	}

}
