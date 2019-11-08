package com.github.ocelotwars.engine.game;

import com.github.ocelotwars.engine.*;
import com.github.ocelotwars.engine.command.GatherCommand;
import com.github.ocelotwars.engine.command.MoveCommand;
import com.github.ocelotwars.engine.command.UnloadCommand;
import com.github.ocelotwars.engine.gameover.TimeOutGameOverCondition;
import com.github.ocelotwars.victory.ResourceVictoryEvaluator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.github.ocelotwars.engine.Direction.EAST;
import static com.github.ocelotwars.engine.Direction.WEST;
import static com.github.ocelotwars.engine.PlaygroundBuilder.playground;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class GameTest {

    private Player player = new Player("Player");
    private int unitId = 1;

    @Test
    public void execute_EmptyListOfCommands() throws Exception {
        Playground beforeExecution = createDefaultPlayground(player, unitId);
        Game game = new Game(beforeExecution, new HashMap<>(), new TimeOutGameOverCondition(10), new ResourceVictoryEvaluator());

        List<Command> commands = emptyList();

        game.execute(commands);

        Playground afterExecution = game.getPlayground();

        assertThat(afterExecution.getUnit(player, unitId).getPosition(),
            is(beforeExecution.getUnit(player, unitId).getPosition()));
    }

    @Test
    public void execute_OneMoveRight() throws Exception {

        Playground beforeExecution = createDefaultPlayground(player, unitId);
        Game game = new Game(beforeExecution, new HashMap<>(), new TimeOutGameOverCondition(10), new ResourceVictoryEvaluator());

        List<Command> commands = new ArrayList<>();
        commands.add(new MoveCommand(player, unitId, EAST));

        game.execute(commands);

        Playground afterExecution = game.getPlayground();

        assertThat(afterExecution.getUnit(player, unitId).getPosition(), is(new Position(5, 16)));
    }

    @Test
    public void execute_gatheringOfResource() throws Exception {

        Playground beforeExecution = createDefaultPlayground(player, unitId);
        Game game = new Game(beforeExecution, new HashMap<>(), new TimeOutGameOverCondition(10), new ResourceVictoryEvaluator());

        List<Command> commands = new ArrayList<>();
        commands.add(new MoveCommand(player, unitId, EAST));
        commands.add(new MoveCommand(player, unitId, EAST));
        commands.add(new GatherCommand(player, unitId));
        commands.add(new MoveCommand(player, unitId, WEST));
        commands.add(new MoveCommand(player, unitId, WEST));
        commands.add(new UnloadCommand(player, unitId));

        assertThat(beforeExecution.getHeadQuarter(player).getResources(), is(0));
        game.execute(commands);

        Playground afterExecution = game.getPlayground();
        assertThat(afterExecution.getHeadQuarter(player).getResources(), is(1));
    }


    private Playground createDefaultPlayground(Player player, int unitId) {
        return playground()
            .withHeight(32)
            .withWidth(32)
            .withHeadquarter(new Headquarter(player))
            .withUnit(new Unit(player, unitId))
            .withResource(new Position(6, 16), 5)
            .create();
    }

    // Game kann Commands verarbeiten

    // Game hat Endbedingung

}
