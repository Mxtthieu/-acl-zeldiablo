package com.zeldiablo.models.traps;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.zeldiablo.models.GameWorld;
import com.zeldiablo.models.Player;

public abstract class Trap {

    private boolean touch;
    protected Vector2 pos;
    protected Body bodyPiege;
    private float height;
    private float width;
    protected GameWorld gameWorld;

    public Trap(Vector2 pos, GameWorld gameWorld){

        this.touch = true;
        this.pos = pos;
        this.height = (1/60f)* GameWorld.HEIGHT;
        this.width = (1/80f)*GameWorld.WIDTH;

        this.gameWorld = gameWorld;

        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.StaticBody;
        bodydef.position.set(this.pos);
        bodyPiege = gameWorld.getWorld().createBody(bodydef);
        float[] vertice = {0f, 0f, 0f,this.height, this.width, this.height, this.width, 0f, 0f, 0f};
        FixtureDef fixtureDef = new FixtureDef();
        ChainShape shape = new ChainShape();
        shape.createChain(vertice);
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        bodyPiege.setUserData(this);
        bodyPiege.createFixture(fixtureDef);
        shape.dispose();
    }

    /**
     * Permet de déssiner le joueur sur l'ensemble de srpites présent sur l'écran
     * @param batch SpriteBatch qui regroupe tous les sprite déssiné à l'écran
     */
    public abstract void draw(SpriteBatch batch);

    public abstract void applyEffectToPlayer();

    public abstract void clearTimer();

    public float getWidth() {
        return this.width;
    }

    public abstract void removeProjectile(Projectile p);
}
