package com.github.ocelotwars.engine.game;

import com.github.ocelotwars.engine.Command;
import com.github.ocelotwars.engine.Player;
import com.github.ocelotwars.engine.Playground;
import com.github.ocelotwars.engine.commandsource.CommandSource;
import com.github.ocelotwars.engine.victory.VictoryCondition;

import java.util.*;
import java.util.logging.Logger;

public class Game {

    private Playground playground;
    private VictoryCondition victory;
    private Map<Player, CommandSource> players;
    private int round;

    public Game(Playground playground, Map<Player, CommandSource> players, VictoryCondition victory) {
        this.playground = playground;
        this.victory = victory;
        this.players = players;
        this.round = 1;
    }

    public int getRound() {
        return round;
    }

    public Playground getPlayground() {
        return playground;
    }

    public void execute(List<Command> commands) {
        commands.forEach(command -> command.execute(playground));
    }

    public void run() {
        while (!victory.isOver(this)) {
            System.out.println("Runde:"+round);
            List<Command> commands = new ArrayList<>();
            for (Map.Entry<Player,CommandSource> entry : players.entrySet()) {
                Player player = entry.getKey();
                CommandSource commandSource = entry.getValue();
                commands.addAll(validatePlayerCommands(commandSource.nextCommands(playground), player));
            }
            List<Command> conflictFreeCommands = resolveConflicts(commands);
            execute(conflictFreeCommands);
            round++;
        }
    }

    private List<Command> validatePlayerCommands(List<Command> commands, Player player) {
        return commands;
    }

    private List<Command> resolveConflicts(List<Command> commands) {
        return commands;
    }

}
