package com.zeldiablo.models.traps;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.zeldiablo.models.GameWorld;
import com.zeldiablo.models.Player;

public abstract class Trap {

    private boolean touch;
    private boolean actif;
    protected Vector2 pos;
    private Body bodyPiege;
    protected float height;
    protected float width;
    protected GameWorld gameWorld;

    public Trap(Vector2 pos, float angle, GameWorld gameWorld){

        this.touch = true;
        this.pos = pos;
        this.height = (1/60f)* GameWorld.HEIGHT;
        this.width = (1/80f)*GameWorld.WIDTH;

        this.gameWorld = gameWorld;

        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.StaticBody;
        bodydef.position.set(this.pos);
        bodyPiege = gameWorld.getWorld().createBody(bodydef);
        FixtureDef fixtureDef = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(height/2f,width/2f,new Vector2(0,0), angle);
        fixtureDef.shape = shape;
        fixtureDef.isSensor = false;
        bodyPiege.setUserData(this);
        bodyPiege.createFixture(fixtureDef);
        shape.dispose();
    }

    /**
     * Permet de déssiner le joueur sur l'ensemble de srpites présent sur l'écran
     * @param batch SpriteBatch qui regroupe tous les sprite déssiné à l'écran
     */
    public void draw(SpriteBatch batch){
        batch.begin();
        //batch.draw(); Ajout Sprite
        batch.end();
    }

    public abstract void applyEffectToPlayer();

    public abstract void clearTimer();

    public abstract void pause();

    public Body getBody() {
        return bodyPiege;
    }

    public abstract void play();
}
