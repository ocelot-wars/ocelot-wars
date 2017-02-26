package com.github.ocelotwars.engine;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class DirectionTest {

	private final int x = 5;
	private final int y = 12;
	private final Position position = new Position(x, y);

	@Test
	public void testShiftNorth() {
		Position shifted = Direction.NORTH.shift(position);
		assertThat(shifted, is(new Position(x, y - 1)));
	}

	@Test
	public void testShiftEast() {
		Position shifted = Direction.EAST.shift(position);
		assertThat(shifted, is(new Position(x + 1, y)));
	}

	@Test
	public void testShiftSouth() {
		Position shifted = Direction.SOUTH.shift(position);
		assertThat(shifted, is(new Position(x, y + 1)));
	}

	@Test
	public void testShiftWest() {
		Position shifted = Direction.WEST.shift(position);
		assertThat(shifted, is(new Position(x - 1, y)));
	}

}
