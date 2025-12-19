package com.puzzle.state_pattern;


public abstract class PieceState {
    public abstract boolean canMove();
    public abstract boolean canRotate();
}
