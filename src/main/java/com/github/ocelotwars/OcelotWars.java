package com.github.ocelotwars;

import com.github.ocelotwars.engine.Player;
import com.github.ocelotwars.engine.Playground;
import com.github.ocelotwars.engine.commandsource.CommandSource;
import com.github.ocelotwars.engine.game.Game;
import com.github.ocelotwars.engine.gameover.LessThanResourcesLeftGameOverCondition;
import com.github.ocelotwars.playgroundparser.PlaygroundFactory;
import com.github.ocelotwars.victory.ResourceVictoryEvaluator;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OcelotWars {
    public static void main(String[] args) throws IOException, ReflectiveOperationException {
        PlaygroundFactory playgroundFactory = new PlaygroundFactory(args[0]);
        List<Player> players = new ArrayList<>();
        Map<Player, CommandSource> commandSources = new HashMap<>();
        for (int i = 1; i < args.length - 1; i += 2) {
            String name = args[i];
            String commandSourceClassName = args[i + 1];
            Player player = new Player(name);
            players.add(player);
            commandSources.put(player, instantiate(commandSourceClassName, player));
        }
        Playground playground = playgroundFactory.createPlayground(players);
        Game game = new Game(playground, commandSources, new LessThanResourcesLeftGameOverCondition(1), new ResourceVictoryEvaluator());
        game.run();
    }

    private static CommandSource instantiate(String commandSourceClassName, Player player) throws ReflectiveOperationException {
        Class<CommandSource> aClass = (Class<CommandSource>) Class.forName(commandSourceClassName);
        CommandSource commandSource = aClass.newInstance();
        commandSource.setPlayer(player);
        return commandSource;
    }
}
