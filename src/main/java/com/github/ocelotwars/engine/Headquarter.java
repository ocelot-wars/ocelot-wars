package com.github.ocelotwars.engine;

public class Headquarter extends Asset {

    private int resources;
    private Tile tile;

    public Headquarter(Player owner) {
        super(owner);
    }

    public int getResources() {
        return resources;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Tile getTile() {
        return tile;
    }

    public void storeResource() {
        resources++;
    }

}
