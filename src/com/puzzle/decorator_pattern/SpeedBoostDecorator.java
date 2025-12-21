package com.puzzle.decorator_pattern;

import com.puzzle.GameLogger;
import com.puzzle.command_pattern.Command;

public class SpeedBoostDecorator implements Command {
    private final Command wrappedCommand;

    public SpeedBoostDecorator(Command wrappedCommand) {
        this.wrappedCommand = wrappedCommand;
        GameLogger.getInstance().logDecoratorApplied("SpeedBoost");
    }

    @Override
    public boolean canExecute() {
        return wrappedCommand.canExecute() ;
    }

    @Override
    public void execute() {
        while (wrappedCommand.canExecute() ) {
            wrappedCommand.execute();
        }
    }
}