package com.github.ocelotwars.engine;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class PlaygroundFactoryTest {
	@Test
	public void createPlaygroundFromFile() throws Exception {
		PlaygroundFactory playgroundFactory = new PlaygroundFactory();
		Player player = new Player("Test", "test", 0);
		int unitId = 1;

		Playground playground = playgroundFactory.getOnePlayerPlayground(player);

		assertThat(playground.getTiles().length, is(7));
		assertThat(playground.getTiles()[0].length, is(7));
		assertThat(playground.getHeadQuarter(player).getTile().getPosition(), is(new Position(3, 4)));
		assertThat(playground.getUnit(player, unitId).getPosition(), is(new Position(3, 4)));

		assertThat(playground.getTileAt(new Position(1, 1)).getResources(), is(1));
		assertThat(playground.getTileAt(new Position(5, 1)).getResources(), is(2));
		assertThat(playground.getTileAt(new Position(1, 4)).getResources(), is(3));
		assertThat(playground.getTileAt(new Position(6, 5)).getResources(), is(4));
	}
}
