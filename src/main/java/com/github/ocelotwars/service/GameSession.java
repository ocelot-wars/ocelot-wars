package com.github.ocelotwars.service;

import static java.util.stream.Collectors.toList;
import static rx.Observable.timer;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.TimeUnit;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.ocelotwars.engine.Player;
import com.github.ocelotwars.engine.Playground;
import com.github.ocelotwars.engine.command.GatherCommand;
import com.github.ocelotwars.engine.command.MoveCommand;
import com.github.ocelotwars.engine.command.UnloadCommand;
import com.github.ocelotwars.engine.game.Game;
import com.github.ocelotwars.service.commands.Command;
import com.github.ocelotwars.service.commands.Direction;
import com.github.ocelotwars.service.commands.Gather;
import com.github.ocelotwars.service.commands.Move;
import com.github.ocelotwars.service.commands.Unload;

import io.vertx.core.http.ServerWebSocket;
import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.ReplaySubject;

public class GameSession {

	private int time;
	private ObjectMapper mapper;

	private Observable<SocketPlayer> players;
	private PublishSubject<Integer> nextRound;
	private Observable<Integer> rounds;
	private PublishSubject<SocketPlayer> winner;
	private Game game;

	public GameSession(List<SocketPlayer> players, int time) {
		this.time = time;
		this.mapper = new ObjectMapper();
		this.players = Observable.from(players);
		this.nextRound = PublishSubject.create();
		this.rounds = nextRound.take(100);
		this.winner = PublishSubject.create();
		this.game = new Game(new Playground());
	}

	private String json(OutMessage msg) {
		try {
			return mapper.writeValueAsString(msg);
		} catch (IOException e) {
			return null;
		}
	}

	public void notifyPlayers() {
		players
			.subscribe(player -> notify(player));
	}

	private void notify(SocketPlayer player) {
		ServerWebSocket socket = player.getSocket();
		socket.writeFinalTextFrame(json(new Notify()));
	}

	public GameSession rounds(int no, Observable<SocketMessage> mq) {
		rounds.subscribe(round -> round(round, mq));
		rounds.doOnCompleted(() -> {
			players.first().subscribe(player -> winner.onNext(player));
		});
		nextRound.onNext(0);
		return this;
	}

	private void round(int round, Observable<SocketMessage> mq) {
		ReplaySubject<SocketPlayer> done = ReplaySubject.create();
		timer(time, TimeUnit.SECONDS)
			.first()
			.subscribe(time -> done.onCompleted());
		mq
			.filter(msg -> msg.getMessage() instanceof Commands)
			.subscribe(msg -> doCommands(((Commands) msg.getMessage()).getCommands(), msg.getSocket(), done));
		done.doOnCompleted(() -> nextRound.onNext(round + 1));

		notifyPlayers();
	}

	private List<com.github.ocelotwars.engine.Command> convertCommands(SocketPlayer player, List<Command> commands) {
		return commands.stream()
			.map(command -> convertCommand(command, player.getPlayer()))
			.collect(toList());
	}

	private com.github.ocelotwars.engine.Command convertCommand(Command command, Player player) {
		if (command instanceof Gather) {
			return new GatherCommand(player, command.getUnitId());
		} else if (command instanceof Move) {
			Direction direction = ((Move) command).getDirection();
			com.github.ocelotwars.engine.Direction dir = com.github.ocelotwars.engine.Direction.valueOf(direction.name());
			return new MoveCommand(player, command.getUnitId(), dir);
		} else if (command instanceof Unload) {
			return new UnloadCommand(player, command.getUnitId());
		} else {
			return null;
		}
	}

	public void doCommands(List<Command> commands, ServerWebSocket socket, ReplaySubject<SocketPlayer> done) {
		done
			.exists(p -> p.getSocket() == socket)
			.subscribe(existing -> {
				if (!existing) {
					players
						.filter(p -> p.getSocket() == socket)
						.subscribe(p -> {
							game.execute(convertCommands(p, commands));
							done.onNext(p);
						});
				}
			});
	}

	public Observable<SocketPlayer> winner() {
		return winner;
	}

}
