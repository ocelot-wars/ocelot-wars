package com.github.ocelotwars.engine.victory;

import com.github.ocelotwars.engine.game.Game;

public class TimeOutVictoryCondition implements VictoryCondition {
    private int maxRounds;

    public TimeOutVictoryCondition(int maxRounds) {
        this.maxRounds = maxRounds;
    }

    @Override public boolean isOver(Game game) {
        return game.getRound() > maxRounds;
    }
}
