package com.github.ocelotwars.service;

public class Unit extends Asset {

    private int id;
    private int load;

    
    public Unit(int id, int load) {
        this.id = id;
        this.load = load;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getLoad() {
        return load;
    }

    public void setLoad(int load) {
        this.load = load;
    }
}
