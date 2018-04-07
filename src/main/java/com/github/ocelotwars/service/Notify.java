package com.github.ocelotwars.service;

import java.util.List;

public class Notify implements OutMessage {

    private List<List<Tile>> tiles;

    public Notify(List<List<Tile>> tiles) {
        this.tiles = tiles;
    }

    public List<List<Tile>> getTiles() {
        return tiles;
    }

}
