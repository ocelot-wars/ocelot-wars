package com.github.ocelotwars.engine.gameover;

import com.github.ocelotwars.engine.game.Game;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;


@RunWith(MockitoJUnitRunner.class)
public class LessThanResourcesLeftGameOverConditionTest {

    private LessThanResourcesLeftGameOverCondition underTest = new LessThanResourcesLeftGameOverCondition(1);

    @Mock
    private Game game;

    @Test
    public void isOverPositiveCase() {
        Mockito.when(game.getFullResourceNumber()).thenReturn(0);

        boolean result = underTest.isOver(game);

        assertThat(result, is(true));
    }

    @Test
    public void isOverNegativeCase() {
        Mockito.when(game.getFullResourceNumber()).thenReturn(1);

        boolean result = underTest.isOver(game);

        assertThat(result, is(false));
    }
}
