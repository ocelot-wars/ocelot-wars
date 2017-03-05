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

}
