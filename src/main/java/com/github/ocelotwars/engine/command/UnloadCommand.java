package com.github.ocelotwars.engine.command;

import com.github.ocelotwars.engine.Command;
import com.github.ocelotwars.engine.Headquarter;
import com.github.ocelotwars.engine.Player;
import com.github.ocelotwars.engine.Playground;
import com.github.ocelotwars.engine.Unit;

public class UnloadCommand implements Command {

    private Player player;
	private int unitId;

	public UnloadCommand(Player player, int unitId) {
		this.player = player;
        this.unitId = unitId;
	}

	@Override
	public void execute(Playground playground) {
		Unit unit = playground.getUnit(player, unitId);
		Headquarter headquarter = playground.getHeadQuarter(player);
		unit.unload(headquarter);
	}
	@Override
	public int hashCode() {
		return player.hashCode() * 13
			+ unitId * 23
			+7;
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
		UnloadCommand that = (UnloadCommand) obj;
		return this.player.equals(that.player)
			&& this.unitId == that.unitId;
	}

	@Override
	public String toString() {
		return player.toString() + ": " + unitId + " UNLOAD";
	}
}