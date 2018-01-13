package com.github.ocelotwars.engine;

public enum Direction {
    NORTH(0, -1), EAST(1, 0), SOUTH(0, 1), WEST(-1, 0);

    private int deltax;
    private int deltay;

    private Direction(int deltax, int deltay) {
        this.deltax = deltax;
        this.deltay = deltay;
    }

    public Position shift(Position position) {
        return new Position(position.x + deltax, position.y + deltay);
    }
}
