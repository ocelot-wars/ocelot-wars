package com.github.ocelotwars.engine.command;

import com.github.ocelotwars.engine.Command;
import com.github.ocelotwars.engine.Playground;
import com.github.ocelotwars.engine.Unit;

public class GatherCommand implements Command {

	private int unitId;

	public GatherCommand(int unitId) {
		this.unitId = unitId;
	}

	@Override
	public void execute(Playground playground) throws InvalidCommandException {
		Unit unit = playground.getUnit(unitId);
		if (unit == null) {
			throw new InvalidCommandException();
		}
		unit.gather();
	}

}
