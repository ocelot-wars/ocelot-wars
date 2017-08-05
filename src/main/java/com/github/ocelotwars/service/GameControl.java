package com.github.ocelotwars.service;

import java.util.ArrayList;
import java.util.List;

import com.github.ocelotwars.engine.Player;

import rx.Observable;
import rx.Subscriber;
import rx.subjects.ReplaySubject;

public class GameControl extends Subscriber<Game> {

	private List<Player> registeredPlayers = new ArrayList<>();
	private ReplaySubject<Game> startedGame = ReplaySubject.create();

	public void register(String playerName) {
		System.out.println("registered: " + playerName);
		registeredPlayers.add(new Player(playerName));
	}

	public void unregister(String playerName) {
		if (playerName != null) {
			registeredPlayers.removeIf(player -> player.getName().equals(playerName));
		}
	}

	@Override
	public void onCompleted() {
		System.out.println("complete");
	}

	@Override
	public void onError(Throwable e) {
		System.out.println("error");
	}

	@Override
	public void onNext(Game game) {
		System.out.println("start");
		startedGame.onNext(game);
		game.start(registeredPlayers);
	}

	public Observable<Player> invites() {
		return startedGame
				.flatMap(game -> game.getPlayers());
	}

	public Observable<Game> currentGame() {
		return startedGame;
	}

}
