package com.github.ocelotwars.engine;

import static com.almondtools.conmatch.conventions.EqualityMatcher.satisfiesDefaultEquality;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class PlayerTest {

    @Test
    public void testEqualsHashCode() throws Exception {
        assertThat(new Player("name", "localhost", 1234), satisfiesDefaultEquality()
            .andEqualTo(new Player("name", "localhost", 1234))
            .andNotEqualTo(new Player("newname", "localhost", 1234))
            .andNotEqualTo(new Player("name", "192.168.0.1", 1234))
            .andNotEqualTo(new Player("name", "localhost", 8080))
            .andNotEqualTo(new Player(null, null, 0)));
    }

}
