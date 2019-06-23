package com.github.ocelotwars.playgroundparser;

import com.github.ocelotwars.engine.*;

import java.util.*;

public class PlaygroundBuilder {

    private List<List<String>> tiles;
    private int unitId;
    private Map<Player, Position> startPositions;
    private List<Position> resources;

    public PlaygroundBuilder(List<List<String>> tiles) {
        this.tiles = tiles;
        this.unitId = 0;
        this.startPositions = new HashMap<>();
        this.resources = new ArrayList<>();
    }

    public static PlaygroundBuilder builder(List<List<String>> tiles) {
        return new PlaygroundBuilder(tiles);
    }

    public PlaygroundBuilder withPlayers(List<Player> players) {
        Queue<Player> playerQueue = new LinkedList<>(players);
        int height = getHeight();
        int width = getWidth();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Position position = new Position(x, y);
                if (!playerQueue.isEmpty() && hasHeadquarterAtPosition(position)) {
                    Player player = playerQueue.remove();
                    startPositions.put(player, position);
                } else {
                    resources.add(position);
                }
            }
        }
        return this;
    }

    public Playground build() {
        int height = getHeight();
        int width = getWidth();
        Playground playground = new Playground(width, height);
        for (Map.Entry<Player, Position> startPosition : startPositions.entrySet()) {
            Player player = startPosition.getKey();
            Position position = startPosition.getValue();
            playground.putHeadquarter(createHeadquarter(player), position);
            playground.putUnit(createUnit(player), position);
        }
        for (Position position : resources) {
            playground.putResource(createResource(position), position);
        }
        return playground;
    }

    private Unit createUnit(Player player) {
        unitId++;
        return new Unit(player, unitId);
    }

    private int createResource(Position position) {
        try {
            String tile = getTileAtPosition(position);
            return Integer.parseInt(tile);
        } catch (NumberFormatException | NullPointerException e) {
            return 0;
        }
    }

    private Headquarter createHeadquarter(Player player) {
        return new Headquarter(player);
    }

    public int getHeight() {
        return tiles.size();
    }

    public int getWidth() {
        return tiles.get(0).size();
    }

    public boolean hasHeadquarterAtPosition(Position position) {
        return getTileAtPosition(position).equals("H");
    }

    private String getTileAtPosition(Position position) {
        return tiles.get(position.y).get(position.x);
    }

}
