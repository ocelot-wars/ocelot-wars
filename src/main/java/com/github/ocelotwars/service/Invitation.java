package com.github.ocelotwars.service;

import static rx.Observable.timer;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.http.ServerWebSocket;
import rx.Observable;
import rx.subjects.PublishSubject;

public class Invitation {

    private ObjectMapper mapper;

    private Observable<SocketPlayer> players;
    private PublishSubject<SocketPlayer> confirmed;

    public Invitation(List<SocketPlayer> players, int time) {
        this.mapper = new ObjectMapper();
        this.players = Observable.from(players);
        this.confirmed = PublishSubject.create();
        timer(time, TimeUnit.SECONDS)
            .first()
            .subscribe(t -> confirmed.onCompleted());
    }

    private String json(OutMessage msg) throws JsonProcessingException {
        return mapper.writeValueAsString(msg);
    }

    public Invitation invitePlayers(Observable<SocketMessage> mq) {
        mq
            .filter(msg -> msg.getMessage() instanceof Accept)
            .subscribe(accept -> confirm(accept.getSocket()));
        players
            .subscribe(player -> invite(player));

        return this;
    }

    private void invite(SocketPlayer player) {
        ServerWebSocket socket = player.getSocket();
        try {
            socket.writeFinalTextFrame(json(new Invite()));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public void confirm(ServerWebSocket socket) {
        players
            .filter(player -> player.getSocket() == socket)
            .subscribe(player -> confirmed.onNext(player));
    }

    public Observable<List<SocketPlayer>> confirmedPlayers() {
        return confirmed.toList();
    }

}
