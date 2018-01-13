package com.github.ocelotwars.service;

import com.github.ocelotwars.engine.Command;
import com.github.ocelotwars.engine.Direction;
import com.github.ocelotwars.engine.Player;
import com.github.ocelotwars.engine.command.GatherCommand;
import com.github.ocelotwars.engine.command.MoveCommand;
import com.github.ocelotwars.engine.command.UnloadCommand;
import com.github.ocelotwars.service.commands.Gather;
import com.github.ocelotwars.service.commands.Move;
import com.github.ocelotwars.service.commands.Unload;

public class CommandFactory {

    private Player player;

    public CommandFactory(Player player) {
        this.player = player;
    }

    public Command convertCommand(com.github.ocelotwars.service.commands.Command commandSrc) {
        if (commandSrc instanceof Move) {
            Move move = (Move) commandSrc;
            return new MoveCommand(player, move.getUnitId(), convertDirection(move.getDirection()));
        } else if (commandSrc instanceof Unload) {
            return new UnloadCommand(player, commandSrc.getUnitId());
        } else if (commandSrc instanceof Gather) {
            return new GatherCommand(player, commandSrc.getUnitId());
        } else {
            return Command.NULL;
        }
    }

    public Direction convertDirection(com.github.ocelotwars.service.commands.Direction direction) {
        return Direction.valueOf(direction.name());
    }
}
