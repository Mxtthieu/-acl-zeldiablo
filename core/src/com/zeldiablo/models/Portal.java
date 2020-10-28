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
    private boolean actif;

    public Portal(int num, Vector2 pos , Portal exitP, World world){

        this.numMaze = num;
        this.exitPortal = exitP;
        this.actif = true;
        this.posPortal = pos;

        taille = (1/40f)* 700;
        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.StaticBody;
        bodydef.position.set(posPortal);
        bodyPortal = world.createBody(bodydef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(taille);
        fixtureDef.shape = shape;
        //fixtureDef.isSensor = true;
        bodyPortal.setUserData(this);
        bodyPortal.createFixture(fixtureDef);
        shape.dispose();

    }

    public void draw(SpriteBatch batch){
        batch.begin();
        //batch.draw(); Ajout Sprite
        batch.end();
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

    public int getExitPortalNumMaze(){
        return this.exitPortal.getNumMaze();
    }

    public boolean exitSameMaze(){
        return getNumMaze() == getExitPortalNumMaze();
    }

    public boolean isActif() {
        return actif;
    }

}
