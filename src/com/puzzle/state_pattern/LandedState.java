package com.puzzle.state_pattern;

public class LandedState extends PieceState {
    @Override
    public boolean canMove() { 
        return false; 
    }
    
    @Override
    public boolean canRotate() { 
        return false; 
    }
}