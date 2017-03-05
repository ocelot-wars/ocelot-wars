package com.github.ocelotwars.engine.command;

import com.github.ocelotwars.engine.Command;
import com.github.ocelotwars.engine.Direction;
import com.github.ocelotwars.engine.Player;
import com.github.ocelotwars.engine.Playground;
import com.github.ocelotwars.engine.Position;
import com.github.ocelotwars.engine.Tile;
import com.github.ocelotwars.engine.Unit;

public class MoveCommand implements Command {

    private Player player;
	private int unitId;
	private Direction direction;

	public MoveCommand(Player player, int unitId, Direction direction) {
		this.player = player;
        this.unitId = unitId;
		this.direction = direction;
	}

	@Override
	public void execute(Playground playground) {
		Unit unit = playground.getUnit(player, unitId);
		Position position = unit.getPosition();
		Position targetPosition = playground.shift(position, direction);
		Tile target = playground.getTileAt(targetPosition);
		unit.moveTo(target);
	}

}
