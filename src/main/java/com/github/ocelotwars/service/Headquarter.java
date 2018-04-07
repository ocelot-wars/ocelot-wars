package com.github.ocelotwars.service;

public class Headquarter extends Asset {

    private int resources;

    public Headquarter(int resources) {
        this.resources = resources;
    }

    public int getResources() {
        return resources;
    }

    public void setResources(int resources) {
        this.resources = resources;
    }

}
