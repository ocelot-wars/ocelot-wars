package com.github.ocelotwars.engine.victory;

import com.github.ocelotwars.engine.game.Game;

public interface VictoryCondition {
    boolean isOver(Game game);
}
