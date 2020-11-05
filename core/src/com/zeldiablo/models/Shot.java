package com.zeldiablo.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.zeldiablo.controllers.MouseListener;

import static java.lang.Math.sin;
import static java.lang.StrictMath.cos;

public class Shot {
    private static final int SPEED = 500;
    private float x;
    private float y;
    private float hitboxx;
    private float hitboxy;
    private Body body;
    private World world;
    private boolean isAlive;

    public Shot(World world, float x, float y, int reach, int width, float angle, MouseListener mouse){
        this.x = x;
        this.y = y;
        this.world = world;
        this.hitboxx = reach;
        this.hitboxy = width;
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(this.x + (float)cos(angle)*20, this.y + (float)sin(angle)*20);
        body = world.createBody(bd);
        FixtureDef fixture = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(reach / 2f, width / 2f, new Vector2(0, 0), angle);
        fixture.shape = shape;
        fixture.density = 1f;
        fixture.restitution = 0f;
        fixture.friction = 0f;

        body.createFixture(fixture);
        shape.dispose();
    }


    public void update(){
        if (!isAlive) {
            body.destroyFixture(body.getFixtureList().items[0]);
            world.destroyBody(body);
        }
    }

    public void render(SpriteBatch sb){
        sb.begin();
        //TODO: Texture du coup (coup d'épée / fleche / etc..)
        //sb.draw(texture, x ,y);
        sb.end();
        this.update();
    }
}
