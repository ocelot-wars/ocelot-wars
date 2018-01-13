package com.github.ocelotwars.service;

import java.util.ArrayList;
import java.util.List;
import com.github.ocelotwars.service.commands.Command;

public class Commands implements Message {

    private List<Command> commands;

    public Commands(List<Command> commands) {
        this.commands = commands;
    }

    public Commands() {
        this.commands = new ArrayList<>();
    }

    public List<Command> getCommands() {
        return commands;
    }

}
