package com.puzzle;

import javafx.scene.paint.Color;
import java.util.HashMap;
import java.util.Map;

public class Constants {
    public static final int GRID_WIDTH = 10;
    public static final int GRID_HEIGHT = 20;
    public static final int CELL_SIZE = 30;
    public static final int FALL_SPEED = 500;
    
    public static final Map<BlockType, Color> TETROMINO_COLORS = new HashMap<>();
    
    static {
        TETROMINO_COLORS.put(BlockType.I, Color.CYAN);
        TETROMINO_COLORS.put(BlockType.O, Color.YELLOW);
        TETROMINO_COLORS.put(BlockType.T, Color.PURPLE);
        TETROMINO_COLORS.put(BlockType.S, Color.LIME);
        TETROMINO_COLORS.put(BlockType.Z, Color.RED);
        TETROMINO_COLORS.put(BlockType.L, Color.ORANGE);
        TETROMINO_COLORS.put(BlockType.J, Color.BLUE);
    }
}