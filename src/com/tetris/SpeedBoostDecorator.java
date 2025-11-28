package com.tetris;

public class SpeedBoostDecorator implements Command {
    private Command wrappedCommand;
    
    public SpeedBoostDecorator(Command wrappedCommand) {
        this.wrappedCommand = wrappedCommand;
    }
    
    @Override
    public boolean canExecute() {
        return wrappedCommand.canExecute();
    }
    
    @Override
    public void execute() {
        while (wrappedCommand.canExecute()) {
            wrappedCommand.execute();
        }
    }
}