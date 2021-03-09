package com.devkasatkin.jackthegiant.scenes;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.actions.RunnableAction;
import com.badlogic.gdx.scenes.scene2d.actions.SequenceAction;
import com.badlogic.gdx.utils.viewport.StretchViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.devkasatkin.jackthegiant.clouds.CloudsController;
import com.devkasatkin.jackthegiant.helpers.GameInfo;
import com.devkasatkin.jackthegiant.helpers.GameManager;
import com.devkasatkin.jackthegiant.huds.UIHud;
import com.devkasatkin.jackthegiant.main.GameMain;
import com.devkasatkin.jackthegiant.player.Player;

public class Gameplay implements Screen, ContactListener {
    private final GameMain game;
    private Sprite[] bgs;
    private OrthographicCamera mainCamera;
    private OrthographicCamera box2DCamera;
    private Box2DDebugRenderer debugRenderer;
    private World world;
    private Viewport viewport;
    private float lastYposition;
    private boolean touchedForTheFirstTime;
    private CloudsController cloudsController;
    private Player player;
    private float lastPlayerY;
    private UIHud hud;

    public Gameplay(GameMain game) {
        this.game = game;

        mainCamera = new OrthographicCamera(GameInfo.WIDTH, GameInfo.HEIGHT);
        mainCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);

        viewport = new StretchViewport(GameInfo.WIDTH, GameInfo.HEIGHT, mainCamera);

        box2DCamera = new OrthographicCamera();
        box2DCamera.setToOrtho(false, GameInfo.WIDTH / GameInfo.PPM,
                GameInfo.HEIGHT / GameInfo.PPM);
        box2DCamera.position.set(GameInfo.WIDTH / 2f, GameInfo.HEIGHT / 2f, 0);

        debugRenderer = new Box2DDebugRenderer();

        world = new World(new Vector2(0, -9.8f), true);
        //inform the world that the contact listener is the gameplay class
        world.setContactListener(this);

        cloudsController = new CloudsController(world);

        player = cloudsController.positionThePlayer(player);

        hud = new UIHud(game);

