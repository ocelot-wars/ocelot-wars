package com.github.ocelotwars.service;

import static io.netty.handler.codec.http.HttpResponseStatus.INTERNAL_SERVER_ERROR;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;

import java.util.ArrayList;
import java.util.List;

import com.github.ocelotwars.engine.Player;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.http.HttpServer;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

public class Host extends AbstractVerticle {

    private List<Player> players;

    public Host() {
        players = new ArrayList<>();
    }

    public void start() {
        Router router = Router.router(vertx);
        router.route("/register/:name/:host/:port").handler(this::register);
        router.route().failureHandler(this::fail);

        HttpServer server = vertx.createHttpServer();
        server.requestHandler(router::accept).listen(8080);
    }

    public void register(RoutingContext context) {
        HttpServerRequest request = context.request();
        String name = request.getParam("name");
        String host = request.getParam("host");
        int port = Integer.parseInt(request.getParam("port"));
        players.removeIf(player -> player.getHost().equals(host) && player.getPort() == port);
        players.add(new Player(name, host, port));

        context.response()
            .setStatusCode(OK.code())
            .end();
    }

    public void fail(RoutingContext context) {

        context.response()
            .setStatusCode(INTERNAL_SERVER_ERROR.code())
            .end();
    }

    public List<Player> getPlayers() {
        return players;
    }
}
