package com.github.ocelotwars.service;

import static rx.Observable.timer;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.ServerWebSocket;
import io.vertx.core.http.WebSocketFrame;
import rx.subjects.PublishSubject;

public class Host extends AbstractVerticle {

    private ObjectMapper mapper;
    private PublishSubject<SocketMessage> mq;
    private GameControl gameControl;

    public Host() {
        mq = PublishSubject.create();
        mapper = new ObjectMapper();
        timer(5, TimeUnit.SECONDS)
            .map(value -> new SocketMessage(new Start()))
            .subscribe(msg -> mq.onNext(msg));
        gameControl = new GameControl(mq);
        gameControl.init();
    }

    @Override
    public void start() {
        HttpServer server = vertx.createHttpServer();
        server.websocketHandler(this::websocket).listen(8080);
        mq.subscribe(
            msg -> System.out.println("mq: " + msg.getMessage().getClass().getSimpleName()),
            e -> System.out.println("mq error " + e.getClass().getSimpleName()),
            () -> {
                System.out.println("mq completed");
            });
    }

    public void websocket(ServerWebSocket socket) {
        socket.frameHandler(frame -> message(frame, socket));
    }

    public void message(WebSocketFrame frame, ServerWebSocket socket) {
        String text = frame.textData();
        try {
            Message msg = fromJson(text);
            System.out.println("message:" + text + "->" + msg.getClass().getName());
            mq.onNext(new SocketMessage(socket, msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Message fromJson(String message) throws IOException {
        return mapper.readValue(message, Message.class);
    }

}
