package com.github.ocelotwars.engine.command;

import com.github.ocelotwars.engine.Command;
import com.github.ocelotwars.engine.Player;
import com.github.ocelotwars.engine.Playground;
import com.github.ocelotwars.engine.Unit;

public class GatherCommand implements Command {

    private Player player;
	private int unitId;

	public GatherCommand(Player player, int unitId) {
		this.player = player;
        this.unitId = unitId;
	}

	@Override
	public void execute(Playground playground) {
		Unit unit = playground.getUnit(player, unitId);
		unit.gather();
	}

	@Override
	public int hashCode() {
		return player.hashCode() * 13
			+ unitId * 23
			+ 29;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		GatherCommand that = (GatherCommand) obj;
		return this.player.equals(that.player)
			&& this.unitId == that.unitId;
	}

	@Override
	public String toString() {
		return player.toString() + ": " + unitId + " GATHER";
	}
}
