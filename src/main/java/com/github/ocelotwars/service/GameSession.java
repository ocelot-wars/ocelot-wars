package com.github.ocelotwars.service;

import static java.util.stream.Collectors.toList;

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

	private String json(OutMessage msg) {
		try {
			return mapper.writeValueAsString(msg);
		} catch (IOException e) {
			return null;
		}
	}

	public void notifyPlayers() {
		for (SocketPlayer player : players) {
			notify(player);
		}
	}

	private void notify(SocketPlayer player) {
		ServerWebSocket socket = player.getSocket();
		socket.writeFinalTextFrame(json(new Notify()));
	}

	public GameSession rounds(int no, Observable<SocketMessage> mq) {
		for (int i = 0; i < no; i++) {
			round = round.flatMap(round -> round(round, mq));
		}
		round.subscribe(round -> {
			winner.onNext(players.get(0)); // TODO return the player that is winner
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

	public Observable<SocketPlayer> winner() {
		return winner;
	}

}