        createBackgrounds();
    }

    public void handleInput(float dt) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            player.movePlayer(-2);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            player.movePlayer(2);
        } else {
            player.setWalking(false);
        }
    }

    private void checkForFirstTouch() {
        if (!touchedForTheFirstTime) {
            if (Gdx.input.justTouched()) {
                touchedForTheFirstTime = true;
                GameManager.getInstance().isPaused = false;
                lastPlayerY = player.getY();
            }
        }
    }

    public void update(float dt) {
        checkForFirstTouch();
        if (!GameManager.getInstance().isPaused) {
            handleInput(dt);
            moveCamera();
            checkBackgroundOutOfBounds();
            cloudsController.setCameraY(mainCamera.position.y);
            cloudsController.createAndArrangeNewClouds();
            cloudsController.removeOffScreenCollectables();
            checkPlayerBounds();
            countScore();
        }
    }

    private void moveCamera() {
        mainCamera.position.y -= 1.5f;
    }


    private void createBackgrounds() {
        bgs = new Sprite[3];
        for (int i = 0; i < bgs.length; i++) {
            bgs[i] = new Sprite(new Texture("Backgrounds/Game BG.png"));
            bgs[i].setPosition(0, -(i * bgs[i].getHeight()));
            lastYposition = Math.abs(bgs[i].getY());
        }
    }

    private void drawBackgrounds() {
         for (Sprite bg : bgs) {
            game.getBatch().draw(bg, bg.getX(), bg.getY());
        }
    }

    private void checkBackgroundOutOfBounds() {
        for (int i = 0; i < bgs.length; i++) {
            if ((bgs[i].getY() - bgs[i].getHeight() / 2f - 5) > mainCamera.position.y) {
                float newPosition = bgs[i].getHeight() + lastYposition;
                bgs[i].setPosition(0, -newPosition);
                lastYposition = Math.abs(newPosition);
            }
        }
    }

    private void checkPlayerBounds() {
        if ((player.getY() - GameInfo.HEIGHT / 2f - player.getHeight() / 2) > mainCamera.position.y) {
            if (!player.isDied()) {
                playerDied();
            }
        }

        if ((player.getY() + GameInfo.HEIGHT / 2f + player.getHeight() / 2) < mainCamera.position.y) {
            if (!player.isDied()) {
                playerDied();
            }
        }

        if (player.getX() - 25 > GameInfo.WIDTH || player.getX() + 25 < 0) {
            if (!player.isDied()) {
                playerDied();
            }
        }
    }

    private void countScore() {
        if (lastPlayerY > player.getY()) {
            hud.incrementScore(1);
            lastPlayerY = player.getY();
        }
    }

    private void playerDied() {
        GameManager.getInstance().isPaused = true;
        // decrenent life count
        hud.decrenentLife();
        player.setDied(true);
        player.setPosition(1000,1000);

        if (GameManager.getInstance().lifeScore < 0) {
            //game over
            //check new highscore
            GameManager.getInstance().checkForNewHighscore();

            //show the end score to user

            hud.createGameOverPanel();

            //load main menu
            //Determines the sequence of actions for displaying the main menu
            RunnableAction run = new RunnableAction();
            run.setRunnable(new Runnable() {
                @Override
                public void run() {
                    // add custom code for action
                    game.setScreen(new MainMenu(game));
                }
            });

            SequenceAction sa = new SequenceAction();
            sa.addAction(Actions.delay(3f));
            sa.addAction(Actions.fadeOut(1f));
            sa.addAction(run);

            hud.getStage().addAction(sa);

        } else {
            //reload the game with continue

            RunnableAction run = new RunnableAction();
            run.setRunnable(new Runnable() {
                @Override
                public void run() {
                    // add custom code for action
                    game.setScreen(new Gameplay(game));
                }
            });

            SequenceAction sa = new SequenceAction();
            sa.addAction(Actions.delay(3f));
            sa.addAction(Actions.fadeOut(1f));
            sa.addAction(run);

            hud.getStage().addAction(sa);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        update(delta);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.getBatch().begin();

        drawBackgrounds();
        cloudsController.drawClouds(game.getBatch());
        cloudsController.drawCollectables(game.getBatch());

        player.drawIdle(game.getBatch());
        player.drawPlayerAnimation(game.getBatch());

        game.getBatch().end();

        //debugRenderer.render(world, box2DCamera.combined);

        game.getBatch().setProjectionMatrix(hud.getStage().getCamera().combined);
        hud.getStage().draw();
        hud.getStage().act();// necessarily for sequenceActions

        game.getBatch().setProjectionMatrix(mainCamera.combined);
        mainCamera.update();

        player.update();

        world.step(Gdx.graphics.getDeltaTime(), 6, 2);
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        world.dispose();
        for (int i = 0; i < bgs.length; i++) {
            bgs[i].getTexture().dispose();
        }
        player.getTexture().dispose();

        debugRenderer.dispose();
    }

    @Override
    public void beginContact(Contact contact) {
        Fixture body1;
        Fixture body2;

        if (contact.getFixtureA().getUserData() == "Player") {
            body1 = contact.getFixtureA();
            body2 = contact.getFixtureB();
        } else {
            body1 = contact.getFixtureB();
            body2 = contact.getFixtureA();
        }

        if (body1.getUserData() == "Player" && body2.getUserData() == "Coin") {
            //collided with the coin
            hud.incrementCoins();
            body2.setUserData("Remove");
            cloudsController.removeCollectables();
        }

        if (body1.getUserData() == "Player" && body2.getUserData() == "Life") {
            //collided with the life
            hud.incrementLifes();
            body2.setUserData("Remove");
            cloudsController.removeCollectables();
        }

        if (body1.getUserData() == "Player" && body2.getUserData() == "Dark Cloud") {
            if (!player.isDied()) {
                playerDied();
            }
        }
    }

    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {

    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}// gameplay
