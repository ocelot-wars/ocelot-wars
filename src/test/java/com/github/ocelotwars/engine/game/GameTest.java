package com.github.ocelotwars.engine.game;

import static com.github.ocelotwars.engine.Direction.EAST;
import static com.github.ocelotwars.engine.Direction.WEST;
import static com.github.ocelotwars.engine.PlaygroundBuilder.playground;
import static java.util.Collections.emptyList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import java.util.ArrayList;
import java.util.List;
import com.github.ocelotwars.engine.Command;
import com.github.ocelotwars.engine.Headquarter;
import com.github.ocelotwars.engine.Player;
import com.github.ocelotwars.engine.Playground;
import com.github.ocelotwars.engine.Position;
import com.github.ocelotwars.engine.Unit;
import com.github.ocelotwars.engine.command.GatherCommand;
import com.github.ocelotwars.engine.command.MoveCommand;
import com.github.ocelotwars.engine.command.UnloadCommand;
import org.junit.Test;

public class GameTest {

    private Player player = new Player("Player");
    private int unitId = 1;

    @Test
    public void execute_EmptyListOfCommands() throws Exception {
        Playground beforeExecution = createDefaultPlayground(player, unitId);
        Game game = new Game(beforeExecution);

        List<Command> commands = emptyList();

        game.execute(commands);
        
        Playground afterExecution = game.getPlayground();

        assertThat(afterExecution.getUnit(player, unitId).getPosition(),
            is(beforeExecution.getUnit(player, unitId).getPosition()));
    }

    @Test
    public void execute_OneMoveRight() throws Exception {

        Playground beforeExecution = createDefaultPlayground(player, unitId);
        Game game = new Game(beforeExecution);

        List<Command> commands = new ArrayList<>();
        commands.add(new MoveCommand(player, unitId, EAST));

        game.execute(commands);
        
        Playground afterExecution = game.getPlayground();

        assertThat(afterExecution.getUnit(player, unitId).getPosition(), is(new Position(5, 16)));
    }

    @Test
    public void execute_gatheringOfResource() throws Exception {

        Playground beforeExecution = createDefaultPlayground(player, unitId);
        Game game = new Game(beforeExecution);

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
        Playground beforeExecution = playground().withHeight(32).withWidth(32).withHeadquarter(new Headquarter(player))
            .withUnit(new Unit(player, unitId)).withResource(new Position(6, 16), 5).create();
        return beforeExecution;
    }

    // Game kann Commands verarbeiten

    // Game hat Endbedingung

}
