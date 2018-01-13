package com.github.ocelotwars.service.report;

import java.util.ArrayList;
import java.util.List;

public class Tile {

    private int resources;
    private List<Asset> assets;

    public Tile() {
        this.assets = new ArrayList<>();
    }

    public void setResources(int resources) {
        this.resources = resources;
    }

    public int getResources() {
        return resources;
    }

    public void setAssets(List<Asset> assets) {
        this.assets = assets;
    }

    public List<Asset> getAssets() {
        return assets;
    }

}
