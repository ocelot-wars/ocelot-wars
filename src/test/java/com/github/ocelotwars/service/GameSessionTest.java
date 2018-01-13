package com.github.ocelotwars.service;

import static java.util.Collections.singletonList;
import static org.hamcrest.Matchers.notNullValue;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import com.github.ocelotwars.engine.Player;
import com.github.ocelotwars.engine.command.GatherCommand;
import com.github.ocelotwars.engine.command.MoveCommand;
import com.github.ocelotwars.engine.game.Game;
import com.github.ocelotwars.service.commands.Direction;
import com.github.ocelotwars.service.commands.Gather;
import com.github.ocelotwars.service.commands.Move;
import com.github.ocelotwars.service.commands.Unload;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnit;
import org.mockito.junit.MockitoRule;
import io.vertx.core.http.ServerWebSocket;
import rx.Observable;
import rx.plugins.RxJavaHooks;
import rx.schedulers.TestScheduler;
import rx.subjects.PublishSubject;

public class GameSessionTest {

    @Rule
    public MockitoRule mockitoJUnit = MockitoJUnit.rule();

    private TestScheduler scheduler;

    @Mock
    private Game game;

    private List<SocketPlayer> players = new ArrayList<>();

    @Before
    public void before() {
        scheduler = new TestScheduler();
        RxJavaHooks.setOnComputationScheduler(d -> scheduler);
    }

    @Test
    public void testRound_CommandsMove() throws Exception {
        ServerWebSocket socket1 = Mockito.mock(ServerWebSocket.class);
        players.add(new SocketPlayer("player1", socket1));
        Commands commands = new Commands(singletonList(new Move(1, Direction.EAST)));
        Observable<SocketMessage> mq = Observable.just(new SocketMessage(socket1, commands));
        GameSession session = new GameSession(game, players, 5);

        session.round(0, mq);

        verify(game).execute(
            singletonList(new MoveCommand(new Player("player1"), 1, com.github.ocelotwars.engine.Direction.EAST)));
    }

    @Test
    public void testRound_CommandsGather() throws Exception {
        ServerWebSocket socket1 = Mockito.mock(ServerWebSocket.class);
        players.add(new SocketPlayer("player1", socket1));
        Commands commands = new Commands(singletonList(new Gather(1)));
        Observable<SocketMessage> mq = Observable.just(new SocketMessage(socket1, commands));
        GameSession session = new GameSession(game, players, 5);

        session.round(0, mq);

        verify(game).execute(singletonList(new GatherCommand(new Player("player1"), 1)));
    }

    @Test
    public void testRound_TwoPlayers_CommandsForPlayer1() throws Exception {
        ServerWebSocket socket1 = Mockito.mock(ServerWebSocket.class);
        ServerWebSocket socket2 = Mockito.mock(ServerWebSocket.class);
        players.add(new SocketPlayer("player1", socket1));
        players.add(new SocketPlayer("player2", socket2));
        Commands commands = new Commands(singletonList(new Gather(1)));
        Observable<SocketMessage> mq = Observable.just(new SocketMessage(socket1, commands));
        GameSession session = new GameSession(game, players, 5);

        session.round(0, mq);

        verify(game).execute(singletonList(new GatherCommand(new Player("player1"), 1)));
    }

    @Test
    public void testRound_TwoPlayers_CommandsForPlayer2() throws Exception {
        ServerWebSocket socket1 = Mockito.mock(ServerWebSocket.class);
        ServerWebSocket socket2 = Mockito.mock(ServerWebSocket.class);
        players.add(new SocketPlayer("player1", socket1));
        players.add(new SocketPlayer("player2", socket2));
        Commands commands = new Commands(singletonList(new Gather(1)));
        Observable<SocketMessage> mq = Observable.just(new SocketMessage(socket2, commands));
        GameSession session = new GameSession(game, players, 5);

        session.round(0, mq);

        verify(game).execute(singletonList(new GatherCommand(new Player("player2"), 1)));
    }

    @Test
    public void testRound_TwoPlayers_CommandsForPlayer12() throws Exception {
        ServerWebSocket socket1 = Mockito.mock(ServerWebSocket.class);
        ServerWebSocket socket2 = Mockito.mock(ServerWebSocket.class);
        players.add(new SocketPlayer("player1", socket1));
        players.add(new SocketPlayer("player2", socket2));
        Commands commands1 = new Commands(singletonList(new Move(1, Direction.NORTH)));
        Commands commands2 = new Commands(singletonList(new Gather(1)));
        Observable<SocketMessage> mq = Observable.just(new SocketMessage(socket2, commands2), new SocketMessage(socket1, commands1));
        GameSession session = new GameSession(game, players, 5);

        session.round(0, mq);

        verify(game).execute(singletonList(new MoveCommand(new Player("player1"), 1, com.github.ocelotwars.engine.Direction.NORTH)));
        verify(game).execute(singletonList(new GatherCommand(new Player("player2"), 1)));
    }

    @Test
    public void testRound_TwoPlayers_CommandsForIllegalSecondMessage_() throws Exception {
        ServerWebSocket socket1 = Mockito.mock(ServerWebSocket.class);
        ServerWebSocket socket2 = Mockito.mock(ServerWebSocket.class);
        players.add(new SocketPlayer("player1", socket1));
        players.add(new SocketPlayer("player2", socket2));
        Commands commands1 = new Commands(singletonList(new Move(1, Direction.NORTH)));
        Commands commands2 = new Commands(singletonList(new Gather(1)));
        Commands commands3 = new Commands(singletonList(new Unload(1)));
        Observable<SocketMessage> mq = Observable.just(
            new SocketMessage(socket2, commands2),
            new SocketMessage(socket1, commands1),
            new SocketMessage(socket2, commands3));
        GameSession session = new GameSession(game, players, 5);

        session.round(0, mq);

        verify(game).execute(singletonList(new MoveCommand(new Player("player1"), 1, com.github.ocelotwars.engine.Direction.NORTH)));
        verify(game).execute(singletonList(new GatherCommand(new Player("player2"), 1)));
    }

