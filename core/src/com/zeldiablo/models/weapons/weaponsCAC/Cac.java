package com.zeldiablo.models.weapons.weaponsCAC;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public abstract class Cac {

    protected float atkspeed;
    protected int width;
    protected int reach;
    protected int damage;
    protected String name;
    protected boolean canAtk;

    protected Timer timer;
    protected Timer timer2;
    protected Timer.Task timertask;
    protected Timer.Task destroytask;
    protected Body hitbox;
    
    public Cac(String name, float as, int w, int r, int d){
        this.name = name;
        this.atkspeed = as;
        this.width = w;
        this.reach = r;
        this.damage = d;
        this.canAtk = true;
        this.timer = new Timer();
        this.timer2 = new Timer();
        this.timertask = new Timer.Task() {
            @Override
            public void run() {
                canAtk = true;
            }
        };
    }

    public void attack(float radius, float x, float y, float angle, World world){
        if (canAtk){
            createHitbox(radius, x, y, angle, world);
        }
    }

    public void createHitbox(float radius, float x, float y, float angle, final World world){
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        if (this.name == "Sword") {
            bd.position.set(x + (float) cos(angle) * ((radius+6) * 2), y + (float) sin(angle) * ((radius+6) * 2));
        } else if (this.name == "Hammer") {
            bd.position.set(x + (float) cos(angle) * ((radius+1) * 2), y + (float) sin(angle) * ((radius+1) * 2));
        } else if (this.name == "Spear") {
            bd.position.set(x + (float) cos(angle) * ((radius+10) * 2), y + (float) sin(angle) * ((radius+10) * 2));
        } else if (this.name == "Dagger") {
            bd.position.set(x + (float) cos(angle) * (radius * 2), y + (float) sin(angle) * (radius * 2));
        }
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
        timer.scheduleTask(timertask, this.atkspeed);
        this.destroytask = new Timer.Task() {
            @Override
            public void run() {
                world.destroyBody(hitbox);
            }
        };
        timer.scheduleTask(destroytask, 0.5f);
    }

    public abstract void draw(SpriteBatch sb);
}
