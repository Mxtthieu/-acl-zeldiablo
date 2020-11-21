package com.zeldiablo.models.weapons.weaponsCAC;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
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
    protected float angle;

    protected float tmpAnim;
    protected Animation animation;
    protected boolean attacking;

    protected Timer timer;
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
        this.angle = 0f;
        this.timer = new Timer();
        this.timertask = new Timer.Task() {
            @Override
            public void run() {
                canAtk = true;
            }
        };

        this.tmpAnim = Gdx.graphics.getDeltaTime();
        this.attacking = false;
    }

    public void attack(float radius, float x, float y, float angle, World world){
        if (canAtk){
            createHitbox(radius, x, y, angle, world);
            this.attacking = true;
        }
    }

    public void createHitbox(float radius, float x, float y, float angle, final World world){
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        this.setPositionBody(bd,x,y,angle,radius);
        this.hitbox = world.createBody(bd);
        this.hitbox.setUserData(this);
        FixtureDef fixture = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(reach / 2f, width / 2f, new Vector2(0, 0), angle);
        fixture.shape = shape;
        fixture.density = 1f;
        fixture.restitution = 0f;
        fixture.friction = 0f;
        fixture.isSensor = true;
        hitbox.createFixture(fixture);
        shape.dispose();
        canAtk = false;
        timer.scheduleTask(timertask, this.atkspeed);
        this.destroytask = new Timer.Task() {
            @Override
            public void run() {
                world.destroyBody(hitbox);
                attacking = false;
            }
        };
        timer.scheduleTask(destroytask, 0.2f);
    }

    public abstract void setPositionBody(BodyDef bd, float x, float y, float angle, float radius);

    public abstract void draw(SpriteBatch sb);

    public abstract void effect(Body b);
}
