package com.github.ocelotwars.playgroundparser;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

import org.junit.Test;

import com.github.ocelotwars.engine.Player;
import com.github.ocelotwars.engine.Playground;
import com.github.ocelotwars.engine.Position;

import com.github.ocelotwars.playgroundparser.PlaygroundFactory;

public class PlaygroundFactoryTest {
	@Test
	public void createPlaygroundFromFile_onePlayer() throws Exception {
		PlaygroundFactory playgroundFactory = new PlaygroundFactory();
		Player player = new Player("Test", "test", 0);
		int unitId = 1;

		Playground playground = playgroundFactory.createPlayground(Arrays.asList(player),
				"src/main/resources/onePlayer");

		assertThat(playground.getTiles().length, is(7));
		assertThat(playground.getTiles()[0].length, is(7));
		assertThat(playground.getHeadQuarter(player).getTile().getPosition(), is(new Position(3, 4)));
		assertThat(playground.getUnit(player, unitId).getPosition(), is(new Position(3, 4)));

		assertThat(playground.getTileAt(new Position(1, 1)).getResources(), is(1));
		assertThat(playground.getTileAt(new Position(5, 1)).getResources(), is(2));
		assertThat(playground.getTileAt(new Position(1, 4)).getResources(), is(3));
		assertThat(playground.getTileAt(new Position(6, 5)).getResources(), is(4));
	}

	@Test
	public void createPlaygroundFromFile_twoPlayers() throws Exception {
		PlaygroundFactory playgroundFactory = new PlaygroundFactory();
		Player player1 = new Player("Test1", "test", 0);
		Player player2 = new Player("Test2", "test", 0);
		int unitIdOfPlayer1 = 1;
		int unitIdOfPlayer2 = 2;

		Playground playground = playgroundFactory.createPlayground(Arrays.asList(player1, player2),
				"src/main/resources/twoPlayers");

		assertThat(playground.getTiles().length, is(7));
		assertThat(playground.getTiles()[0].length, is(7));
		assertThat(playground.getHeadQuarter(player1).getTile().getPosition(), is(new Position(3, 1)));
		assertThat(playground.getUnit(player1, unitIdOfPlayer1).getPosition(), is(new Position(3, 1)));

		assertThat(playground.getHeadQuarter(player2).getTile().getPosition(), is(new Position(3, 4)));
		assertThat(playground.getUnit(player2, unitIdOfPlayer2).getPosition(), is(new Position(3, 4)));

		assertThat(playground.getTileAt(new Position(1, 1)).getResources(), is(1));
		assertThat(playground.getTileAt(new Position(5, 1)).getResources(), is(2));
		assertThat(playground.getTileAt(new Position(1, 4)).getResources(), is(3));
		assertThat(playground.getTileAt(new Position(6, 5)).getResources(), is(4));
	}

	@Test
	public void createPlaygroundFromFile_twoPlayers_notEnoughPlayers() throws Exception {
		PlaygroundFactory playgroundFactory = new PlaygroundFactory();
		Player player1 = new Player("Test1", "test", 0);
		int unitIdOfPlayer1 = 1;

		Playground playground = playgroundFactory.createPlayground(Arrays.asList(player1),
				"src/main/resources/twoPlayers");

		assertThat(playground.getTiles().length, is(7));
		assertThat(playground.getTiles()[0].length, is(7));
		assertThat(playground.getHeadQuarter(player1).getTile().getPosition(), is(new Position(3, 1)));
		assertThat(playground.getUnit(player1, unitIdOfPlayer1).getPosition(), is(new Position(3, 1)));

		assertThat(playground.getTileAt(new Position(1, 1)).getResources(), is(1));
		assertThat(playground.getTileAt(new Position(5, 1)).getResources(), is(2));
		assertThat(playground.getTileAt(new Position(1, 4)).getResources(), is(3));
		assertThat(playground.getTileAt(new Position(6, 5)).getResources(), is(4));
	}
}
