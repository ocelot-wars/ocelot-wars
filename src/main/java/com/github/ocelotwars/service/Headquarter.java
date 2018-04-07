package com.github.ocelotwars.service;

public class Headquarter implements Asset {

    private int resources;

    public Headquarter(int resources) {
        this.resources = resources;
    }

    public int getResources() {
        return resources;
    }

}
