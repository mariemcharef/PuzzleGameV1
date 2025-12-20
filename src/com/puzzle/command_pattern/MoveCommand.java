package com.puzzle.command_pattern;

import com.puzzle.BlockType;
import com.puzzle.Constants;
import com.puzzle.Position;
import com.puzzle.composite_pattern.Piece;
import com.puzzle.GameLogger;

public class MoveCommand implements Command {
    private Piece piece;
    private int dx;
    private int dy;
    private BlockType[][] grid;
    private PieceUpdater updater;
    
    public interface PieceUpdater {
        void update(Piece piece);
    }
    
    public MoveCommand(Piece piece, int dx, int dy, BlockType[][] grid, PieceUpdater updater) {
        this.piece = piece;
        this.dx = dx;
        this.dy = dy;
        this.grid = grid;
        this.updater = updater;
        GameLogger.getInstance().log("Command pattern used: MoveCommand created");
    }
    

        @Override
        public boolean canExecute() {
            if (!piece.getState().canMove()) return false;

            for (Position block : piece.getAbsoluteBlocks()) {
                int newX = block.getX() + dx;
                int newY = block.getY() + dy;

                if (newX < 0 || newX >= Constants.GRID_WIDTH || newY >= Constants.GRID_HEIGHT)
                    return false;
                if (newY >= 0 && grid[newY][newX] != null)
                    return false;
            }
            return true;
        }

        @Override
        public void execute() {
            if (!canExecute()) return;
            piece.getPosition().setX(piece.getPosition().getX() + dx);
            piece.getPosition().setY(piece.getPosition().getY() + dy);
            updater.update(piece);
        }


}