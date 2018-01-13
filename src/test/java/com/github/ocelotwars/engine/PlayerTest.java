package com.github.ocelotwars.engine;

import static com.almondtools.conmatch.conventions.EqualityMatcher.satisfiesDefaultEquality;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class PlayerTest {

    @Test
    public void testEqualsHashCode() throws Exception {
        assertThat(new Player("name"), satisfiesDefaultEquality()
            .andEqualTo(new Player("name"))
            .andNotEqualTo(new Player("newname")));
    }

}
