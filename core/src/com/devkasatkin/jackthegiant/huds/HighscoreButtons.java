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
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.SpriteDrawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.devkasatkin.jackthegiant.helpers.GameInfo;
import com.devkasatkin.jackthegiant.helpers.GameManager;
import com.devkasatkin.jackthegiant.main.GameMain;
import com.devkasatkin.jackthegiant.scenes.MainMenu;

public class HighscoreButtons {

    private GameMain game;
    private Stage stage;
    private Viewport viewport;

    private Label scoreLabel;
    private Label coinLabel;

    private ImageButton backBtn;

    public HighscoreButtons(GameMain game) {
        this.game = game;

        viewport = new FitViewport(GameInfo.WIDTH, GameInfo.HEIGHT, new OrthographicCamera());

        stage = new Stage(viewport, game.getBatch());

        Gdx.input.setInputProcessor(stage);

        createAndPositionUIElements();

        stage.addActor(backBtn);
        stage.addActor(scoreLabel);
        stage.addActor(coinLabel);
    }

    public Stage getStage() {
        return this.stage;
    }

    private void createAndPositionUIElements() {
        backBtn = new ImageButton(new SpriteDrawable(
                new Sprite(new Texture("Buttons/Options Menu/Back.png"))));

        //Add and tune customs fonts
        FreeTypeFontGenerator generator =
                new FreeTypeFontGenerator(Gdx.files.internal("Fonts/blow.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter
                = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 40;

        BitmapFont scoreFont = generator.generateFont(parameter);
        BitmapFont coinFont = generator.generateFont(parameter);

        scoreLabel = new Label(String.valueOf(GameManager.getInstance().gameData.getHighscore()),
                new Label.LabelStyle(scoreFont, Color.WHITE));
        coinLabel = new Label(String.valueOf(GameManager.getInstance().gameData.getCoinHighscore()),
                new Label.LabelStyle(coinFont, Color.WHITE));

        backBtn.setPosition(17,17, Align.bottomLeft);

        scoreLabel.setPosition(GameInfo.WIDTH / 2f - 40, GameInfo.HEIGHT / 2f - 120);
        coinLabel.setPosition(GameInfo.WIDTH / 2f - 20, GameInfo.HEIGHT / 2f - 215);

        backBtn.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                game.setScreen(new MainMenu(game));
            }
        });
    }
}// highscore buttons
