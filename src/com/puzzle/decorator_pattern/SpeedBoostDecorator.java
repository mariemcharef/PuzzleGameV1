package com.puzzle.decorator_pattern;

import com.puzzle.command_pattern.Command;
import com.puzzle.GameLogger;

public class SpeedBoostDecorator implements Command {
    private final Command wrappedCommand;

    public SpeedBoostDecorator(Command wrappedCommand) {
        this.wrappedCommand = wrappedCommand;
    }

    @Override
    public boolean canExecute() {
        return wrappedCommand.canExecute();
    }

    @Override
    public void execute() {
        GameLogger.getInstance().log("Decorator pattern used: SpeedBoostDecorator.execute()");
        while (wrappedCommand.canExecute()) {
            wrappedCommand.execute();
        }
    }
}
