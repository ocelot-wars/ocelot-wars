package com.github.ocelotwars.service.commands;

public class Move extends Command {

    private Direction direction;

    public Move(int unitId, Direction direction) {
        super(unitId);
        this.direction = direction;
    }

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

}
