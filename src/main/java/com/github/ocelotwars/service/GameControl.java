package com.github.ocelotwars.service;

import java.util.List;

import rx.Subscriber;
import rx.subjects.PublishSubject;

public class GameControl extends Subscriber<GameControlMessage> {

	private PublishSubject<SocketMessage> mq;
	private PlayerRegistry allPlayers;

	public GameControl(PublishSubject<SocketMessage> mq) {
		this.mq = mq;
		this.allPlayers = new PlayerRegistry();
	}

	public void init() {
		this.mq
			.map(msg -> msg.getMessage())
			.filter(msg -> msg instanceof GameControlMessage)
			.cast(GameControlMessage.class)
			.subscribe(this);
		this.mq
			.filter(msg -> msg.getMessage() instanceof Register)
			.subscribe(msg -> allPlayers.register(((Register) msg.getMessage()).getPlayerName(), msg.getSocket()));
	}

	@Override
	public void onCompleted() {
	}

	@Override
	public void onError(Throwable e) {
	}

	@Override
	public void onNext(GameControlMessage msg) {
		if (msg instanceof Start) {
			start();
		} else if (msg instanceof Run) {
			run(((Run) msg).getPlayers());
		} else if (msg instanceof Stop) {
			stop();
		}
	}

	private void start() {
		Invitation invitation = new Invitation(allPlayers.getPlayers(), 10)
			.invitePlayers(mq);
		invitation.confirmedPlayers()
			.subscribe(players -> mq.onNext(new SocketMessage(new Run(players))));

	}

	private void run(List<SocketPlayer> players) {
		GameSession session = new GameSession(players, 1)
			.rounds(10, mq);
		session.winner()
			.subscribe(player -> mq.onNext(new SocketMessage(new Stop(player))));
	}

	private void stop() {
		mq.onNext(new SocketMessage(new Start()));
	}

}
