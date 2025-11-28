package com.puzzle;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

public class Piece implements PieceComponent {
    private BlockType type;
    private List<Position> blocks;
    private Color color;
    private Position position;
    private PieceState state;
    
    public Piece(BlockType type, List<Position> blocks, Color color) {
        this(type, blocks, color, new Position(3, 0), new FallingState());
    }
    
    public Piece(BlockType type, List<Position> blocks, Color color, Position position, PieceState state) {
        this.type = type;
        this.blocks = blocks;
        this.color = color;
        this.position = position;
        this.state = state;
    }
    
    @Override
    public void render(GraphicsContext gc, int cellSize) {
        gc.setFill(color);
        for (Position block : getAbsoluteBlocks()) {
            if (block.getY() >= 0 && block.getY() < Constants.GRID_HEIGHT &&
                block.getX() >= 0 && block.getX() < Constants.GRID_WIDTH) {
                gc.fillRect(block.getX() * cellSize, block.getY() * cellSize, 
                           cellSize, cellSize);
            }
        }
    }
    
    @Override
    public List<Position> getAbsoluteBlocks() {
        List<Position> absolute = new ArrayList<>();
        for (Position block : blocks) {
            absolute.add(new Position(
                block.getX() + position.getX(),
                block.getY() + position.getY()
            ));
        }
        return absolute;
    }
    
    @Override
    public void setState(PieceState state) {
        this.state = state;
    }
    
    @Override
    public Piece clone() {
        List<Position> clonedBlocks = new ArrayList<>();
        for (Position block : blocks) {
            clonedBlocks.add(new Position(block));
        }
        return new Piece(type, clonedBlocks, color, new Position(position), state);
    }

    
    @Override
    public int getScoreMultiplier() {
        return 1;
    }
    
    @Override
    public double getFallSpeedMultiplier() {
        return 1.0;
    }
    
    @Override
    public String getDescription() {
        return "Standard " + type.name() + " piece";
    }
    

    @Override
    public BlockType getType() { return type; }
    
    @Override
    public List<Position> getBlocks() { return blocks; }
    
    @Override
    public String getColor() { 
        return String.format("#%02x%02x%02x",
            (int)(color.getRed() * 255),
            (int)(color.getGreen() * 255),
            (int)(color.getBlue() * 255));
    }
    
    public Color getColorObject() { return color; }
    
    @Override
    public Position getPosition() { return position; }
    
    @Override
    public PieceState getState() { return state; }
    

    public void setBlocks(List<Position> blocks) { this.blocks = blocks; }
}