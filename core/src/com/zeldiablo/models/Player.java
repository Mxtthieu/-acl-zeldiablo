package com.zeldiablo.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.zeldiablo.models.weapons.weaponsCAC.*;

/**
 * @author Sousa Ribeiro Pedro
 */
public class Player implements Entity {

    private String name;
    private int hp;
    private int att;
    private int def;
    private int speed;
    private Cac weapon;
    private Body body;
    private GameWorld gameWorld;

    public Player(GameWorld gameWorld, String n) {
        this.name = n;
        this.hp = 100;
        this.att = 0;
        this.def = 0;
        this.speed = 20;
        this.weapon = new Sword();
        this.gameWorld = gameWorld;

        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(10, 10);
        body = this.gameWorld.getWorld().createBody(bd);
        body.setUserData(this);

        FixtureDef fixture = new FixtureDef();
        Shape shape = new CircleShape();
        shape.setRadius(1f/50f*GameWorld.WIDTH);
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
        this.body.setTransform(this.getPosition(), angle);
        this.body.setLinearVelocity(dx*this.speed, dy*this.speed);
    }

    /**
     * Methode seravnt a récupérer la position de l'entité
     * @return Vector2 de la position
     */
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
        return this.hp;
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
        return this.name;
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

    /**
     * Permet de déssiner le joueur sur l'ensemble de sprites présent sur l'écran
     *
     * @param batch SpriteBatch qui regroupe tous les sprite déssiné à l'écran
     */
    @Override
    public void draw(SpriteBatch batch) {
        batch.begin();
        //TODO: Ajouter ici la texture du personnage
        batch.end();
    }

    @Override
    public float getRadius() {
        Fixture fixture = this.body.getFixtureList().get(0);
        return fixture.getShape().getRadius();
    }

    public void attack(float angle){
        weapon.attack(this.getRadius() ,this.body.getPosition().x,this.body.getPosition().y, angle, gameWorld.getWorld());
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public Cac getWeapon() {
        return weapon;
    }
}
