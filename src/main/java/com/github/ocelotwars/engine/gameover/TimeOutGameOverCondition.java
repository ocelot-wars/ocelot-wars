package com.github.ocelotwars.engine.gameover;

import com.github.ocelotwars.engine.game.Game;

public class TimeOutGameOverCondition implements GameOverCondition {
    private int maxRounds;

    public TimeOutGameOverCondition(int maxRounds) {
        this.maxRounds = maxRounds;
    }

    @Override public boolean isOver(Game game) {
        return game.getRound() > maxRounds;
    }
}
