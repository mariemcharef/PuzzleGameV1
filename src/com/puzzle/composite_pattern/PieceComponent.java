package com.puzzle.composite_pattern;

import javafx.scene.canvas.GraphicsContext;
import java.util.List;

import com.puzzle.BlockType;
import com.puzzle.Position;
import com.puzzle.state_pattern.PieceState;

public interface PieceComponent {
    void render(GraphicsContext gc, int cellSize);
    List<Position> getAbsoluteBlocks();
    BlockType getType();
    String getColor();
    Position getPosition();
    List<Position> getBlocks();
    PieceState getState();
    void setState(PieceState state);
    PieceComponent clone();
    int getScoreMultiplier();
    double getFallSpeedMultiplier();
    String getDescription();
}