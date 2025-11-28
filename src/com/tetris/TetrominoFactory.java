package com.tetris;
import javafx.scene.paint.Color;
import java.util.*;

public class TetrominoFactory {
    private static final Map<BlockType, Shape> shapes = new HashMap<>();
    
    static class Shape {
        List<Position> blocks;
        Color color;
        
        Shape(List<Position> blocks, Color color) {
            this.blocks = blocks;
            this.color = color;
        }
    }
    
    static {
        shapes.put(BlockType.I, new Shape(Arrays.asList(
            new Position(0, 0), new Position(1, 0), new Position(2, 0), new Position(3, 0)
        ), Color.CYAN));
        
        shapes.put(BlockType.O, new Shape(Arrays.asList(
            new Position(0, 0), new Position(1, 0), new Position(0, 1), new Position(1, 1)
        ), Color.YELLOW));
        
        shapes.put(BlockType.T, new Shape(Arrays.asList(
            new Position(1, 0), new Position(0, 1), new Position(1, 1), new Position(2, 1)
        ), Color.PURPLE));
        
        shapes.put(BlockType.S, new Shape(Arrays.asList(
            new Position(1, 0), new Position(2, 0), new Position(0, 1), new Position(1, 1)
        ), Color.LIME));
        
        shapes.put(BlockType.Z, new Shape(Arrays.asList(
            new Position(0, 0), new Position(1, 0), new Position(1, 1), new Position(2, 1)
        ), Color.RED));
        
        shapes.put(BlockType.L, new Shape(Arrays.asList(
            new Position(0, 0), new Position(0, 1), new Position(0, 2), new Position(1, 2)
        ), Color.ORANGE));
        
        shapes.put(BlockType.J, new Shape(Arrays.asList(
            new Position(1, 0), new Position(1, 1), new Position(1, 2), new Position(0, 2)
        ), Color.BLUE));
    }
    
    public static Piece createPiece() {
        BlockType[] types = BlockType.values();
        BlockType type = types[new Random().nextInt(types.length)];
        Shape shape = shapes.get(type);
        
        List<Position> blocks = new ArrayList<>();
        for (Position pos : shape.blocks) {
            blocks.add(new Position(pos));
        }
        
        return new Piece(type, blocks, shape.color);
    }
}