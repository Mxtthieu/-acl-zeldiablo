package com.zeldiablo.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.zeldiablo.controllers.MouseListener;

import java.util.ArrayList;

/**
 * @author Sousa Ribeiro Pedro
 */
public class Player implements Entity {

    private String name;
    private int hp;
    private int att;
    private int def;
    private Body body;
    private Weapon weapon;
    private World world;
    private ArrayList<Shot> shots = new ArrayList<>();

    public Player(World world, String n) {
        this.world = world;
        this.name = n;
        this.hp = 20;
        this.att = 0;
        this.def = 0;
        this.weapon = new Weapon();

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(50, 50);
        body = world.createBody(bd);

        FixtureDef fixture = new FixtureDef();
        Shape shape = new CircleShape();
        shape.setRadius(20);
        fixture.shape = shape;
        fixture.density = 1f;
        fixture.restitution = 0.25f;
        fixture.friction = 0f;

        body.createFixture(fixture);
        shape.dispose();

    }

    /**
     * Effectue un déplacement du joueur
     *
     * @param dx déplacement en X
     * @param dy déplacement en Y
     */
    public void move(float dx, float dy, float angle) {
        Vector2 pos = body.getPosition();
        this.body.setTransform(pos.x + dx, pos.y + dy, angle);
    }

    public Vector2 getPosition() {
        return this.body.getPosition();
    }

    public Body getBody() {
        return this.body;
    }

    /**
     * Méthode servant a récupérer les points de vie du joueur
     *
     * @return int hp du joueur
     */
    @Override
    public int getHP() {
        return 0;
    }

    /**
     * Méthode servant a récupérer les points d'attaque du joueur
     *
     * @return int att du joueur
     */
    @Override
    public int getATT() {
        return 0;
    }

    /**
     * Méthode servant a récupérer les points de défense du joueur
     *
     * @return int def du joueur
     */
    @Override
    public int getDEF() {
        return 0;
    }

    /**
     * Méthode servant a récupérer le nom (ou pseudo) du joueur
     *
     * @return String nom du joueur
     */
    @Override
    public String getName() {
        return null;
    }

    /**
     * Permet de déssiner le joueur sur l'ensemble de srpites présent sur l'écran
     *
     * @param batch SpriteBatch qui regroupe tous les sprite déssiné à l'écran
     */
    @Override
    public void draw(SpriteBatch batch) {
        batch.begin();
        //TODO: Ajouter ici la texture du personnage
        batch.end();
    }

    public void attack(float x, float y, float angle, MouseListener mouse){
        shots.add(new Shot(world,x+20, y+20, this.weapon.getReach(), this.weapon.getWidth(), angle, mouse));
    }
}
