package com.devkasatkin.jackthegiant.huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.devkasatkin.jackthegiant.helpers.GameInfo;
import com.devkasatkin.jackthegiant.main.GameMain;
import com.devkasatkin.jackthegiant.scenes.Gameplay;
import com.devkasatkin.jackthegiant.scenes.Highscore;
import com.devkasatkin.jackthegiant.scenes.Options;

public class MainMenuButtons {
    private GameMain game;
    private Stage stage;
    private Viewport viewport;

    private ImageButton playBtn;
    private ImageButton highScoreBtn;
    private ImageButton optionsBtn;
    private ImageButton quitBtn;
    private ImageButton musicBtn;


    public MainMenuButtons(GameMain game) {
        this.game = game;

        viewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.getBatch());

        //обязательно назначить кто будет получать действия в случае нажатия
        Gdx.input.setInputProcessor(stage);

        createPositionButtons();
        addAllListeners();

        stage.addActor(playBtn);
        stage.addActor(highScoreBtn);
        stage.addActor(optionsBtn);
        stage.addActor(quitBtn);
        stage.addActor(musicBtn);
    }

    public Stage getStage() {
        return this.stage;
    }

    private void createPositionButtons() {
        playBtn = new ImageButton(new SpriteDrawable(new Sprite(
                new Texture("Buttons/Main Menu/Start Game.png"))));
        highScoreBtn = new ImageButton(new SpriteDrawable(new Sprite(
                new Texture("Buttons/Main Menu/Highscore.png"))));
        optionsBtn = new ImageButton(new SpriteDrawable(new Sprite(
                new Texture("Buttons/Main Menu/Options.png"))));
        quitBtn = new ImageButton(new SpriteDrawable(new Sprite(
                new Texture("Buttons/Main Menu/Quit.png"))));
        musicBtn = new ImageButton(new SpriteDrawable(new Sprite(
                new Texture("Buttons/Main Menu/Music on.png"))));

        playBtn.setPosition(GameInfo.WIDTH / 2f - 80, GameInfo.HEIGHT / 2f + 50, Align.center);
        highScoreBtn.setPosition(GameInfo.WIDTH / 2f - 60, GameInfo.HEIGHT / 2f - 20, Align.center);
        optionsBtn.setPosition(GameInfo.WIDTH / 2f - 40, GameInfo.HEIGHT / 2f - 90,  Align.center);
        quitBtn.setPosition(GameInfo.WIDTH / 2f - 20, GameInfo.HEIGHT / 2f - 160, Align.center);
        musicBtn.setPosition(GameInfo.WIDTH / 2f - 13, 30, Align.center);

    }

    private void addAllListeners() {
        playBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new Gameplay(game));
            }
        });

        highScoreBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new Highscore(game));
            }
        });

        optionsBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new Options(game));
            }
        });

        quitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            }
        });

        musicBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
            }
        });


    }
}// main menu bnts
