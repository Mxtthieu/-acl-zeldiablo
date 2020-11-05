package com.zeldiablo.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Shape;

import java.awt.*;
import java.util.List;

public abstract class Monster implements Entity {

    private Entity target;
    private Body body;

    private List<Point> path;

    public Monster(World world, float x, float y, Entity tar) {
        this.target = tar;

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(x, y);
        body = world.createBody(bd);

        FixtureDef fixture = new FixtureDef();
        Shape shape = new CircleShape();
        shape.setRadius(20);
        fixture.shape = shape;
        fixture.density = 1f;
        fixture.restitution = 0.25f;
        fixture.friction = 0f;

        body.setUserData(this);
        body.createFixture(fixture);
        shape.dispose();
    }

    public void calculatePathToTarget() {

    }
}
