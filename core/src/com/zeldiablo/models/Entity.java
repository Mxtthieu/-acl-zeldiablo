package com.zeldiablo.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Sousa Ribeiro Pedro
 */
public interface Entity {

    public static float SIZE = GameWorld.WIDTH / 20;

    /**
     * Méthode servant a récupérer les points de vie du joueur
     * @return int hp du joueur
     */
    public int getHP();

    /**
     * Méthode servant a récupérer les points d'attaque du joueur
     * @return int att du joueur
     */
    public int getATT();

    /**
     * Méthode servant a récupérer les points de défense du joueur
     * @return int def du joueur
     */
    public int getDEF();

    /**
     * Méthode servant a récupérer le nom (ou pseudo) de l'entité
     * @return String nom du joueur
     */
    public String getName();

    /**
     * Methode seravnt a récupérer la position de l'entité
     * @return Vector2 de la position
     */
    public Vector2 getPosition();

    /**
     * Méthode servant a récupérer la position en X de l'entité
     * @return int coordonée X
     */
    public int getX();

    /**
     * Méthode servant a récupérer la position en Y de l'entité
     * @return int coordonée Y
     */
    public int getY();

    /**
     * Permet de déssiner le joueur sur l'ensemble de srpites présent sur l'écran
     * @param batch SpriteBatch qui regroupe tous les sprite déssiné à l'écran
     */
    public void draw(SpriteBatch batch);
}
