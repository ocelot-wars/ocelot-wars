package com.github.ocelotwars.victory;

import com.github.ocelotwars.engine.Player;
import com.github.ocelotwars.engine.game.Game;

import java.util.Map;

public interface VictoryEvaluator {
    Map<Player, Integer> assignPoints(Game game);
}
