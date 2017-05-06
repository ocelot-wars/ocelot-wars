package com.github.ocelotwars.engine.command;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.github.ocelotwars.engine.Headquarter;
import com.github.ocelotwars.engine.NoSuchAssetException;
import com.github.ocelotwars.engine.NotUnitOwnerException;
import com.github.ocelotwars.engine.Player;
import com.github.ocelotwars.engine.Playground;
import com.github.ocelotwars.engine.Position;
import com.github.ocelotwars.engine.Unit;

public class UnloadCommandTest {

	private Player player1 = new Player("1", "192.168.0.10", 8080);
	private Player player2 = new Player("2", "192.168.0.11", 8080);
	private Headquarter hq1 = new Headquarter(player1);
	private Unit unit42 = new Unit(player1, 42);

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	private Playground playground;

	@Before
	public void before() {
		playground = new Playground().init(9, 9);

	}

	@Test
	public void testUnloadOnUnitAndHqWithResourceTransfersResource() throws Exception {
		playground.putUnit(unit42, pos(4, 4));
		unit42.setLoad(1);
		playground.putHeadquarter(hq1, pos(4, 4));

		UnloadCommand command = new UnloadCommand(player1, 42);
		command.execute(playground);

		assertThat(unit42.getLoad(), equalTo(0));
		assertThat(hq1.getResources(), equalTo(1));
	}

	@Test
	public void testUnloadOnUnitAndNoHqInRangeWithResourceTransfersResource() throws Exception {
		playground.putUnit(unit42, pos(4, 4));
		unit42.setLoad(1);
		playground.putHeadquarter(hq1, pos(4, 3));

		UnloadCommand command = new UnloadCommand(player1, 42);
		command.execute(playground);

		assertThat(unit42.getLoad(), equalTo(1));
		assertThat(hq1.getResources(), equalTo(0));
	}

	@Test
	public void testGatherOnUnitWithoutResourceTransfersNoResource() throws Exception {
		playground.putUnit(unit42, pos(4, 4));
		playground.putHeadquarter(hq1, pos(4, 4));

		UnloadCommand command = new UnloadCommand(player1, 42);
		command.execute(playground);

		assertThat(unit42.getLoad(), equalTo(0));
		assertThat(hq1.getResources(), equalTo(0));
	}

	@Test
	public void testUnloadOnUnitAndNoHqThrowsException() throws Exception {
		playground.putUnit(unit42, pos(4, 4));
		unit42.setLoad(1);
		UnloadCommand command = new UnloadCommand(player1, 42);

		thrown.expect(NoSuchAssetException.class);
		command.execute(playground);
	}

	@Test
	public void testGatherOnNoUnitThrowsException() throws Exception {
		UnloadCommand command = new UnloadCommand(player1, 42);

		thrown.expect(NoSuchAssetException.class);
		command.execute(playground);
	}

	@Test
	public void testGatherOnForeignUnitThrowsException() throws Exception {
		playground.putUnit(unit42, pos(4, 4));
		UnloadCommand command = new UnloadCommand(player2, 42);

		thrown.expect(NotUnitOwnerException.class);
		command.execute(playground);
	}

	private Position pos(int x, int y) {
		return new Position(x, y);
	}

}
