package com.github.ocelotwars.engine;

import java.util.ArrayList;

import java.util.List;

public class Tile {

    private Position position;
    private int resources;
    private List<Unit> units;
    private Headquarter headquarter;

    public Tile(Position position) {
        this.position = position;
        this.units = new ArrayList<>();
    }

    public void setResources(int resources) {
        this.resources = resources;
    }
    
    public int getResources() {
        return resources;
    }

    public int removeResource() {
        if (resources <= 0) {
            return 0;
        }
        resources--;
        return 1;
    }

    public void addHeadquarter(Headquarter hq) {
        hq.setTile(this);
        headquarter = hq;
    }
    
    public boolean hasHeadquarter(Player player) {
        if (headquarter == null) {
            return false;
        }
        if (player == null) {
            return true;
        } else {
            return player.equals(headquarter.getOwner());
        }
    }

    public void addUnit(Unit unit) {
        unit.setTile(this);
        units.add(unit);
    }

    public void removeUnit(Unit unit) {
        units.remove(unit);
    }

    public List<Unit> getUnits() {
        return units;
    }

    public Position getPosition() {
        return position;
    }

}
