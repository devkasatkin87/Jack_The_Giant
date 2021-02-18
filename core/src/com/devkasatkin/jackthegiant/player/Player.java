package com.devkasatkin.jackthegiant.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.devkasatkin.jackthegiant.helpers.GameInfo;

public class Player extends Sprite {
    private World world;
    private Body body;

    public Player(World world, float x, float y) {
        super(new Texture("Player/Player 1.png"));
        this.world = world;
        setPosition(x, y);
        createBody();
    }

    private void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(getX() / GameInfo.PPM, getY() / GameInfo.PPM);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / 2f - 15) / GameInfo.PPM, (getHeight() / 2f / GameInfo.PPM));

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.density = 4f; //this is the mass of body
        fixtureDef.friction = 2f; // will make player not slide on surface
        fixtureDef.shape = shape;

        Fixture fixture = body.createFixture(fixtureDef);

        shape.dispose();
    }

    public void update() {
        setPosition(body.getPosition().x * GameInfo.PPM,
                body.getPosition().y * GameInfo.PPM);
    }

    public void draw(SpriteBatch batch) {
        batch.draw(this, this.getX() + getWidth() / 2f - 7, this.getY() - getHeight() / 2f);
    }
}
