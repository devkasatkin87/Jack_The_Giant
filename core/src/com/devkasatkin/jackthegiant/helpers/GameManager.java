package com.devkasatkin.jackthegiant.helpers;

public class GameManager {
    private static GameManager ourInstance = new GameManager();

    public boolean gameStartedFromMainMenu;
    public boolean isPaused = true;
    public int lifeScore;
    public int coinScore;
    public int score;

    private GameManager() {}

    public static GameManager getInstance() {
        return ourInstance;
    }
} // game manager
