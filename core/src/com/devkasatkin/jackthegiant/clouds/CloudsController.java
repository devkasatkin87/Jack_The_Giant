package com.devkasatkin.jackthegiant.clouds;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.devkasatkin.jackthegiant.helpers.GameInfo;

import java.util.Random;

public class CloudsController {
    private World world;
    private Array<Cloud> clouds = new Array<>();

    private final float DISTANCE_BETWEEN_CLOUDS = 250f;
    private float minX;
    private float maxX;

    private Random random = new Random();

    public CloudsController(World world) {
        this.world = world;
        minX = GameInfo.WIDTH / 2f - 120;
        maxX = GameInfo.WIDTH / 2f + 120;
        createClouds();
        positionClouds();
    }

    private void createClouds() {
        for (int i = 0; i < 2; i++) {
            clouds.add(new Cloud(world, "Dark Cloud"));
        }

        int indexClouds = 1;

        for (int i = 0; i < 6; i++) {
                clouds.add(new Cloud(world, "Cloud " + indexClouds));
                indexClouds++;
                if (indexClouds == 4) indexClouds = 1;
        }

        clouds.shuffle();

    }

    public void positionClouds() {

        while (clouds.get(0).getCloudName().equals("Dark Cloud")) {
            clouds.shuffle();
        }

        float positionY = GameInfo.HEIGHT / 2f;

        int controlX = 0;

        for (Cloud c: clouds) {
            float tempX = 0;
            if (controlX == 0) {
                tempX = randomBetweenNumbers(maxX - 50, maxX);
                controlX = 1;
            }else if (controlX == 1) {
                tempX = randomBetweenNumbers(minX + 50, minX);
                controlX = 0;
            }
            c.setSpritePosition(tempX, positionY);
            positionY -= DISTANCE_BETWEEN_CLOUDS;
        }
    }

    public void drawClouds(SpriteBatch batch) {
        for (Cloud c:
             clouds) {
            batch.draw(c, c.getX() - c.getWidth() / 2f,
                    c.getY() - c.getHeight() / 2f);
        }
    }

    private float randomBetweenNumbers(float min, float max) {
        return random.nextFloat() * (max - min) + min;
    }
}
