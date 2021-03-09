package com.devkasatkin.jackthegiant.huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.devkasatkin.jackthegiant.helpers.GameInfo;
import com.devkasatkin.jackthegiant.helpers.GameManager;
import com.devkasatkin.jackthegiant.main.GameMain;
import com.devkasatkin.jackthegiant.scenes.MainMenu;

public class OptionsButtons {

    private GameMain game;
    private Stage stage;
    private Viewport viewport;

    private ImageButton backBtn;
    private ImageButton easyBtn;
    private ImageButton mediumBtn;
    private ImageButton hardBtn;

    private Image checkSignBtn;

    public OptionsButtons(GameMain game) {
        this.game = game;

        viewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);

        createAndPositionButtons();
        addAllListeners();

        stage.addActor(backBtn);
        stage.addActor(easyBtn);
        stage.addActor(mediumBtn);
        stage.addActor(hardBtn);
        stage.addActor(checkSignBtn);

    }

    public Stage getStage() {
        return this.stage;
    }

    private void createAndPositionButtons() {
        backBtn = new ImageButton(new SpriteDrawable(
                new Sprite(new Texture("Buttons/Options Menu/Back.png"))));
        easyBtn = new ImageButton(new SpriteDrawable(
                new Sprite(new Texture("Buttons/Options Menu/Easy.png"))));
        mediumBtn = new ImageButton(new SpriteDrawable(
                new Sprite(new Texture("Buttons/Options Menu/Medium.png"))));
        hardBtn = new ImageButton(new SpriteDrawable(
                new Sprite(new Texture("Buttons/Options Menu/Hard.png"))));

        checkSignBtn = new Image(new SpriteDrawable(
                new Sprite(new Texture("Buttons/Options Menu/Check Sign.png"))));

        backBtn.setPosition(17,17, Align.bottomLeft);
        easyBtn.setPosition(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f + 40, Align.center);
        mediumBtn.setPosition(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f - 40, Align.center);
        hardBtn.setPosition(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f - 120, Align.center);

        positionSign();
    }

    private void positionSign() {
        if (GameManager.getInstance().gameData.isEasyDifficilty()) {
            checkSignBtn.setPosition(GameInfo.WIDTH / 2f + 76, easyBtn.getY() + 13, Align.bottomLeft);
        }
        if (GameManager.getInstance().gameData.isMediumDifficilty()) {
            checkSignBtn.setPosition(GameInfo.WIDTH / 2f + 76, mediumBtn.getY() + 13, Align.bottomLeft);
        }
        if (GameManager.getInstance().gameData.isHardDifficilty()) {
            checkSignBtn.setPosition(GameInfo.WIDTH / 2f + 76, hardBtn.getY() + 13, Align.bottomLeft);
        }
    }

    private void changeDifficulty(int difficulty) {
        switch (difficulty) {
            case 0 :
                GameManager.getInstance().gameData.setEasyDifficilty(true);
                GameManager.getInstance().gameData.setMediumDifficilty(false);
                GameManager.getInstance().gameData.setHardDifficilty(false);
                break;
            case 1 :
                GameManager.getInstance().gameData.setEasyDifficilty(false);
                GameManager.getInstance().gameData.setMediumDifficilty(true);
                GameManager.getInstance().gameData.setHardDifficilty(false);
                break;
            case 2 :
                GameManager.getInstance().gameData.setEasyDifficilty(false);
                GameManager.getInstance().gameData.setMediumDifficilty(false);
                GameManager.getInstance().gameData.setHardDifficilty(true);
                break;
        }
        GameManager.getInstance().saveData();
    }

    private void addAllListeners() {
        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
            }
        });

        easyBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeDifficulty(0);
                checkSignBtn.setY(easyBtn.getY() + 13);
            }
        });

        mediumBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeDifficulty(1);
                checkSignBtn.setY(mediumBtn.getY() + 13);
            }
        });

        hardBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                changeDifficulty(2);
                checkSignBtn.setY(hardBtn.getY() + 13);
            }
        });
    }


}// options buttons