    @Test
    public void testRound_TwoPlayers_CommandsWithTimeout() throws Exception {
        ServerWebSocket socket1 = Mockito.mock(ServerWebSocket.class);
        ServerWebSocket socket2 = Mockito.mock(ServerWebSocket.class);
        players.add(new SocketPlayer("player1", socket1));
        players.add(new SocketPlayer("player2", socket2));
        Commands commands1 = new Commands(singletonList(new Move(1, Direction.NORTH)));
        Commands commands2 = new Commands(singletonList(new Gather(1)));

        Observable<SocketMessage> mq = Observable.just(new SocketMessage(socket1, commands1), new SocketMessage(socket2, commands2))
            .zipWith(Observable.interval(3, TimeUnit.SECONDS), (message, time) -> message);
        GameSession session = new GameSession(game, players, 5);

        session.round(0, mq);
        scheduler.advanceTimeBy(6, TimeUnit.SECONDS);

        verify(game).execute(singletonList(new MoveCommand(new Player("player1"), 1, com.github.ocelotwars.engine.Direction.NORTH)));
    }

    @Test
    public void testRound_notifysAllPlayers() throws Exception {
        ServerWebSocket socket1 = Mockito.mock(ServerWebSocket.class);
        ServerWebSocket socket2 = Mockito.mock(ServerWebSocket.class);
        players.add(new SocketPlayer("player1", socket1));
        players.add(new SocketPlayer("player2", socket2));
        Observable<SocketMessage> mq = Observable.empty();
        GameSession session = new GameSession(game, players, 5);

        session.round(0, mq);

        verify(socket1).writeFinalTextFrame("{\"@type\":\"Notify\"}");
        verify(socket2).writeFinalTextFrame("{\"@type\":\"Notify\"}");
    }

    @Test
    public void testRounds_2rounds_runInSequence() throws Exception {
        ServerWebSocket socket1 = Mockito.mock(ServerWebSocket.class);
        ServerWebSocket socket2 = Mockito.mock(ServerWebSocket.class);
        players.add(new SocketPlayer("player1", socket1));
        players.add(new SocketPlayer("player2", socket2));
        Commands commands1 = new Commands(singletonList(new Move(1, Direction.NORTH)));
        Commands commands2 = new Commands(singletonList(new Move(1, Direction.WEST)));

        PublishSubject<SocketMessage> mq = PublishSubject.create();

        GameSession session = new GameSession(game, players, 5);

        session.rounds(2, mq);

        scheduler.advanceTimeBy(1, TimeUnit.SECONDS);
        verify(socket1).writeFinalTextFrame("{\"@type\":\"Notify\"}");
        verify(socket2).writeFinalTextFrame("{\"@type\":\"Notify\"}");
        scheduler.advanceTimeBy(2, TimeUnit.SECONDS);
        mq.onNext(new SocketMessage(socket1, commands1));

        verify(game, times(1)).execute(
            singletonList(new MoveCommand(new Player("player1"), 1, com.github.ocelotwars.engine.Direction.NORTH)));

        Mockito.reset(game, socket1, socket2);

        scheduler.advanceTimeBy(3, TimeUnit.SECONDS);
        verify(socket1).writeFinalTextFrame("{\"@type\":\"Notify\"}");
        verify(socket2).writeFinalTextFrame("{\"@type\":\"Notify\"}");
        mq.onNext(new SocketMessage(socket2, commands2));

        verify(game, times(1)).execute(
            singletonList(new MoveCommand(new Player("player2"), 1, com.github.ocelotwars.engine.Direction.WEST)));
    }

    @Test
    public void testRounds_2rounds_terminatesWithWinner() throws Exception {
        ServerWebSocket socket1 = Mockito.mock(ServerWebSocket.class);
        ServerWebSocket socket2 = Mockito.mock(ServerWebSocket.class);
        players.add(new SocketPlayer("player1", socket1));
        players.add(new SocketPlayer("player2", socket2));
        Commands commands1 = new Commands(singletonList(new Move(1, Direction.NORTH)));
        Commands commands2 = new Commands(singletonList(new Move(1, Direction.WEST)));

        PublishSubject<SocketMessage> mq = PublishSubject.create();

        GameSession session = new GameSession(game, players, 5);
        SocketPlayer[] winner = new SocketPlayer[1];
        session.winner().subscribe(p -> {
            winner[0] = p;
        });

        session.rounds(2, mq);

        scheduler.advanceTimeBy(3, TimeUnit.SECONDS);
        mq.onNext(new SocketMessage(socket1, commands1));
        scheduler.advanceTimeBy(3, TimeUnit.SECONDS);
        mq.onNext(new SocketMessage(socket2, commands2));

        verify(game).execute(singletonList(new MoveCommand(new Player("player1"), 1, com.github.ocelotwars.engine.Direction.NORTH)));
        verify(game).execute(singletonList(new MoveCommand(new Player("player2"), 1, com.github.ocelotwars.engine.Direction.WEST)));

        assertThat(winner[0], nullValue());

        scheduler.advanceTimeBy(4, TimeUnit.SECONDS);

        assertThat(winner[0], notNullValue());
    }

}
