package com.puzzle.command_pattern;

import java.util.ArrayList;
import java.util.List;

import com.puzzle.BlockType;
import com.puzzle.Constants;
import com.puzzle.Position;
import com.puzzle.composite_pattern.Piece;
import com.puzzle.GameLogger;

public class RotateCommand implements Command {
    private Piece piece;
    private BlockType[][] grid;
    private MoveCommand.PieceUpdater updater;
    
    public RotateCommand(Piece piece, BlockType[][] grid, MoveCommand.PieceUpdater updater) {
        this.piece = piece;
        this.grid = grid;
        this.updater = updater;
    }
    
    @Override
    public boolean canExecute() {
        if (!piece.getState().canRotate() || piece.getType() == BlockType.O) {
            return false;
        }
        
        Piece rotated = getRotatedPiece();
        return !checkCollision(rotated);
    }
    
    @Override
    public void execute() {
        if (canExecute()) {
            updater.update(getRotatedPiece());
        }
    }
    
    private Piece getRotatedPiece() {
        Piece newPiece = (Piece) piece.clone();
        List<Position> rotatedBlocks = new ArrayList<>();
        
        for (Position block : newPiece.getBlocks()) {
            rotatedBlocks.add(new Position(-block.getY(), block.getX()));
        }
        
        newPiece.setBlocks(rotatedBlocks);
        return newPiece;
    }
    
    private boolean checkCollision(Piece piece) {
        for (Position block : piece.getAbsoluteBlocks()) {
            if (block.getX() < 0 || block.getX() >= Constants.GRID_WIDTH || 
                block.getY() >= Constants.GRID_HEIGHT) {
                return true;
            }
            if (block.getY() >= 0 && grid[block.getY()][block.getX()] != null) {
                return true;
            }
        }
        return false;
    }
}