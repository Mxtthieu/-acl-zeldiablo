package com.zeldiablo.models.traps;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.zeldiablo.models.GameWorld;

public class Projectile {

    private int att;
    private Vector2 posProjectil;
    private static float taille;
    private Body bodyProjectil;
    private boolean touch;

    public Projectile(Vector2 pos, World world){

        this.posProjectil = pos;
        this.touch = false;

        taille = (1/160f) * GameWorld.WIDTH;
        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.DynamicBody;
        bodydef.position.set(posProjectil);
        bodyProjectil = world.createBody(bodydef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(taille);
        fixtureDef.shape = shape;
        bodyProjectil.setUserData(this);
        bodyProjectil.createFixture(fixtureDef);
        shape.dispose();

    }

    public Body getBodyProjectil() {
        return bodyProjectil;
    }

    public Vector2 getPosProjectil() {
        return posProjectil;
    }

    public void setDirection(){
        bodyProjectil.setLinearVelocity(5f,0f);
    }

    public boolean isTouch() {
        return touch;
    }

    public void setTouch(boolean touch) {
        this.touch = touch;
    }

    public int getAtt() {
        return att;
    }
}
