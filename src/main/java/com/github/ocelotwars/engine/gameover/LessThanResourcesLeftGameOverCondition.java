package com.github.ocelotwars.engine.gameover;

import com.github.ocelotwars.engine.game.Game;

public class LessThanResourcesLeftGameOverCondition implements GameOverCondition {
    private int resources;

    public LessThanResourcesLeftGameOverCondition(int resources) {
        this.resources = resources;
    }

    @Override public boolean isOver(Game game) {
        return game.getFullResourceNumber() < resources;
    }
}
