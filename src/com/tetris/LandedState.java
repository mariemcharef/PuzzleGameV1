package com.tetris;
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