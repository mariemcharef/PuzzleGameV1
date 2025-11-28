package com.puzzle;

public interface Command {
    boolean canExecute();
    void execute();
}
