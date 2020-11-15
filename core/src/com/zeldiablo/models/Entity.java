package com.zeldiablo.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Sousa Ribeiro Pedro
 */
public interface Entity {

    float SIZE = (float)GameWorld.WIDTH / 30;

    /**
     * Méthode servant a récupérer les points de vie du joueur
     * @return int hp du joueur
     */
    int getHP();

    /**
     * Méthode servant a récupérer les points d'attaque du joueur
     * @return int att du joueur
     */
    int getATT();

    /**
     * Méthode servant a récupérer les points de défense du joueur
     * @return int def du joueur
     */
    int getDEF();

    /**
     * Méthode servant a récupérer le nom (ou pseudo) du joueur
     * @return String nom du joueur
     */
    String getName();

    /**
     * Permet de déssiner le joueur sur l'ensemble de srpites présent sur l'écran
     * @param batch SpriteBatch qui regroupe tous les sprite déssiné à l'écran
     */
    void draw(SpriteBatch batch);

    /**
     * Permet de récupérer le radius de l'entité
     * @return le radius
     */
    float getRadius();

    /**
     * Methode seravnt a récupérer la position de l'entité
     * @return Vector2 de la position
     */
    Vector2 getPosition();

    /**
     * Méthode servant a récupérer la position en X de l'entité
     * @return int coordonée X
     */
    int getX();

    /**
     * Méthode servant a récupérer la position en Y de l'entité
     * @return int coordonée Y
     */
    int getY();

}
