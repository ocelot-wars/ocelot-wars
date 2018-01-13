package com.github.ocelotwars.service;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.sameInstance;
import static org.junit.Assert.assertThat;

import org.junit.jupiter.api.Test;

import com.github.ocelotwars.engine.Command;
import com.github.ocelotwars.engine.Player;
import com.github.ocelotwars.engine.command.GatherCommand;
import com.github.ocelotwars.engine.command.MoveCommand;
import com.github.ocelotwars.engine.command.UnloadCommand;
import com.github.ocelotwars.service.commands.Direction;
import com.github.ocelotwars.service.commands.Gather;
import com.github.ocelotwars.service.commands.Move;
import com.github.ocelotwars.service.commands.Unload;

class CommandFactoryTest {

    private static final int UNIT_ID = 1000;
    private Player player = new Player("test player name");

    private CommandFactory commandFactory = new CommandFactory(player);

    @Test
    void convertGatherCommand() {
        com.github.ocelotwars.service.commands.Command commandSrc = new Gather(UNIT_ID);

        Command command = commandFactory.convertCommand(commandSrc);

        assertThat(command, equalTo(new GatherCommand(player, UNIT_ID)));
    }

    @Test
    void convertMoveCommand() {
        com.github.ocelotwars.service.commands.Command commandSrc = new Move(UNIT_ID, Direction.SOUTH);

        Command command = commandFactory.convertCommand(commandSrc);

        assertThat(command, equalTo(new MoveCommand(player, UNIT_ID, com.github.ocelotwars.engine.Direction.SOUTH)));
    }

    @Test
    void convertUnloadCommand() {
        com.github.ocelotwars.service.commands.Command commandSrc = new Unload(UNIT_ID);

        Command command = commandFactory.convertCommand(commandSrc);

        assertThat(command, equalTo(new UnloadCommand(player, UNIT_ID)));
    }

    @Test
    void convertUnknownCommand() {
        com.github.ocelotwars.service.commands.Command commandSrc = new com.github.ocelotwars.service.commands.Command(
            UNIT_ID) {
        };

        Command command = commandFactory.convertCommand(commandSrc);

        assertThat(command, sameInstance(Command.NULL));
    }

}
