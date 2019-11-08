package com.github.ocelotwars.victory;

import com.github.ocelotwars.engine.Player;
import com.github.ocelotwars.engine.game.Game;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Collections;
import java.util.HashSet;
import java.util.Map;

import static org.junit.Assert.assertThat;

@RunWith(MockitoJUnitRunner.class)
class ResourceVictoryEvaluatorTest {

    private ResourceVictoryEvaluator underTest = new ResourceVictoryEvaluator();

    @Mock
    private Game game;

    @Test
    void assignPoints() {
        Player player = new Player("test");
        game = Mockito.mock(Game.class);
        Mockito.when(game.getPlayers()).thenReturn(new HashSet<>(Collections.singletonList(player)));
        Mockito.when(game.getPlayerResources(player)).thenReturn(2);

        Map<Player, Integer> result = underTest.assignPoints(game);

        assertThat(result.keySet(), Matchers.hasItem(player));
        assertThat(result.get(player), Matchers.is(2));
    }


}
