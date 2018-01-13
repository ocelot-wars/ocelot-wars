package com.github.ocelotwars.service;

import static org.hamcrest.Matchers.instanceOf;
import static org.junit.Assert.assertThat;
import org.junit.Test;

public class HostTest {

    @Test
    public void testMapMessage() throws Exception {
        Message register = new Host().mapMessage("{\"@type\":\"register\"}");
        assertThat(register, instanceOf(Register.class));
    }

}
