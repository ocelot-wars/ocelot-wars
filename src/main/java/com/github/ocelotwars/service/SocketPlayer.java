package com.github.ocelotwars.service;

import com.github.ocelotwars.engine.Player;
import io.vertx.core.http.ServerWebSocket;

public class SocketPlayer {

    private Player player;
    private ServerWebSocket socket;
    private OutFactory out;

    public SocketPlayer(String name, ServerWebSocket socket) {
        this.player = new Player(name);
        this.socket = socket;
        this.out = new OutFactory(player);
    }

    public Player getPlayer() {
        return player;
    }

    public String getName() {
        return player.getName();
    }

    public ServerWebSocket getSocket() {
        return socket;
    }

    public OutFactory getOut() {
        return out;
    }
}
