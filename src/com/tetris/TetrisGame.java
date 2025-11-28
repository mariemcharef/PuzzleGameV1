package com.tetris;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Timer;
import java.util.TimerTask;

public class TetrisGame extends Application {

    private BlockType[][] grid;
    private Piece currentPiece;
    private int score;
    private boolean gameOver;
    private boolean isPaused;

    private Canvas canvas;
    private Label scoreLabel;
    private Button pauseButton;
    private Timer timer;

    private StackPane rootPane;

    @Override
    public void start(Stage primaryStage) {
        initializeGame();

        rootPane = new StackPane();
        rootPane.setStyle("-fx-background-color: #1a1a1a;");

        VBox layout = new VBox(20);
        layout.setAlignment(Pos.CENTER);
        layout.setPadding(new Insets(20));

        Label title = new Label("TETRIS");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 48));
        title.setTextFill(Color.WHITE);

        HBox gameArea = new HBox(30);
        gameArea.setAlignment(Pos.CENTER);

        canvas = new Canvas(Constants.GRID_WIDTH * Constants.CELL_SIZE,
                Constants.GRID_HEIGHT * Constants.CELL_SIZE);
        StackPane canvasPane = new StackPane(canvas);
        canvasPane.setStyle("-fx-background-color: black; -fx-padding: 10;");

        VBox sidePanel = new VBox(15);
        sidePanel.setAlignment(Pos.TOP_CENTER);
        sidePanel.setPrefWidth(200);

        VBox scorePanel = new VBox(10);
        scorePanel.setAlignment(Pos.CENTER);
        scorePanel.setStyle("-fx-background-color: #2a2a2a; -fx-padding: 15; -fx-background-radius: 5;");

        Label scoreTitle = new Label("SCORE");
        scoreTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        scoreTitle.setTextFill(Color.WHITE);

        scoreLabel = new Label("0");
        scoreLabel.setFont(Font.font("Arial", FontWeight.BOLD, 36));
        scoreLabel.setTextFill(Color.WHITE);

        scorePanel.getChildren().addAll(scoreTitle, scoreLabel);

        VBox controlsPanel = new VBox(8);
        controlsPanel.setAlignment(Pos.CENTER_LEFT);
        controlsPanel.setStyle("-fx-background-color: #2a2a2a; -fx-padding: 15; -fx-background-radius: 5;");

        Label controlsTitle = new Label("CONTROLS");
        controlsTitle.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        controlsTitle.setTextFill(Color.WHITE);

        controlsPanel.getChildren().add(controlsTitle);
        controlsPanel.getChildren().addAll(
                createControlLabel("← → Move"),
                createControlLabel("↑ Rotate"),
                createControlLabel("↓ Soft Drop"),
                createControlLabel("Space: Hard Drop")
        );

        pauseButton = new Button("Pause");
        pauseButton.setPrefWidth(150);
        pauseButton.setStyle("-fx-background-color: #2563eb; -fx-text-fill: white; " +
                "-fx-font-size: 16; -fx-padding: 10; -fx-background-radius: 5;");
        pauseButton.setOnAction(e -> togglePause());

        sidePanel.getChildren().addAll(scorePanel, controlsPanel, pauseButton);

        gameArea.getChildren().addAll(canvasPane, sidePanel);
        layout.getChildren().addAll(title, gameArea);

        rootPane.getChildren().add(layout);

        Scene scene = new Scene(rootPane, 800, 720);

        scene.setOnKeyPressed(e -> handleKeyPress(e.getCode()));
        rootPane.setFocusTraversable(true);
        rootPane.requestFocus();

        primaryStage.setScene(scene);
        primaryStage.setTitle("Tetris");
        primaryStage.setOnCloseRequest(e -> {
            if (timer != null) timer.cancel();
        });
        primaryStage.show();

        startGameLoop();
        render();
    }

    private Label createControlLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Arial", 14));
        label.setTextFill(Color.WHITE);
        return label;
    }

    private void initializeGame() {
        grid = new BlockType[Constants.GRID_HEIGHT][Constants.GRID_WIDTH];
        currentPiece = TetrominoFactory.createPiece();
        score = 0;
        gameOver = false;
        isPaused = false;
    }

    private void startGameLoop() {
        timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                if (!gameOver && !isPaused) {
                    Platform.runLater(() -> {
                        moveDown();
                        render();
                    });
                }
            }
        }, Constants.FALL_SPEED, Constants.FALL_SPEED);
    }

    private void moveDown() {
        MoveCommand cmd = new MoveCommand(currentPiece, 0, 1, grid, this::updatePiece);

        if (cmd.canExecute()) {
            cmd.execute();
        } else {
            currentPiece.setState(new LandedState());
            lockPiece();
        }
    }

    private void updatePiece(Piece piece) {
        currentPiece = piece;
    }

    private void lockPiece() {
        for (Position block : currentPiece.getAbsoluteBlocks()) {
            if (block.getY() >= 0 && block.getY() < Constants.GRID_HEIGHT) {
                grid[block.getY()][block.getX()] = currentPiece.getType();
            }
        }

        int linesCleared = clearLines();
        score += linesCleared * 100;
        scoreLabel.setText(String.valueOf(score));

        currentPiece = TetrominoFactory.createPiece();

        if (checkCollision(currentPiece)) {
            gameOver = true;
            showGameOver();
        }
    }

    private int clearLines() {
        int cleared = 0;

        for (int y = Constants.GRID_HEIGHT - 1; y >= 0; y--) {
            boolean full = true;

            for (int x = 0; x < Constants.GRID_WIDTH; x++) {
                if (grid[y][x] == null) {
                    full = false;
                    break;
                }
            }

            if (full) {
                for (int yy = y; yy > 0; yy--) {
                    grid[yy] = grid[yy - 1].clone();
                }
                grid[0] = new BlockType[Constants.GRID_WIDTH];
                cleared++;
                y++;
            }
        }

        return cleared;
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

    private void handleKeyPress(KeyCode code) {
        if (gameOver || isPaused) return;

        switch (code) {
            case LEFT:
                new MoveCommand(currentPiece, -1, 0, grid, this::updatePiece).execute();
                break;
            case RIGHT:
                new MoveCommand(currentPiece, 1, 0, grid, this::updatePiece).execute();
                break;
            case DOWN:
                moveDown();
                break;
            case UP:
                new RotateCommand(currentPiece, grid, this::updatePiece).execute();
                break;
            case SPACE:
                MoveCommand drop = new MoveCommand(currentPiece, 0, 1, grid, this::updatePiece);
                while (drop.canExecute()) drop.execute();
                lockPiece();
                break;
        }

        render();
    }

    private void togglePause() {
        isPaused = !isPaused;
        pauseButton.setText(isPaused ? "Resume" : "Pause");
    }

    private void render() {
        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.setFill(Color.web("#1a1a1a"));
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        for (int y = 0; y < Constants.GRID_HEIGHT; y++) {
            for (int x = 0; x < Constants.GRID_WIDTH; x++) {
                if (grid[y][x] != null) {
                    gc.setFill(Constants.TETROMINO_COLORS.get(grid[y][x]));
                    gc.fillRect(x * Constants.CELL_SIZE, y * Constants.CELL_SIZE,
                            Constants.CELL_SIZE, Constants.CELL_SIZE);
                }

                gc.setStroke(Color.web("#2a2a2a"));
                gc.strokeRect(x * Constants.CELL_SIZE, y * Constants.CELL_SIZE,
                        Constants.CELL_SIZE, Constants.CELL_SIZE);
            }
        }


        gc.setFill(currentPiece.getColorObject());
        for (Position block : currentPiece.getAbsoluteBlocks()) {
            if (block.getY() >= 0 && block.getY() < Constants.GRID_HEIGHT &&
                    block.getX() >= 0 && block.getX() < Constants.GRID_WIDTH) {
                gc.fillRect(block.getX() * Constants.CELL_SIZE,
                        block.getY() * Constants.CELL_SIZE,
                        Constants.CELL_SIZE, Constants.CELL_SIZE);
            }
        }
    }

    private void showGameOver() {
        VBox overlay = new VBox(15);
        overlay.setAlignment(Pos.CENTER);
        overlay.setStyle("-fx-background-color: rgba(220,38,38,0.9); -fx-padding: 20; -fx-background-radius: 5;");

        Label over = new Label("GAME OVER!");
        over.setFont(Font.font("Arial", FontWeight.BOLD, 24));
        over.setTextFill(Color.WHITE);

        Button restart = new Button("Restart");
        restart.setStyle("-fx-background-color: white; -fx-text-fill: black; " +
                "-fx-font-size: 16; -fx-padding: 10; -fx-background-radius: 5;");
        restart.setOnAction(e -> restart());

        overlay.getChildren().addAll(over, restart);

        rootPane.getChildren().add(overlay);
    }

    private void restart() {
        if (timer != null) timer.cancel();

        initializeGame();
        scoreLabel.setText("0");
        pauseButton.setText("Pause");

        rootPane.getChildren().removeIf(node ->
                node instanceof VBox && node.getStyle().contains("rgba(220,38,38"));

        startGameLoop();
        render();

        rootPane.requestFocus();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
