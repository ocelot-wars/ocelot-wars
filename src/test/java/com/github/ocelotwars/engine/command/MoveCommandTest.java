package com.github.ocelotwars.engine.command;

import static com.almondtools.conmatch.conventions.EqualityMatcher.satisfiesDefaultEquality;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.github.ocelotwars.engine.Direction;
import com.github.ocelotwars.engine.NoSuchAssetException;
import com.github.ocelotwars.engine.NotUnitOwnerException;
import com.github.ocelotwars.engine.Player;
import com.github.ocelotwars.engine.Playground;
import com.github.ocelotwars.engine.Position;
import com.github.ocelotwars.engine.Unit;

public class MoveCommandTest {

    private Player player1 = new Player("1");
    private Player player2 = new Player("2");
    private Unit unit42 = new Unit(player1, 42);

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Playground playground;

    @Before
    public void before() {
        playground = new Playground().init(9, 9);
    }

    @Test
    public void testMoveRemovesUnitFromOriginTile() throws Exception {
        playground.putUnit(unit42, pos(4, 4));
        MoveCommand command = new MoveCommand(player1, 42, Direction.NORTH);
        command.execute(playground);

        assertThat(playground.getTileAt(pos(4, 4)).getUnits(), empty());
    }

    @Test
    public void testMoveAddsUnitToTargetTile() throws Exception {
        playground.putUnit(unit42, pos(4, 4));
        MoveCommand command = new MoveCommand(player1, 42, Direction.NORTH);
        command.execute(playground);

        assertThat(playground.getTileAt(pos(4, 3)).getUnits(), contains(unit42));
    }

    @Test
    public void testMoveSetsTileForUnit() throws Exception {
        playground.putUnit(unit42, pos(4, 4));
        MoveCommand command = new MoveCommand(player1, 42, Direction.NORTH);
        command.execute(playground);

        assertThat(playground.getTileAt(pos(4, 3)).getUnits(), contains(unit42));
    }

    @Test
    public void testThrowsExceptionOnUnknownUnit() throws Exception {
        MoveCommand command = new MoveCommand(player1, 42, Direction.NORTH);

        thrown.expect(NoSuchAssetException.class);
        command.execute(playground);
    }

    @Test
    public void testThrowsExceptionOnForeignUnit() throws Exception {
        playground.putUnit(unit42, pos(4, 4));
        MoveCommand command = new MoveCommand(player2, 42, Direction.NORTH);

        thrown.expect(NotUnitOwnerException.class);
        command.execute(playground);
    }

    @Test
    public void testEquals() throws Exception {
        assertThat(new MoveCommand(new Player("Player1"), 42, Direction.NORTH), satisfiesDefaultEquality()
            .andEqualTo(new MoveCommand(new Player("Player1"), 42, Direction.NORTH))
            .andNotEqualTo(new MoveCommand(new Player("Player2"), 42, Direction.NORTH))
            .andNotEqualTo(new MoveCommand(new Player("Player1"), 43, Direction.NORTH))
            .andNotEqualTo(new MoveCommand(new Player("Player1"), 42, Direction.EAST)));
    }

    private Position pos(int x, int y) {
        return new Position(x, y);
    }

}
