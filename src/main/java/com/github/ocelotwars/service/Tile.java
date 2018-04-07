package com.github.ocelotwars.service;

import java.util.List;

public class Tile {

    private int resources;
    private List<Asset> assets;

    public Tile(int resources, List<Asset> assets) {
        this.resources = resources;
        this.assets = assets;
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
