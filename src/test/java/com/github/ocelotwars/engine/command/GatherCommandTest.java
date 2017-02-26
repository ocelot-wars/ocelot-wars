package com.github.ocelotwars.engine.command;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.github.ocelotwars.engine.Dimension;
import com.github.ocelotwars.engine.Playground;
import com.github.ocelotwars.engine.Position;
import com.github.ocelotwars.engine.Resource;
import com.github.ocelotwars.engine.Unit;

public class GatherCommandTest {

	private static Unit unit42 = new Unit(42);
	private static Resource resource = new Resource();

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private Playground playground;

	@Before
	public void before() {
		playground = new Playground(new Dimension(9, 9));

	}

	@Test
	public void testGatherOnUnitWithResourceTransfersResource() throws Exception {
		playground.putUnit(unit42, pos(4, 4));
		playground.putResource(resource, pos(4, 4));

		GatherCommand command = new GatherCommand(42);
		command.execute(playground);

		assertThat(unit42.getLoad(), is(resource));
		assertThat(playground.getTileAt(pos(4, 4)).getResource(), nullValue());
	}

	@Test
	public void testGatherOnUnitWithoutResourceTransfersNoResource() throws Exception {
		playground.putUnit(unit42, pos(4, 4));

		GatherCommand command = new GatherCommand(42);
		command.execute(playground);

		assertThat(unit42.getLoad(), nullValue());
		assertThat(playground.getTileAt(pos(4, 4)).getResource(), nullValue());
	}

	@Test
	public void testGatherOnNoUnitThrowsException() throws Exception {
		GatherCommand command = new GatherCommand(42);
		thrown.expect(InvalidCommandException.class);
		command.execute(playground);
	}

	private Position pos(int x, int y) {
		return new Position(x, y);
	}

}
