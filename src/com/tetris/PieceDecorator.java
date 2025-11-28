package com.tetris;

import javafx.scene.canvas.GraphicsContext;
import java.util.List;

public abstract class PieceDecorator implements PieceComponent {
    protected final PieceComponent decoratedPiece; // name used by your decorators

    public PieceDecorator(PieceComponent decoratedPiece) {
        this.decoratedPiece = decoratedPiece;
    }

    @Override public void render(GraphicsContext gc, int cellSize) { decoratedPiece.render(gc, cellSize); }
    @Override public List<Position> getAbsoluteBlocks() { return decoratedPiece.getAbsoluteBlocks(); }
    @Override public BlockType getType() { return decoratedPiece.getType(); }
    @Override public String getColor() { return decoratedPiece.getColor(); }
    @Override public Position getPosition() { return decoratedPiece.getPosition(); }
    @Override public List<Position> getBlocks() { return decoratedPiece.getBlocks(); }
    @Override public PieceState getState() { return decoratedPiece.getState(); }
    @Override public void setState(PieceState s) { decoratedPiece.setState(s); }

    @Override public int getScoreMultiplier() { return decoratedPiece.getScoreMultiplier(); }
    @Override public double getFallSpeedMultiplier() { return decoratedPiece.getFallSpeedMultiplier(); }
    @Override public String getDescription() { return decoratedPiece.getDescription(); }

    @Override public abstract PieceComponent clone();
}
