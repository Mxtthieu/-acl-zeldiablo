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
    protected Body bodyPiege;
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
        shape.setAsBox(width/2f,height/2f,new Vector2(width,height), angle);
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
    public abstract void draw(SpriteBatch batch);

    /**
     * Cette procedure permet d'appliquer l'effect du piege sur le joueur
     */
    public abstract void applyEffectToPlayer();

    /**
     * Cette procedure permet de reset les timer si le piege en utilise un
     */
    public abstract void clearTimer();

    /**
     * Cette procedure permet de mettre en pause les timers si le piege en utilise un
     */
    public abstract void pause();

    /**
     * Cette fonction permet de retourner le body du piege
     * @return Body
     */
    public Body getBody() {
        return bodyPiege;
    }

    /**
     * Cette procedure permet de mettre en marche les timers si le piege en utilise un
     */
    public abstract void play();

    public float getWidth() {
        return this.width;
    }

    public abstract void removeProjectile(Projectile p);
}
