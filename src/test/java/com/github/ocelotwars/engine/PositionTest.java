package com.github.ocelotwars.engine;

import static com.almondtools.conmatch.conventions.EqualityMatcher.satisfiesDefaultEquality;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class PositionTest {

    @Test
    public void testEqualsHashcode() throws Exception {
        assertThat(new Position(1, 2), satisfiesDefaultEquality()
            .andEqualTo(new Position(1, 2))
            .andNotEqualTo(new Position(2, 2))
            .andNotEqualTo(new Position(1, 1))
            .andNotEqualTo(new Position(2, 1)));
    }

    @Test
    public void testNormalize() throws Exception {
        assertThat(new Position(10, 10).normalize(4, 7), equalTo(new Position(2, 3)));
    }

    @Test
    public void testNormalizeOnNegativePositions() throws Exception {
        assertThat(new Position(-10, -10).normalize(4, 7), equalTo(new Position(2, 4)));
    }
}
