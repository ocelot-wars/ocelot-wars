package com.github.ocelotwars.service;

public class Stop implements GameControlMessage {

    private SocketPlayer player;

    public Stop(SocketPlayer player) {
        this.player = player;
    }

    public SocketPlayer getPlayer() {
        return player;
    }
}
