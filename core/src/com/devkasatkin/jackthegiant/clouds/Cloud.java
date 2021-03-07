package com.devkasatkin.jackthegiant.clouds;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;
import com.devkasatkin.jackthegiant.helpers.GameInfo;

public class Cloud extends Sprite {
    private World world;
    private Body body;
    private String cloudName;

    private boolean drawLeft;

    public Cloud(World world, String cloudName) {
        super(new Texture("Clouds/" + cloudName + ".png"));
        this.world = world;
        this.cloudName = cloudName;
    }

    public boolean isDrawLeft() {
        return drawLeft;
    }

    public void setDrawLeft(boolean drawLeft) {
        this.drawLeft = drawLeft;
    }

    public String getCloudName() {
        return cloudName;
    }

    private void createBody() {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;

        bodyDef.position.set((getX() - 45)/ GameInfo.PPM,
                getY() / GameInfo.PPM);
        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox((getWidth() / 2 - 20) / GameInfo.PPM,
                (getHeight() / 2 - 10) / GameInfo.PPM);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;

        Fixture fixture = body.createFixture(fixtureDef);
        fixture.setUserData(cloudName);

        shape.dispose();
    }

    public void setSpritePosition(float x, float y) {
        setPosition(x, y);
        createBody();
    }


}
