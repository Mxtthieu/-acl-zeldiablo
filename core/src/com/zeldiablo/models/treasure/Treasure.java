package com.zeldiablo.models.treasure;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.zeldiablo.models.GameWorld;

public class Treasure {

    private int treasurePoint;
    private GameWorld gameWorld;
    private Body body;
    private int posX;
    private int posY;

    public Treasure(GameWorld gameWorld, int x, int y, int treasurePoint) {
        this.gameWorld = gameWorld;
        this.treasurePoint = treasurePoint;
        this.posX = x;
        this.posY = y;

        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.StaticBody;
        bodydef.position.set(posX, posY);
        body = gameWorld.getWorld().createBody(bodydef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(1/90f * GameWorld.WIDTH);
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
        this.gameWorld.getGameStats().increaseScore();
    }

    public void draw(SpriteBatch batch) {

    }
}
