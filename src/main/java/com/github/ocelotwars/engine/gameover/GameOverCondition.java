package com.github.ocelotwars.engine.gameover;

import com.github.ocelotwars.engine.game.Game;

public interface GameOverCondition {
    boolean isOver(Game game);
}
