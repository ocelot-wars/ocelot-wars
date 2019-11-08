package com.github.ocelotwars.victory;

import com.github.ocelotwars.engine.Player;
import com.github.ocelotwars.engine.game.Game;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ResourceVictoryEvaluator implements VictoryEvaluator {

    @Override
    public Map<Player, Integer> assignPoints(Game game) {
        HashMap<Player, Integer> result = new HashMap<>();
        Set<Player> players = game.getPlayers();
        players.forEach(player -> result.put(player, game.getPlayerResources(player)));
        return result;
    }
}
