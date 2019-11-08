package com.github.ocelotwars.engine;

import org.junit.Test;

import java.util.List;

import static com.github.ocelotwars.engine.PlaygroundBuilder.playground;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class PlaygroundBuilderTest {
    @Test
    public void createsPlayground_widthAndHeight()   {
        Playground playground = playground().withHeight(32).withWidth(32).create();

        assertThat(playground.getTiles().length, is(32));
        assertThat(playground.getTiles()[0].length, is(32));
    }

    @Test
    public void createsPlayground_HeadquarterAndUnitForPlayer()   {
        Player player = new Player("Player");
        Headquarter headquarter = new Headquarter(player);
        Unit unit = new Unit(player, 1);

        Playground playground = playground().withHeight(32).withWidth(32).withHeadquarter(headquarter).withUnit(unit)
            .create();

        assertThat(playground.getHeadQuarter(player), is(headquarter));
        assertThat(playground.getUnit(player, 1), is(unit));
    }

    @Test
    public void createsPlayground_resource() {
        Position position = new Position(28, 16);
        Playground playground = playground().withHeight(32).withWidth(32).withResource(position, 5).create();

        assertThat(playground.getTileAt(position).getResources(), is(5));
        assertThat(playground.getFullResourceNumber(),is(5));
    }

    @Test
    public void createsPlayground_getFullResourceNumber() {
        Position firstPosition = new Position(11, 7);
        Position secondPostion = new Position(28, 16);
        Playground playground = playground().withHeight(32).withWidth(32)
            .withResource(firstPosition,13)
            .withResource(secondPostion, 5)
            .create();

        assertThat(playground.getTileAt(firstPosition).getResources(), is(13));
        assertThat(playground.getTileAt(secondPostion).getResources(), is(5));
        assertThat(playground.getFullResourceNumber(),is(18));
    }

    @Test
    public void createsPlayground_getUnits() {
        Player player1 = new Player("player1");
        Player player2 = new Player("player2");
        Unit unit = new Unit(player2, 3);
        Playground playground = playground().withHeight(32).withWidth(32)
            .withUnit(new Unit(player1, 1))
            .withUnit(new Unit(player1, 2))
            .withUnit(unit)
            .create();

        List<Unit> units = playground.getUnits(player2);
        assertThat(playground.getUnits(player1), hasSize(2));
        assertThat(units, hasSize(1));
        assertThat(units.get(0), is(unit));
    }
}
