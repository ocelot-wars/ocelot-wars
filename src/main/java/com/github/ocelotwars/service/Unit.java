package com.github.ocelotwars.service;

public class Unit implements Asset {

    private int id;
    private int load;

    public Unit(int id, int load) {
        this.id = id;
        this.load = load;
    }

    public int getId() {
        return id;
    }

    public int getLoad() {
        return load;
    }
}
