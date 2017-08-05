package com.github.ocelotwars.service;

import java.util.List;

import com.github.ocelotwars.engine.Player;

import rx.Observable;
import rx.subjects.PublishSubject;

public class Game {

	private PublishSubject<Player> players = PublishSubject.create();

	public void start(List<Player> players) {
		for (Player player :players) {
			this.players.onNext(player);
		}
		this.players.onCompleted();
	}
	
	public Observable<Player> getPlayers() {
		return players;
	}

	public void accept(String playerName) {
		System.out.println("accepting " + playerName);
	}

}
