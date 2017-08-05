package com.github.ocelotwars.engine;

import static java.lang.Integer.valueOf;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class PlaygroundFactory {

	public Playground getOnePlayerPlayground(Player player) {
		File file = new File("src/main/resources/onePlayer");
		Path path = file.toPath();
		Playground playground = null;
		try {
			List<List<String>> tiles = Files.lines(path).map(line -> line.split("\\|")).map(Arrays::asList)
					.collect(Collectors.toList());

			playground = new Playground();
			int height = tiles.size();
			int width = tiles.get(0).size();
			playground.init(width, height);
			fillPlayground(tiles, player, playground);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return playground;
	}

	private void fillPlayground(List<List<String>> tiles, Player player, Playground playground) {
		Headquarter headquarter = new Headquarter(player);
		for (int y = 0; y < tiles.size(); y++) {
			for (int x = 0; x < tiles.get(y).size(); x++) {
				Position position = new Position(x, y);
				if (tiles.get(y).get(x).equals("H")) {
					playground.putHeadquarter(headquarter, position);
					playground.putUnit(new Unit(player, 1), position);
				} else {
					playground.putResource(valueOf(tiles.get(y).get(x)), position);
				}
			}
		}

	}

}
