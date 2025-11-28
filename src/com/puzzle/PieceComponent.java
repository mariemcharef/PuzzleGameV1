package com.puzzle;

import javafx.scene.canvas.GraphicsContext;
import java.util.List;

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