package com.github.ocelotwars.engine;

public interface Command {

    public static final Command NULL = new Command() {
        @Override
        public void execute(Playground playground) {
        }
    };

    void execute(Playground playground);

}
