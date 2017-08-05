package com.github.ocelotwars.engine;

import static com.github.ocelotwars.engine.PlaygroundBuilder.playground;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class GameTest {

	@Test
	public void initialize() {
		PlaygroundBuilder builder = playground().withHeight(32).withWidth(32);
		Game game = new Game(builder);

		Playground playground = game.initialize();
		assertThat(playground, notNullValue());
	}

	// initialize erstellt playground mit Headquarter, Units, Resource -> evtl. in
	// separatem PlaygroundBuilder

	// Game kann Commands verarbeiten

	// Game hat Endbedingung

}
