package com.devkasatkin.jackthegiant.helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Base64Coder;
import com.badlogic.gdx.utils.Json;

public class GameManager {
    private static GameManager ourInstance = new GameManager();

    public GameData gameData;
    private Json json = new Json();
    private FileHandle fileHandle = Gdx.files.local("bin/GameData.json");

    public boolean gameStartedFromMainMenu;
    public boolean isPaused = true;
    public int lifeScore;
    public int coinScore;
    public int score;
    private Music music;

    private GameManager() {}

    public void initializeGameData() {
        if (!fileHandle.exists()) {
            gameData = new GameData();

            gameData.setHighscore(0);
            gameData.setCoinHighscore(0);

            gameData.setEasyDifficilty(false);
            gameData.setMediumDifficilty(true);
            gameData.setHardDifficilty(false);

            gameData.setMusicOn(true);

            saveData();
        } else {
            loadData();
        }
    }

    public void saveData() {
        if (gameData != null) {
            fileHandle.writeString(Base64Coder.encodeString(json.prettyPrint(gameData)), false);
        }
    }

    public void loadData() {
        gameData = json.fromJson(GameData.class, Base64Coder.decodeString(fileHandle.readString()));
    }

    public void checkForNewHighscore() {
        int oldHighscore = gameData.getHighscore();
        int oldCoinHighscore = gameData.getCoinHighscore();

        if (oldHighscore < score) {
            gameData.setHighscore(score);
        }

        if (oldCoinHighscore < coinScore) {
            gameData.setCoinHighscore(coinScore);
        }

        saveData();
    }

    public void playMusic() {
        if (music == null) {
            music = Gdx.audio.newMusic(Gdx.files.internal("Sounds/Background.mp3"));
            music.setLooping(true);
        }
        if (!music.isPlaying()) {
            music.play();
        }
    }

    public void stopMusic() {
        if (music.isPlaying()) {
            music.stop();
            music.dispose();
        }
    }

    public static GameManager getInstance() {
        return ourInstance;
    }
} // game manager
