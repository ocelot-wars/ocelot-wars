package com.github.ocelotwars.engine;

public class Resource {

    private Position position;
    private int value;

    public Resource(Position position, int value) {
        this.position = position;
        this.value = value;
    }

    public Position getPosition() {
        return position;
    }

    public int getValue() {
        return value;
    }

}
