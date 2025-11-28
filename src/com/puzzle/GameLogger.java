package com.puzzle;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class GameLogger {
    private static GameLogger instance;
    private PrintWriter writer;
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    
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
    
    public void log(String message) {
        String timestamp = LocalDateTime.now().format(formatter);
        String logMessage = String.format("[%s] %s", timestamp, message);
        System.out.println(logMessage);
        if (writer != null) {
            writer.println(logMessage);
        }
    }
    
    public void close() {
        if (writer != null) {
            writer.close();
        }
    }
}