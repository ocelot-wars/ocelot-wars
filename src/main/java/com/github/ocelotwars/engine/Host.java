package com.github.ocelotwars.engine;

import java.util.ArrayList;
import java.util.List;

public class Host {

    private List<Player> players = new ArrayList<>();

    public Player registerPlayer(PlayerRegistration playerRegistration) {
        players.stream() //
                .filter(player -> player.getUrl().equals(playerRegistration.getUrl())) //
                .findAny() //
                .ifPresent(player -> {
                    throw new DuplicatePlayerException();
                });
        Player player = new Player(playerRegistration);
        players.add(player);
        return player;
    }

    public List<Player> getPlayers() {
        return players;
    }
}
