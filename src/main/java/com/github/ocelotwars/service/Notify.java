package com.github.ocelotwars.service;

public class Notify implements OutMessage {

    private Tile[][] tiles;

    public Notify(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

}
