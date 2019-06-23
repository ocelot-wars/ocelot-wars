package com.github.ocelotwars.player;

import com.github.ocelotwars.engine.*;
import com.github.ocelotwars.engine.command.GatherCommand;
import com.github.ocelotwars.engine.command.MoveCommand;
import com.github.ocelotwars.engine.command.UnloadCommand;
import com.github.ocelotwars.engine.commandsource.CommandSource;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.toList;

public class BruteForceCommandSource implements CommandSource {
    private Player player;

    private Map<Unit, Position> lastPosition = new HashMap<>();


    @Override public List<Command> nextCommands(Playground playground) {
        List<Unit> units = playground.getUnits(player);
        return units.stream().map(unit -> nextCommandFor(playground, unit)).collect(toList());
    }

    private Command nextCommandFor(Playground playground, Unit unit) {
        Position position = unit.getPosition();
        Position lastPosition = this.lastPosition.get(unit);
        System.out.println(position.x + " "+ position.y);
        this.lastPosition.put(unit, position);
        if (unit.getLoad() > 0 && playground.getTileAt(position).hasHeadquarter(player)) {
            return new UnloadCommand(player, unit.getId());
        }
        if (unit.getLoad() == 0 && playground.getTileAt(position).getResources() > 0) {
            return new GatherCommand(player, unit.getId());
        }
        if (lastPosition == null) {
            return new MoveCommand(player, unit.getId(), Direction.EAST);
        }
        int edge = playground.getTiles().length - 1;
        if (lastPosition.x == edge) {
            return new MoveCommand(player, unit.getId(), Direction.EAST);
        }
        if (position.x == edge) {
            return new MoveCommand(player, unit.getId(), Direction.SOUTH);
        }
        return new MoveCommand(player, unit.getId(), Direction.EAST);
    }

    @Override
    public void setPlayer(Player player) {
        this.player = player;
    }
}
