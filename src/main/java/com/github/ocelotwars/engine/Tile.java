package com.github.ocelotwars.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class Tile {

    private Position position;
    private int resources;
    private List<Asset> assets;

    public Tile(Position position) {
        this.position = position;
        this.assets = new ArrayList<>();
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
        assets.add(hq);
    }

    public boolean hasHeadquarter(Player player) {
        Optional<Headquarter> headquarter = assets.stream().filter(asset -> asset instanceof Headquarter)
            .map(asset -> (Headquarter) asset).filter(hq -> player == null || hq.getOwner() == player).findFirst();
        return headquarter.isPresent();
    }

    public void addUnit(Unit unit) {
        unit.setTile(this);
        assets.add(unit);
    }

    public void removeUnit(Unit unit) {
        assets.remove(unit);
    }

    public List<Asset> getAssets() {
        return assets;
    }

    public List<Unit> getUnits() {
        return assets.stream()
            .filter(asset -> asset instanceof Unit)
            .map(asset -> (Unit) asset)
            .collect(Collectors.toList());
    }

    public Position getPosition() {
        return position;
    }

}
