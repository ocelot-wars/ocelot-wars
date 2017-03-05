package com.github.ocelotwars.engine;

public class Unit extends Asset {

    private int id;
    private int load;
    private Tile tile;

    public Unit(Player player, int id) {
        super(player);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setLoad(int load) {
        this.load = load;
    }

    public void setTile(Tile tile) {
        this.tile = tile;
    }

    public Position getPosition() {
        return tile.getPosition();
    }

    public int getLoad() {
        return load;
    }

    public void moveTo(Tile target) {
        tile.removeUnit(this);
        target.addUnit(this);
    }

    public void gather() {
        if (load >= 1) {
            return;
        }
        load += tile.removeResource();
    }

    public void unload(Headquarter headquarter) {
        if (load <= 0) {
            return;
        } else if (tile != headquarter.getTile()) {
            return;
        }
        load--;
        headquarter.storeResource();
    }

}
