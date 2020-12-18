package com.zeldiablo.models.treasure;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.zeldiablo.factories.SoundFactory;
import com.zeldiablo.factories.TextureFactory;
import com.zeldiablo.models.GameWorld;

public class Treasure {

    private int treasurePoint;
    private GameWorld gameWorld;
    private Body body;
    private int posX;
    private int posY;
    private boolean pickedUp;

    private static final float RADIUS = 1/90f * GameWorld.WIDTH;

    public Treasure(GameWorld gameWorld, int x, int y, int treasurePoint) {
        this.gameWorld = gameWorld;
        this.treasurePoint = treasurePoint;
        this.posX = x;
        this.posY = y;
        this.pickedUp = false;

        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.StaticBody;
        bodydef.position.set(posX, posY);
        body = gameWorld.getWorld().createBody(bodydef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(RADIUS);
        shape.setPosition(new Vector2(0, 0));
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        body.setUserData(this);
        body.createFixture(fixtureDef);
        shape.dispose();

    }

    public int getTreasurePoint() {
        return treasurePoint;
    }

    public void effect(GameWorld gameWorld){
        this.gameWorld.increaseScore(10);
        this.pickedUp = true;
        SoundFactory.getInstance().coins.play();
    }

    public void draw(SpriteBatch batch) {
        if (!this.pickedUp) {
            batch.begin();
            batch.draw(TextureFactory.INSTANCE.getTreasure(), this.posX - RADIUS, this.posY - RADIUS, RADIUS * 2, RADIUS * 2);
            batch.end();
        }
    }
}
