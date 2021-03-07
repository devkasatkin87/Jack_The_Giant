package com.devkasatkin.jackthegiant.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.devkasatkin.jackthegiant.helpers.GameInfo;

public class Player extends Sprite {
    private World world;
    private Body body;

    private TextureAtlas playerAtlas;
    private Animation animation;
    private float elapsedTime;
    private boolean isWalking;
    private boolean died;

    public Player(World world, float x, float y) {
        super(new Texture("Player/Player 1.png"));
        this.world = world;
        setPosition(x, y);
        createBody();
        playerAtlas = new TextureAtlas("Player Animation/Player Animation.atlas");
    }

    private void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);
        body = world.createBody(bodyDef);
        body.setFixedRotation(true);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / 2f - 15) / GameInfo.PPM, (getHeight() / 2f / GameInfo.PPM));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 4f; //this is the mass of body
        fixtureDef.friction = 2f; // will make player not slide on surface
        fixtureDef.filter.categoryBits = GameInfo.PLAYER;
        fixtureDef.filter.maskBits = GameInfo.DEFAULT | GameInfo.COLLECTABLE;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData("Player");

        shape.dispose();
    }

    public void movePlayer(float x) {
        //moving left, flip the player to face direction
        if (x < 0 && !this.isFlipX()) {
            this.flip(true, false);
        } else if (x > 0 && this.isFlipX()) {
            //moving right, flip the player to face direction
            this.flip(true, false);
        }
        this.isWalking = true;
        body.setLinearVelocity(x, body.getLinearVelocity().y);
    }

    public void update() {
        if (body.getLinearVelocity().x > 0) {
            setPosition(body.getPosition().x * GameInfo.PPM,
                    body.getPosition().y * GameInfo.PPM);
        } else if (body.getLinearVelocity().x < 0) {
            setPosition((body.getPosition().x - 0.2f) * GameInfo.PPM,
                    body.getPosition().y * GameInfo.PPM);
        }
    }

    public void drawIdle(SpriteBatch batch) {
        if(!isWalking) {
            batch.draw(this, getX() + getWidth() / 2f - 20,
                    getY() - getHeight() / 2f);
        }
    }

    public void drawPlayerAnimation(SpriteBatch batch) {
        if(isWalking) {
            elapsedTime += Gdx.graphics.getDeltaTime();

            Array<TextureAtlas.AtlasRegion> frames = playerAtlas.getRegions();

            for(TextureRegion frame : frames) {
                if(body.getLinearVelocity().x < 0 && !frame.isFlipX()) {
                    frame.flip(true, false);
                } else if(body.getLinearVelocity().x > 0 && frame.isFlipX()) {
                    frame.flip(true, false);
                }
            }

            animation = new Animation(1f/10f, playerAtlas.getRegions());

            batch.draw((TextureRegion) animation.getKeyFrame(elapsedTime, true)
                    , getX() + getWidth() / 2f - 20f,
                    getY() - getHeight() / 2f);
        }
    }

    public void setWalking(boolean isWalking) {
        this.isWalking = isWalking;
    }

    public void setDied(boolean died) {
        this.died = died;
    }

    public boolean isDied() {
        return died;
    }
} // player
