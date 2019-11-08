package com.github.ocelotwars.engine.game;

import com.github.ocelotwars.engine.Command;
import com.github.ocelotwars.engine.Player;
import com.github.ocelotwars.engine.Playground;
import com.github.ocelotwars.engine.commandsource.CommandSource;
import com.github.ocelotwars.engine.gameover.GameOverCondition;
import com.github.ocelotwars.victory.VictoryEvaluator;

import java.util.*;

public class Game {

    private Playground playground;
    private GameOverCondition gameOverCondition;
    private VictoryEvaluator victoryEvaluator;
    private Map<Player, CommandSource> players;
    private int round;

    public Game(Playground playground, Map<Player, CommandSource> players, GameOverCondition gameOverCondition, VictoryEvaluator victoryEvaluator) {
        this.playground = playground;
        this.gameOverCondition = gameOverCondition;
        this.players = players;
        this.victoryEvaluator = victoryEvaluator;
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

    public Set<Player> getPlayers() {
        return players.keySet();
    }

    public void run() {
        while (!gameOverCondition.isOver(this)) {
            System.out.println("Runde:"+round);
            List<Command> commands = new ArrayList<>();
            for (Map.Entry<Player,CommandSource> entry : players.entrySet()) {
                Player player = entry.getKey();
                CommandSource commandSource = entry.getValue();
                commands.addAll(validatePlayerCommands(commandSource.nextCommands(playground), player));
            }
            List<Command> conflictFreeCommands = resolveConflicts(commands);
            execute(conflictFreeCommands);
            System.out.println("Resources :" + playground.getFullResourceNumber());
            round++;
        }
        Map<Player, Integer> endResult = victoryEvaluator.assignPoints(this);
        endResult.forEach((player, integer) -> {
            System.out.println("Player: " + player.getName());
            System.out.println("Points: " + integer);
            System.out.println("----------------");
        });
        Optional<Map.Entry<Player, Integer>> winner = endResult.entrySet().stream().max(Comparator.comparingInt(Map.Entry::getValue));
        winner.ifPresent(playerIntegerEntry -> System.out.println("Winner: " + playerIntegerEntry.getKey().getName()));
    }

    private List<Command> validatePlayerCommands(List<Command> commands, Player player) {
        return commands;
    }

    private List<Command> resolveConflicts(List<Command> commands) {
        return commands;
    }

    public int getPlayerResources(Player player) {
        return playground.getHeadQuarter(player).getResources();
    }

    public int getFullResourceNumber() {
        return playground.getFullResourceNumber();
    }

}
