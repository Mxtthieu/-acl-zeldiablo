package com.zeldiablo.models.traps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.zeldiablo.factories.TextureFactory;
import com.zeldiablo.models.GameWorld;
import com.zeldiablo.models.Player;

import java.util.ArrayList;

public class Projectile {

    private int att;
    private Vector2 posProjectil;
    private static float taille;

    private Trap trapParent;
    private Body bodyProjectil;

    public Projectile(Trap trapParent, Vector2 pos, GameWorld gameWorld){

        this.posProjectil = pos;
        this.trapParent = trapParent;

        taille = (1/160f) * GameWorld.WIDTH;
        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.DynamicBody;
        bodydef.position.set(posProjectil);
        bodyProjectil = gameWorld.getWorld().createBody(bodydef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(taille);
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        bodyProjectil.setUserData(this);
        bodyProjectil.createFixture(fixtureDef);
        bodyProjectil.setLinearVelocity(5f,0f);
        shape.dispose();

    }

    public int getAtt() {
        return att;
    }

    public void effect(Body b){
        if(b.getUserData() instanceof Player){
            trapParent.applyEffectToPlayer();
        }

        this.trapParent.removeProjectile(this);
    }

    public void draw(SpriteBatch batch) {
        Texture tex = TextureFactory.INSTANCE.getCannonball();
        float x = this.bodyProjectil.getPosition().x;
        float y = this.bodyProjectil.getPosition().y;

        batch.begin();
        batch.draw(tex, x-taille, y-taille, taille*2, taille*2);
        batch.end();
    }

    public Body getBodyProjectil() {
        return bodyProjectil;
    }

    public Trap getTrapParent() {
        return trapParent;
    }
}
