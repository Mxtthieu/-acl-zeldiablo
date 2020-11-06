package com.zeldiablo.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Sword extends Cac {
    private float atkspeed;
    private int width;
    private int reach;
    private int damage;
    private Body hitbox;
    private boolean canAtk;
    private Timer timer;
    private Timer.Task timertask;

    public Sword(){
        this.damage = 1;
        this.atkspeed = 1;
        this.width = 18;
        this.reach = 65;
        this.canAtk = true;
        timer = new Timer();
        timertask = new Timer.Task() {
            @Override
            public void run() {
                canAtk = true;
            }
        };
    }

    @Override
    public void attack(float x, float y, float angle, World world){
        if (canAtk) {
            BodyDef bd = new BodyDef();
            bd.type = BodyDef.BodyType.DynamicBody;
            bd.position.set(x + (float) cos(angle) * 52, y + (float) sin(angle) * 52);
            this.hitbox = world.createBody(bd);
            FixtureDef fixture = new FixtureDef();
            PolygonShape shape = new PolygonShape();
            shape.setAsBox(reach / 2f, width / 2f, new Vector2(0, 0), angle);
            fixture.shape = shape;
            fixture.density = 1f;
            fixture.restitution = 0f;
            fixture.friction = 0f;
            hitbox.createFixture(fixture);
            shape.dispose();
            canAtk = false;
            timer.scheduleTask(timertask, 2);
        }
    }

    @Override
    public void draw(SpriteBatch sb) {
        sb.begin();
        // TODO : Ajouter texture Sword
        sb.end();
    }

    @Override
    public int getRange() {
        return 0;
    }

    @Override
    public String getName() {
        return "Sword";
    }

    @Override
    public Body getBodyWeapon() {
        return hitbox;
    }
}
