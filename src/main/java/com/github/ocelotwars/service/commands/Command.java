package com.github.ocelotwars.service.commands;

public abstract class Command {
    private int unitId;

    public Command(int unitId) {
        this.unitId = unitId;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

}
