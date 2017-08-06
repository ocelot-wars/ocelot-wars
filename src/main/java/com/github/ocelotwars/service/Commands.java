package com.github.ocelotwars.service;

import java.util.List;

import com.github.ocelotwars.service.commands.Command;

public class Commands implements Message {

	private List<Command> commands;

	public Commands() {
	}
	
	public List<Command> getCommands() {
		return commands;
	}

}
