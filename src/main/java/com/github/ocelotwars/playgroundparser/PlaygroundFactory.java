package com.github.ocelotwars.playgroundparser;

import com.github.ocelotwars.engine.Player;
import com.github.ocelotwars.engine.Playground;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

public class PlaygroundFactory {

    private List<List<String>> tiles;

    public PlaygroundFactory(String pathToFile) throws IOException {
        tiles = Files.lines(Paths.get(pathToFile))
            .map(line -> line.split("\\|"))
            .map(Arrays::asList)
            .collect(toList());
    }

    public Playground createPlayground(List<Player> players)  {
        return PlaygroundBuilder.builder(tiles)
            .withPlayers(players)
            .build();
    }

}
