package com.zeldiablo.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public class Portal {

    Vector2 posPortal;
    Body bodyPortal;
    int numMaze;
    Portal exitPortal;
    private static float taille;

    public Portal(int num, Vector2 pos ,World world){

        posPortal = pos;
        taille = (1/40f)* 800;
        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.StaticBody;
        bodydef.position.set(posPortal);
        bodyPortal = world.createBody(bodydef);
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(taille);
        fixtureDef.shape = shape;
        bodyPortal.createFixture(fixtureDef);
        bodyPortal.setUserData("P");
        shape.dispose();

    }

    public void draw(SpriteBatch batch){
        /*batch.begin();
        batch.draw();
        batch.end();*/
    }

    public Portal(int num, Portal p){
        this.numMaze = num;
        this.exitPortal = p;
    }

    public int getNumMaze() {
        return numMaze;
    }

    public Vector2 getPosPortal() {
        return posPortal;
    }

    public Vector2 getPosPortalExit() {
        return exitPortal.getPosPortal();
    }
}
