package com.github.ocelotwars.engine;

import java.util.*;
import java.util.stream.Collectors;

public class Playground {

    private int width;
    private int height;
    private Tile[][] tiles;
    private Map<Integer, Unit> units;
    private List<Headquarter> headquarters;

    public Playground(int width, int height) {
        this.units = new HashMap<>();
        this.headquarters = new ArrayList<>();
        this.width = width;
        this.height = height;
        this.tiles = initTiles(width, height);
    }


    private static Tile[][] initTiles(int width, int height) {
        Tile[][] tiles = new Tile[width][height];
        for (int x = 0; x < tiles.length; x++) {
            for (int y = 0; y < tiles[x].length; y++) {
                tiles[x][y] = new Tile(new Position(x, y));
            }
        }
        return tiles;
    }

    public Tile[][] getTiles() {
        return tiles;
    }

    public void putUnit(Unit unit, Position position) {
        Tile tile = getTileAt(position);
        tile.addUnit(unit);
        units.put(unit.getId(), unit);
    }

    public void putHeadquarter(Headquarter hq, Position position) {
        Tile tile = getTileAt(position);
        tile.addHeadquarter(hq);
        headquarters.add(hq);
    }

    public void putResource(int resources, Position position) {
        Tile tile = getTileAt(position);
        tile.setResources(resources);
    }

    public int getFullResourceNumber() {
        int unitResources = units.values()
            .stream()
            .mapToInt(Unit::getLoad)
            .sum();
        int playgroundResources = Arrays.stream(tiles)
            .mapToInt(tileArray -> Arrays.stream(tileArray)
                .mapToInt(Tile::getResources)
                .sum())
            .sum();
        return unitResources + playgroundResources;
    }

    public Unit getUnit(Player player, int unitId) {
        Unit unit = units.get(unitId);
        if (unit == null) {
            throw NoSuchAssetException.forUnit(unitId);
        }
        if (!player.equals(unit.getOwner())) {
            throw NotUnitOwnerException.forPlayerAndUnit(player, unitId);
        }
        return unit;
    }

    public List<Unit> getUnits(Player player) {
        return units.values()
            .stream()
            .filter(unit -> player.equals(unit.getOwner()))
            .collect(Collectors.toList());
    }

    public Tile getTileAt(Position targetPosition) {
        return tiles[targetPosition.x][targetPosition.y];
    }

    public Position shift(Position position, Direction direction) {
        return direction.shift(position).normalize(width, height);
    }

    public Headquarter getHeadQuarter(Player player) {
        return headquarters.stream()
            .filter(hq -> player.equals(hq.getOwner()))
            .findFirst()
            .orElseThrow(() -> NoSuchAssetException.forHeadquarter(player));
    }

}
