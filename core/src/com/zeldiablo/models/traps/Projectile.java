package com.zeldiablo.models.traps;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.zeldiablo.models.GameWorld;
import com.zeldiablo.models.Player;
import static java.lang.Math.*;

public class Projectile {

    private int att;
    private Vector2 posProjectil;
    private static float taille;

    private Trap trapParent;
    private Body bodyProjectil;
    private float angle;

    public Projectile(Trap trapParent, Vector2 pos, float angle, GameWorld gameWorld){

        this.posProjectil = pos;
        this.trapParent = trapParent;
        this.angle = angle;

        taille = (1/160f) * GameWorld.WIDTH;
        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.DynamicBody;
        bodydef.position.set(posProjectil);
        bodyProjectil = gameWorld.getWorld().createBody(bodydef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(taille);
        fixtureDef.shape = shape;
        fixtureDef.isSensor = false;
        bodyProjectil.setUserData(this);
        bodyProjectil.createFixture(fixtureDef);
        bodyProjectil.setLinearVelocity((float)cos(angle)*5f,(float)sin(angle)*5f);
        shape.dispose();

    }
    /**
     * Cette fonction permet la valeur de l'attaque du projectil
     * @return int : la valeure de l'attaque
     */
    public int getAtt() {
        return att;
    }

    /**
     * Cette procedure d'appliquer l'effet du projectils sur un body
     * @param b Body sur lequel l'effect va etre appliquer
     */
    public void effect(Body b){
        if(b.getUserData() instanceof Player){
            trapParent.applyEffectToPlayer();
        }
    }

    /**
     * Cette fonction permet de retourner le body du projectil
     * @return Body
     */
    public Body getBodyProjectil() {
        return bodyProjectil;
    }

    public Trap getTrapParent() {
        return trapParent;
    }
}
