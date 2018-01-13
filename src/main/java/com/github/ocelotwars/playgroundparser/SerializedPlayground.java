package com.github.ocelotwars.playgroundparser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import com.github.ocelotwars.engine.Position;

public class SerializedPlayground {
    private List<List<String>> tiles;

    public SerializedPlayground(String pathToFile) {
        File file = new File(pathToFile);
        Path path = file.toPath();
        try {
            setTiles(Files.lines(path).map(line -> line.split("\\|")).map(Arrays::asList).collect(Collectors.toList()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<List<String>> getTiles() {
        return tiles;
    }

    public void setTiles(List<List<String>> tiles) {
        this.tiles = tiles;
    }

    public int getHeight() {
        return tiles.size();
    }

    public int getWidth() {
        return tiles.get(0).size();
    }

    String getTileAtPosition(Position position) {
        return tiles.get(position.y).get(position.x);
    }

    public boolean hasHeadquarterAtPosition(Position position) {
        return getTileAtPosition(position).equals("H");
    }

}
