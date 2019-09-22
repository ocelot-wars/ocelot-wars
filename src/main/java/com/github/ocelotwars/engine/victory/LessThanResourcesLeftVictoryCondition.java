package com.github.ocelotwars.engine.victory;

import com.github.ocelotwars.engine.game.Game;

public class LessThanResourcesLeftVictoryCondition implements VictoryCondition {
    private int resources;

    public LessThanResourcesLeftVictoryCondition(int resources) {
        this.resources = resources;
    }

    @Override public boolean isOver(Game game) {
        return game.getPlayground().getFullResourceNumber() < resources;
    }
}
