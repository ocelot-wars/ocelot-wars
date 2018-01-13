package com.github.ocelotwars.engine;

import java.util.ArrayList;
import java.util.List;

public class PlaygroundBuilder {

    private int height;
    private int width;
    private List<Headquarter> headquarters = new ArrayList<>();
    private List<Unit> units = new ArrayList<>();
    private List<Resource> resources = new ArrayList<>();

    private PlaygroundBuilder() {
    }

    public Playground create() {
        Playground playground = new Playground();
        playground.init(width, height);
        Position position = new Position(4, 16);
        headquarters.forEach(headquarter -> playground.putHeadquarter(headquarter, position));
        units.forEach(unit -> playground.putUnit(unit, position));
        resources.forEach(resource -> playground.putResource(resource.getValue(), resource.getPosition()));

        return playground;
    }

    public static PlaygroundBuilder playground() {
        return new PlaygroundBuilder();
    }

    public PlaygroundBuilder withHeight(int height) {
        this.height = height;
        return this;
    }

    public PlaygroundBuilder withWidth(int width) {
        this.width = width;
        return this;
    }

    public PlaygroundBuilder withHeadquarter(Headquarter headquarter) {
        headquarters.add(headquarter);
        return this;
    }

    public PlaygroundBuilder withUnit(Unit unit) {
        units.add(unit);
        return this;
    }

    public PlaygroundBuilder withResource(Position position, int resource) {
        resources.add(new Resource(position, resource));
        return this;
    }

}
