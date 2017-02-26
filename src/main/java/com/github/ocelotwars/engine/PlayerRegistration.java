package com.github.ocelotwars.engine;

public class PlayerRegistration {

    private String clientUrl;
    private String clientName;

    public PlayerRegistration(String clientUrl, String clientName) {
        this.clientUrl = clientUrl;
        this.clientName = clientName;
    }

    public String getName() {
        return clientName;
    }

    public String getUrl() {
        return clientUrl;
    }

}
