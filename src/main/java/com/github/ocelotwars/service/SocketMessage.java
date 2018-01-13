package com.github.ocelotwars.service;

import io.vertx.core.http.ServerWebSocket;

public class SocketMessage {

    private ServerWebSocket socket;
    private Message message;

    public SocketMessage(ServerWebSocket socket, Message message) {
        this.socket = socket;
        this.message = message;
    }

    public SocketMessage(Message message) {
        this.message = message;
    }

    public ServerWebSocket getSocket() {
        return socket;
    }

    public Message getMessage() {
        return message;
    }

}
