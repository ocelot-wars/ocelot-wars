package com.github.ocelotwars.service;

import static java.util.stream.Collectors.toList;
import java.util.Arrays;
import java.util.List;
import com.github.ocelotwars.service.report.Asset;
import com.github.ocelotwars.service.report.Headquarter;
import com.github.ocelotwars.service.report.Playground;
import com.github.ocelotwars.service.report.Tile;
import com.github.ocelotwars.service.report.Unit;

public class ServiceFacade {

    public Playground convertPlayground(com.github.ocelotwars.engine.Playground playgroundSrc) {
        Playground playground = new Playground();
        playground.setTiles(convertTileMatrix(playgroundSrc.getTiles()));
        return playground;
    }

    public List<List<Tile>> convertTileMatrix(com.github.ocelotwars.engine.Tile[][] tiles) {
        return Arrays.stream(tiles)
            .map(this::convertTileArray)
            .collect(toList());
    }

    public List<Tile> convertTileArray(com.github.ocelotwars.engine.Tile[] tiles) {
        return Arrays.stream(tiles)
            .map(this::convertTile)
            .collect(toList());
    }

    public Tile convertTile(com.github.ocelotwars.engine.Tile tileSrc) {
        Tile tile = new Tile();
        tile.setAssets(convertAssets(tileSrc.getAssets()));
        return tile;
    }

    public List<Asset> convertAssets(List<com.github.ocelotwars.engine.Asset> assets) {
        return assets.stream()
            .map(this::convertAsset)
            .collect(toList());
    }

    public Asset convertAsset(com.github.ocelotwars.engine.Asset assetSrc) {
        if (assetSrc instanceof com.github.ocelotwars.engine.Unit) {
            com.github.ocelotwars.engine.Unit unitSrc = (com.github.ocelotwars.engine.Unit) assetSrc;
            Unit unit = new Unit();
            unit.setPlayer(unitSrc.getOwner().getName());
            unit.setId(unitSrc.getId());
            unit.setLoad(unitSrc.getLoad());
            return unit;
        } else if (assetSrc instanceof com.github.ocelotwars.engine.Headquarter) {
            com.github.ocelotwars.engine.Headquarter hqSrc = (com.github.ocelotwars.engine.Headquarter) assetSrc;
            Headquarter hq = new Headquarter();
            hq.setPlayer(hqSrc.getOwner().getName());
            hq.setResources(hqSrc.getResources());
            return hq;
        } else {
            return null;
        }
    }

}
