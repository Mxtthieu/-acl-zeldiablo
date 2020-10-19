package com.zeldiablo.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public class Player implements Entity {

    private final String name;
    private int hp;
    private int att;
    private int def;
    private Body body;

    public Player(String n) {
        this.name = n;
        this.hp = 20;
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
}
