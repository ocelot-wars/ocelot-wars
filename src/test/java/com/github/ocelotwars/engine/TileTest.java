package com.github.ocelotwars.engine;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class TileTest {

    private Player player1 = new Player("1", "192.168.0.10");
    private Player player2 = new Player("2", "192.168.0.11");

    @Test
    public void testHasHeadquarterIsFalseForNoHq() throws Exception {
        Tile tile = new Tile(new Position(4, 4));

        assertThat(tile.hasHeadquarter(null), is(false));
    }

    @Test
    public void testHasHeadquarterIsTrueOnUnspecificQuery() throws Exception {
        Tile tile = new Tile(new Position(4, 4));
        tile.addHeadquarter(new Headquarter(player1));

        assertThat(tile.hasHeadquarter(null), is(true));
    }

    @Test
    public void testHasHeadquarterIsTrueOnMatchingQuery() throws Exception {
        Tile tile = new Tile(new Position(4, 4));
        tile.addHeadquarter(new Headquarter(player1));

        assertThat(tile.hasHeadquarter(player1), is(true));
    }

    @Test
    public void testHasHeadquarterIsFalseOnUnmatchedQuery() throws Exception {
        Tile tile = new Tile(new Position(4, 4));
        tile.addHeadquarter(new Headquarter(player1));

        assertThat(tile.hasHeadquarter(player2), is(false));
    }

}
