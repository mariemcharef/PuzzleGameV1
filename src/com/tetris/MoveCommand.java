package com.tetris;
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
    }
    
    @Override
    public boolean canExecute() {
        if (!piece.getState().canMove()) return false;
        
        Piece newPiece = piece.clone();
        newPiece.getPosition().setX(newPiece.getPosition().getX() + dx);
        newPiece.getPosition().setY(newPiece.getPosition().getY() + dy);
        
        return !checkCollision(newPiece);
    }
    
    @Override
    public void execute() {
        if (canExecute()) {
            Piece newPiece = piece.clone();
            newPiece.getPosition().setX(newPiece.getPosition().getX() + dx);
            newPiece.getPosition().setY(newPiece.getPosition().getY() + dy);
            updater.update(newPiece);
        }
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