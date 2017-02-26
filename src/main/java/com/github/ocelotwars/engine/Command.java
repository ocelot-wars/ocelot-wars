package com.github.ocelotwars.engine;

import com.github.ocelotwars.engine.command.InvalidCommandException;

public interface Command {

	void execute(Playground playground) throws InvalidCommandException;

}
