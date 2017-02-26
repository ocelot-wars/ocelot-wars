package com.github.ocelotwars.engine;

import java.util.HashMap;
import java.util.Map;

public class Playground {

	private Dimension dimension;
	private Tile[][] tiles;
	private Map<Integer, Unit> units;

	public Playground(Dimension dimension) {
		this.dimension = dimension;
		this.tiles = initTiles(dimension);
		this.units = new HashMap<>();
	}

	private static Tile[][] initTiles(Dimension dimension) {
		Tile[][] tiles = new Tile[dimension.width][dimension.height];
		for (int x = 0; x < tiles.length; x++) {
			for (int y = 0; y < tiles[x].length; y++) {
				tiles[x][y] = new Tile(new Position(x, y));
			}
		}
		return tiles;
	}

	public void putUnit(Unit unit, Position position) {
		Tile tile = getTileAt(position);
		tile.addUnit(unit);
		units.put(unit.getId(), unit);
	}

	public Unit getUnit(int unitId) {
		return units.get(Integer.valueOf(unitId));
	}

	public Tile getTileAt(Position targetPosition) {
		return tiles[targetPosition.x][targetPosition.y];
	}

	public Position shift(Position position, Direction direction) {
		return direction.shift(position).normalize(dimension);
	}

}
