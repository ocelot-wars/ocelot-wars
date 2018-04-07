package com.github.ocelotwars.engine.game;

import java.util.List;
import com.github.ocelotwars.engine.Command;
import com.github.ocelotwars.engine.Playground;

public class Game {

    private Playground playground;

    public Game(Playground playground) {
        this.playground = playground;
    }
    
    public Playground getPlayground() {
        return playground;
    }

    public void execute(List<Command> commands) {
        commands.forEach(command -> command.execute(playground));
    }

}
