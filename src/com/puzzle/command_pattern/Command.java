package com.puzzle.command_pattern;

public interface Command {
    boolean canExecute();
    void execute();
}
