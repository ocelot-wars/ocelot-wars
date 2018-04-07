package com.github.ocelotwars.playgroundparser;

import static java.lang.Integer.valueOf;
import java.util.Iterator;
import java.util.List;
import com.github.ocelotwars.engine.Headquarter;
import com.github.ocelotwars.engine.Player;
import com.github.ocelotwars.engine.Playground;
import com.github.ocelotwars.engine.Position;
import com.github.ocelotwars.engine.Unit;

public class PlaygroundFactory {

    private SerializedPlayground serializedPlayground;
    private Playground playground;
    private int unitId;

    public PlaygroundFactory(String pathToFile) {
        serializedPlayground = new SerializedPlayground(pathToFile);
        unitId = 0;
        playground = new Playground(serializedPlayground.getHeight(), serializedPlayground.getWidth());
    }
    
    public Playground createPlayground(List<Player> players) {
        fillPlayground(players);
        return playground;
    }

    private void fillPlayground(List<Player> players) {
        Iterator<Player> iterator = players.iterator();
        for (int y = 0; y < serializedPlayground.getHeight(); y++) {
            for (int x = 0; x < serializedPlayground.getWidth(); x++) {
                handleTile(iterator, new Position(x, y));
            }
        }

    }

    private void handleTile(Iterator<Player> iterator, Position position) {
        if (serializedPlayground.hasHeadquarterAtPosition(position)) {
            if (iterator.hasNext()) {
                Player player = iterator.next();
                playground.putHeadquarter(new Headquarter(player), position);
                playground.putUnit(createUnit(player), position);
            }
        } else {
            playground.putResource(valueOf(serializedPlayground.getTileAtPosition(position)), position);
        }
    }

    private Unit createUnit(Player player) {
        unitId++;
        Unit unit = new Unit(player, unitId);
        return unit;
    }

}
