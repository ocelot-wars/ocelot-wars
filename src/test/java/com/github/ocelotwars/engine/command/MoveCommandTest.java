package com.github.ocelotwars.engine.command;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.empty;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.github.ocelotwars.engine.Dimension;
import com.github.ocelotwars.engine.Direction;
import com.github.ocelotwars.engine.Playground;
import com.github.ocelotwars.engine.Position;
import com.github.ocelotwars.engine.Unit;

public class MoveCommandTest {

	private static Unit unit42 = new Unit(42);

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private Playground playground;

	@Before
	public void before() {
		playground = new Playground(new Dimension(9, 9));
	}

	@Test
	public void testMoveRemovesUnitFromOriginTile() throws Exception {
		playground.putUnit(unit42, pos(4, 4));
		MoveCommand command = new MoveCommand(42, Direction.NORTH);
		command.execute(playground);

		assertThat(playground.getTileAt(pos(4, 4)).getUnits(), empty());
	}

	@Test
	public void testMoveAddsUnitToTargetTile() throws Exception {
		playground.putUnit(unit42, pos(4, 4));
		MoveCommand command = new MoveCommand(42, Direction.NORTH);
		command.execute(playground);

		assertThat(playground.getTileAt(pos(4, 3)).getUnits(), contains(unit42));
	}

	@Test
	public void testMoveSetsTileForUnit() throws Exception {
		playground.putUnit(unit42, pos(4, 4));
		MoveCommand command = new MoveCommand(42, Direction.NORTH);
		command.execute(playground);

		assertThat(playground.getTileAt(pos(4, 3)).getUnits(), contains(unit42));
	}

	@Test
	public void testThrowsExceptionOnUnknownUnit() throws Exception {
		MoveCommand command = new MoveCommand(42, Direction.NORTH);
		thrown.expect(InvalidCommandException.class);
		command.execute(playground);
	}

	private Position pos(int x, int y) {
		return new Position(x, y);
	}

}
