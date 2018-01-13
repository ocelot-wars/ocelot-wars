package com.github.ocelotwars.engine;

public abstract class Asset {

    private Player owner;

    public Asset(Player owner) {
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

}
