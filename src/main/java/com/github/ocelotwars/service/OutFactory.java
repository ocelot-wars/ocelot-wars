package com.github.ocelotwars.service;

import static java.util.stream.Collectors.toList;

import java.util.ArrayList;
import java.util.List;

import com.github.ocelotwars.engine.Player;

public class OutFactory {

    public OutFactory(Player player) {
    }
    
    public List<List<Tile>> convertTiles(com.github.ocelotwars.engine.Tile[][] tiles) {
        List<List<Tile>> convertedTiles = new ArrayList<>(tiles.length);
        for (int i = 0; i < tiles.length; i++) {
            convertedTiles.add(convertTiles(tiles[i]));
        }
        return convertedTiles;
    }

    public List<Tile> convertTiles(com.github.ocelotwars.engine.Tile[] tiles) {
        List<Tile> convertedTiles = new ArrayList<>(tiles.length);
        for (int i = 0; i < tiles.length; i++) {
            convertedTiles.add(convertTile(tiles[i]));
        }
        return convertedTiles;
    }

    public Tile convertTile(com.github.ocelotwars.engine.Tile tile) {
        return new Tile(tile.getResources(), convertAssets(tile.getAssets()));
    }

    public List<Asset> convertAssets(List<com.github.ocelotwars.engine.Asset> assets) {
        return assets.stream()
            .map(this::convertAsset)
            .collect(toList());
    }

    public Asset convertAsset(com.github.ocelotwars.engine.Asset asset) {
        if (asset instanceof com.github.ocelotwars.engine.Unit) {
            return new Unit(((com.github.ocelotwars.engine.Unit) asset).getId(), ((com.github.ocelotwars.engine.Unit) asset).getLoad());
        } else if (asset instanceof com.github.ocelotwars.engine.Headquarter) {
            return new Headquarter(((com.github.ocelotwars.engine.Headquarter) asset).getResources());
        }
        return null;
    }

}
