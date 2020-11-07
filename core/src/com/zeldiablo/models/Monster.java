package com.zeldiablo.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.physics.box2d.Shape;
import com.zeldiablo.controllers.ai.PathFinding;

import java.awt.*;
import java.util.List;

public abstract class Monster implements Entity {

    private Entity target;
    private Body body;

    private PathFinding finding;

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

        boolean[][] grid = new boolean[GameWorld.HEIGHT][GameWorld.WIDTH];
        for (int j = 0; j < GameWorld.HEIGHT; j++)
            for (int i = 0; i < GameWorld.WIDTH; i++)
                grid[j][i] = true;

        this.finding = new PathFinding(grid, this.getX(), this.getY(), this.target.getX(), this.target.getY());
        System.out.println(this.finding);
    }

    /**
     * Methode seravnt a récupérer la position de l'entité
     * @return Vector2 de la position
     */
    public Vector2 getPosition() {
        return this.body.getPosition();
    }

    /**
     * Méthode servant a récupérer la position en X de l'entité
     *
     * @return int coordonée X
     */
    @Override
    public int getX() {
        return (int) this.getPosition().x;
    }

    /**
     * Méthode servant a récupérer la position en Y de l'entité
     *
     * @return int coordonée Y
     */
    @Override
    public int getY() {
        return (int) this.getPosition().y;
    }
}
