package com.zeldiablo.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.PolygonBatch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.zeldiablo.controllers.MouseListener;

public class Shot {
    private static final int SPEED = 500;
    private float x;
    private float y;
    private float hitboxx;
    private float hitboxy;
    private Body body;
    private World world;
    public boolean remove = false;

    public Shot(World world, float x, float y, int reach, int width, float angle, MouseListener mouse){
        this.x = x;
        this.y = y;
        this.world = world;
        this.hitboxx = reach;
        this.hitboxy = width;
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(this.x, this.y);
        body = world.createBody(bd);

        FixtureDef fixture = new FixtureDef();
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(reach / 2f, width / 2f, new Vector2(-20, -20), angle);
        fixture.shape = shape;
        fixture.density = 1f;
        fixture.restitution = 0.25f;
        fixture.friction = 0f;

        body.createFixture(fixture);
        shape.dispose();
    }

    public void update(float deltaTime){
        y += SPEED + deltaTime;
        body.destroyFixture(body.getFixtureList().items[0]);
        world.destroyBody(body);
    }

    public void render(SpriteBatch sb){
        sb.begin();
        //TODO: Texture du coup (coup d'épée / fleche / etc..
        //sb.draw(texture, x ,y);
        sb.end();
    }
}
