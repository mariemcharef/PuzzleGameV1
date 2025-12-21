package com.puzzle;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameLogger {
    private static GameLogger instance;// Singleton instance only one exists
    private PrintWriter writer;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
    public enum LogLevel {
        INFO, STATE, DECORATOR, EVENT, ERROR
    }
    
    private GameLogger() {
        try {
            writer = new PrintWriter(new FileWriter("tetris_game.log", true), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static GameLogger getInstance() {
        if (instance == null) {
            instance = new GameLogger();
        }
        return instance;
    }
    
    public void log(LogLevel level, String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logMessage = String.format("[%s] [%s] %s", timestamp, level, message);
        System.out.println(logMessage);
        if (writer != null) {
            writer.println(logMessage);
            writer.flush();
        }
    }
    
    public void logStateChange(String entity, String fromState, String toState) {
        String message = String.format("%s: %s -> %s", entity, fromState, toState);
        log(LogLevel.STATE, message);
    }
    
    public void logDecoratorApplied(String decoratorType) {
        String message = String.format("%s applied", decoratorType);
        log(LogLevel.DECORATOR, message);
    }
    
    public void logDecoratorRemoved(String decoratorType) {
        String message = String.format("%s removed ", 
            decoratorType);
        log(LogLevel.DECORATOR, message);
    }
    
    public void logGameEvent(String event) {
        log(LogLevel.EVENT, event);
    }
    
    public void logGameStart() {
        log(LogLevel.INFO, "Game started");
    }
    
    public void logGameOver(int finalScore) {
        log(LogLevel.INFO, "Game Over - Final score: " + finalScore);
    }

    public void logEntityCreated(String entityType, String entityId) {
        String message = String.format("Entity created: %s [Type: %s]", entityType, entityId);
        log(LogLevel.EVENT, message);
    }
    
    public void logEntityDestroyed(String entityType, String entityId) {
        String message = String.format("Entity destroyed: %s [ID: %s]", entityType, entityId);
        log(LogLevel.EVENT, message);
    }

    public void logCollision(String entity1, String entity2) {
        String message = String.format("Collision detected: %s <-> %s", entity1, entity2);
        log(LogLevel.EVENT, message);
    }
    
    public void logLevelChange(int newLevel) {
        String message = String.format("Level changed to: %d", newLevel);
        log(LogLevel.EVENT, message);
    }

    public void logGamePaused() {
        log(LogLevel.INFO, "Game paused");
    }
    
    public void logGameResumed() {
        log(LogLevel.INFO, "Game resumed");
    }
    
    public void close() {
        if (writer != null) {
            writer.close();
        }
    }
}