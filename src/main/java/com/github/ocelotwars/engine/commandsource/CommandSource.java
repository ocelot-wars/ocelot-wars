package com.github.ocelotwars.engine.commandsource;

import com.github.ocelotwars.engine.Command;
import com.github.ocelotwars.engine.Player;
import com.github.ocelotwars.engine.Playground;

import java.util.List;

public interface CommandSource {
    List<Command> nextCommands(Playground playground);
    void setPlayer(Player player);
}
