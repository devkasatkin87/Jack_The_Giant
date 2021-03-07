package com.devkasatkin.jackthegiant.huds;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.devkasatkin.jackthegiant.helpers.GameInfo;
import com.devkasatkin.jackthegiant.helpers.GameManager;
import com.devkasatkin.jackthegiant.main.GameMain;
import com.devkasatkin.jackthegiant.scenes.MainMenu;

public class UIHud {
    private GameMain game;
    private Stage stage;
    private Viewport viewport;

    private Image coinImage;
    private Image scoreImage;
    private Image lifeImage;
    private Image pausePanel;

    private Label coinLabel;
    private Label lifeLabel;
    private Label scoreLabel;

    private ImageButton pauseBtn;
    private ImageButton resumeBtn;
    private ImageButton quitBtn;

    public UIHud(GameMain game) {
        this.game = game;
        viewport = new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, game.getBatch());
        Gdx.input.setInputProcessor(stage);

        if (GameManager.getInstance().gameStartedFromMainMenu) {
            // this is the first time starting the game, set initial values
            GameManager.getInstance().gameStartedFromMainMenu = false;

            GameManager.getInstance().lifeScore = 2;
            GameManager.getInstance().coinScore = 0;
            GameManager.getInstance().score = 0;
        }

        createLabels();
        createImages();
        createButtonsAndAddListeners();

        // create and position table with game information
        Table lifeAndCoinTable = new Table();
        lifeAndCoinTable.top().left();
        lifeAndCoinTable.setFillParent(true);

        lifeAndCoinTable.add(lifeImage).padLeft(10f).padTop(10f);
        lifeAndCoinTable.add(lifeLabel).padLeft(5f);
        lifeAndCoinTable.row();

        lifeAndCoinTable.add(coinImage).padLeft(10f).padTop(10f);
        lifeAndCoinTable.add(coinLabel).padLeft(5f);
        lifeAndCoinTable.row();

        Table scoreTable = new Table();
        scoreTable.top().right();
        scoreTable.setFillParent(true);

        scoreTable.add(scoreImage).padLeft(10f).padTop(10f).padRight(10f);
        scoreTable.row();
        scoreTable.add(scoreLabel).padLeft(20f).padTop(15f).padRight(10f);

        stage.addActor(lifeAndCoinTable);
        stage.addActor(scoreTable);
        stage.addActor(pauseBtn);
    }

    public Stage getStage() {
        return stage;
    }

    private void createLabels() {
        FreeTypeFontGenerator generator =
                new FreeTypeFontGenerator(Gdx.files.internal("Fonts/blow.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter =
                new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;

        BitmapFont font = generator.generateFont(parameter);

        coinLabel = new Label("x " + GameManager.getInstance().coinScore,
                new Label.LabelStyle(font, Color.WHITE));
        lifeLabel = new Label("x " + GameManager.getInstance().lifeScore,
                new Label.LabelStyle(font, Color.WHITE));
        scoreLabel =  new Label("" + GameManager.getInstance().score,
                new Label.LabelStyle(font, Color.WHITE));
    }

    private void createImages() {
        coinImage = new Image(new Texture("Collectables/Coin.png"));
        lifeImage = new Image(new Texture("Collectables/Life.png"));
        scoreImage = new Image(new Texture("Buttons/Gameplay/Score.png"));
    }

    private void createButtonsAndAddListeners() {
        pauseBtn = new ImageButton(new SpriteDrawable(
                new Sprite(new Texture("Buttons/Gameplay/Pause.png"))));
        pauseBtn.setPosition(470, 17, Align.bottomRight);
        pauseBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // code for pausing the game
                if (!GameManager.getInstance().isPaused) {
                    GameManager.getInstance().isPaused = true;
                    createPausePanel();
                }
            }
        });


    }

    private void createPausePanel() {
        pausePanel = new Image(new Texture("Buttons/Pause/Pause Panel.png"));

        resumeBtn = new ImageButton(new SpriteDrawable(
                new Sprite(new Texture("Buttons/Pause/Resume.png"))));
        quitBtn = new ImageButton(new SpriteDrawable(
                new Sprite(new Texture("Buttons/Pause/Quit 2.png"))));

        pausePanel.setPosition(GameInfo.WIDTH / 2f - pausePanel.getWidth() / 2f,
                GameInfo.HEIGHT / 2f - pausePanel.getHeight() / 2f);

        resumeBtn.setPosition(GameInfo.WIDTH / 2f - resumeBtn.getWidth() / 2f, GameInfo.HEIGHT / 2f + 20);
        quitBtn.setPosition(GameInfo.WIDTH / 2f - quitBtn.getWidth() / 2f, GameInfo.HEIGHT / 2f - 80);

        resumeBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                removePausePanel();
                GameManager.getInstance().isPaused = false;
            }
        });

        quitBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
            }
        });

        stage.addActor(pausePanel);
        stage.addActor(pauseBtn);
        stage.addActor(resumeBtn);
        stage.addActor(quitBtn);
    }

    public void incrementScore(int score) {
        GameManager.getInstance().score += score;
        scoreLabel.setText(String.valueOf(GameManager.getInstance().score));
    }

    public void incrementCoins() {
        GameManager.getInstance().coinScore++;
       coinLabel.setText("x " + GameManager.getInstance().coinScore);
       incrementScore(200);
    }

    public void incrementLifes() {
        GameManager.getInstance().lifeScore++;
        lifeLabel.setText("x " + GameManager.getInstance().lifeScore);
        incrementScore(300);
    }

    public void decrenentLife() {
        GameManager.getInstance().lifeScore--;
        if (GameManager.getInstance().lifeScore >= 0) {
            lifeLabel.setText("x " + GameManager.getInstance().lifeScore);
        } else {
            // game over
        }
    }

    private void removePausePanel() {
        pausePanel.remove();
        resumeBtn.remove();
        quitBtn.remove();
    }
} // ui hud
