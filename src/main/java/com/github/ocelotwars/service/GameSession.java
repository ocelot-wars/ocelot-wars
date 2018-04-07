package com.github.ocelotwars.service;

import static java.util.stream.Collectors.toList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ocelotwars.engine.Playground;
import com.github.ocelotwars.engine.game.Game;
import com.github.ocelotwars.service.commands.Command;
import io.vertx.core.http.ServerWebSocket;
import rx.Observable;
import rx.subjects.PublishSubject;

public class GameSession {

    private int time;
    private ObjectMapper mapper;

    private List<SocketPlayer> players;
    private Observable<Integer> round;
    private PublishSubject<SocketPlayer> winner;
    private Game game;

    public GameSession(List<SocketPlayer> players, int time) {
        this(new Game(new Playground()), players, time);
    }

    public GameSession(Game game, List<SocketPlayer> players, int time) {
        this.time = time;
        this.mapper = new ObjectMapper();
        this.players = players;
        this.round = Observable.just(0);
        this.winner = PublishSubject.create();
        this.game = game;
    }

    private String json(OutMessage msg) throws JsonProcessingException {
        return mapper.writeValueAsString(msg);
    }

    public void notifyPlayers() {
        for (SocketPlayer player : players) {
            notify(player);
        }
    }

    private void notify(SocketPlayer player) {
        ServerWebSocket socket = player.getSocket();
        try {
            socket.writeFinalTextFrame(json(new Notify(player.getOut().convertTiles(game.getPlayground().getTiles()))));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    public GameSession rounds(int no, Observable<SocketMessage> mq) {
        for (int i = 0; i < no; i++) {
            round = round.flatMap(round -> round(round, mq));
        }
        round.subscribe(round -> {
            players.stream().sorted((player1, player2) -> {
                int resources1 = game.getPlayground().getHeadQuarter(player1.getPlayer()).getResources();
                int resources2 = game.getPlayground().getHeadQuarter(player1.getPlayer()).getResources();
                return Integer.compare(resources2, resources1);
            }).findFirst().ifPresent(winner::onNext);
        });
        return this;
    }

    protected Observable<Integer> round(int round, Observable<SocketMessage> mq) {
        PublishSubject<Integer> ready = PublishSubject.create();
        mq
            .take(time, TimeUnit.SECONDS)
            .distinct(SocketMessage::getSocket)
            .filter(msg -> msg.getMessage() instanceof Commands)
            .doOnUnsubscribe(() -> ready.onNext(round + 1))
            .subscribe(msg -> executeGame(msg.getSocket(), (Commands) msg.getMessage()));
        notifyPlayers();
        return ready;
    }

    public void executeGame(ServerWebSocket socket, Commands webCommands) {
        players.stream()
            .filter(player -> player.getSocket() == socket)
            .findFirst()
            .ifPresent(webPlayer -> {
                List<com.github.ocelotwars.engine.Command> commands = convertCommands(webPlayer, webCommands.getCommands());
                game.execute(commands);
            });
    }

    private List<com.github.ocelotwars.engine.Command> convertCommands(SocketPlayer player, List<Command> commands) {
        InFactory commandFactory = new InFactory(player.getPlayer());
        return commands.stream()
            .map(commandFactory::convertCommand)
            .collect(toList());
    }

    public Observable<SocketPlayer> winner() {
        return winner;
    }

}
