package com.puzzle;

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
