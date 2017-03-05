package com.github.ocelotwars.engine;

public class Player {

    private String name;
    private String url;

    public Player(PlayerRegistration playerRegistration) {
        this.name = playerRegistration.getName();
        this.url = playerRegistration.getUrl();
    }

    public Player(String name, String url) {
        this.name = name;
        this.url = url;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }
}
