package com.github.ocelotwars.engine;

public class Player {
	private int id;
	private String name;
    private String url;
    public Player(PlayerRegistration playerRegistration) {
        this.name = playerRegistration.getName();
        this.url = playerRegistration.getUrl();
        
    }
    public String getUrl() {
        return url;
    }
    public String getName() {
        return name;
    }
}
