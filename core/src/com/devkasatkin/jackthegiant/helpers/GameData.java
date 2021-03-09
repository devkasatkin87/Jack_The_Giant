package com.devkasatkin.jackthegiant.helpers;

public class GameData {
    private int highscore;
    private int coinHighscore;

    private boolean easyDifficilty;
    private boolean mediumDifficilty;
    private boolean hardDifficilty;

    private boolean musicOn;

    public int getHighscore() {
        return highscore;
    }

    public void setHighscore(int highscore) {
        this.highscore = highscore;
    }

    public int getCoinHighscore() {
        return coinHighscore;
    }

    public void setCoinHighscore(int coinHighscore) {
        this.coinHighscore = coinHighscore;
    }

    public boolean isEasyDifficilty() {
        return easyDifficilty;
    }

    public void setEasyDifficilty(boolean easyDifficilty) {
        this.easyDifficilty = easyDifficilty;
    }

    public boolean isMediumDifficilty() {
        return mediumDifficilty;
    }

    public void setMediumDifficilty(boolean mediumDifficilty) {
        this.mediumDifficilty = mediumDifficilty;
    }

    public boolean isHardDifficilty() {
        return hardDifficilty;
    }

    public void setHardDifficilty(boolean hardDifficilty) {
        this.hardDifficilty = hardDifficilty;
    }

    public boolean isMusicOn() {
        return musicOn;
    }

    public void setMusicOn(boolean musicOn) {
        this.musicOn = musicOn;
    }



}// game data
