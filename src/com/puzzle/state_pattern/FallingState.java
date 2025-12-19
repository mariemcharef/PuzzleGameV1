package com.puzzle.state_pattern;

public class FallingState extends PieceState {
    @Override
    public boolean canMove() { 
        return true; 
    }
    
    @Override
    public boolean canRotate() { 
        return true; 
    }
}
