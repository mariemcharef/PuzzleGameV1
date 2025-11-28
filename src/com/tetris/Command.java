package com.tetris;

public interface Command {
    boolean canExecute();
    void execute();
}
