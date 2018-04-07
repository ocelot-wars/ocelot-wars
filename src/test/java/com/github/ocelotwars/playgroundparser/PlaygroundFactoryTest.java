package com.github.ocelotwars.playgroundparser;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import com.github.ocelotwars.engine.Player;
import com.github.ocelotwars.engine.Playground;
import com.github.ocelotwars.engine.Position;

public class PlaygroundFactoryTest {

    @Test
    public void createPlaygroundFromFile_onePlayer() throws Exception {
        PlaygroundFactory playgroundFactory = new PlaygroundFactory("src/main/resources/onePlayer");
        Player player = new Player("Test");
        int unitId = 1;

        Playground playground = playgroundFactory.createPlayground(asList(player));

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
        PlaygroundFactory playgroundFactory = new PlaygroundFactory("src/main/resources/twoPlayers");
        Player player1 = new Player("Test1");
        Player player2 = new Player("Test2");
        int unitIdOfPlayer1 = 1;
        int unitIdOfPlayer2 = 2;

        Playground playground = playgroundFactory.createPlayground(asList(player1, player2));

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
        PlaygroundFactory playgroundFactory = new PlaygroundFactory("src/main/resources/twoPlayers");
        Player player1 = new Player("Test1");
        int unitIdOfPlayer1 = 1;

        Playground playground = playgroundFactory.createPlayground(asList(player1));

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
