package com.github.ocelotwars.engine;

import java.util.ArrayList;

import java.util.List;

public class Tile {

	private final Position position;
	private Resource resource;
	private List<Unit> units;

	public Tile(Position position) {
		this.position = position;
		this.units = new ArrayList<>();
	}

	public void setResource(Resource resource) {
		this.resource = resource;
	}

	public Resource getResource() {
		return this.resource;
	}

	public Resource removeResource() {
		Resource resource = this.resource;
		this.resource = null;
		return resource;
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
