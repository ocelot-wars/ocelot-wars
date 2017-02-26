package com.github.ocelotwars.engine.command;

import com.github.ocelotwars.engine.Command;
import com.github.ocelotwars.engine.Direction;
import com.github.ocelotwars.engine.Playground;
import com.github.ocelotwars.engine.Position;
import com.github.ocelotwars.engine.Tile;
import com.github.ocelotwars.engine.Unit;

public class MoveCommand implements Command {

	private int unitId;
	private Direction direction;

	public MoveCommand(int unitId, Direction direction) {
		this.unitId = unitId;
		this.direction = direction;
	}

	@Override
	public void execute(Playground playground) throws InvalidCommandException {
		Unit unit = playground.getUnit(unitId);
		if (unit == null) {
			throw new InvalidCommandException();
		}
		Position position = unit.getPosition();
		Position targetPosition = playground.shift(position, direction);
		Tile target = playground.getTileAt(targetPosition);
		unit.moveTo(target);
	}

}
