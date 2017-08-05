package com.github.ocelotwars.engine;

import java.util.List;

public class Game {

	private Playground playground;

	public Game(Playground playground) {
		this.playground = playground;
	}

	public Playground execute(List<Command> commands) {
		commands.forEach(command -> command.execute(playground));
		return playground;
	}

}
