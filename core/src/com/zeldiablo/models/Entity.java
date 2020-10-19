package com.zeldiablo.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface Entity {

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
     * Méthode servant a récupérer le nom (ou pseudo) du joueur
     * @return String nom du joueur
     */
    public String getName();

    /**
     * Permet de déssiner le joueur sur l'ensemble de srpites présent sur l'écran
     * @param batch SpriteBatch qui regroupe tous les sprite déssiné à l'écran
     */
    public void draw(SpriteBatch batch);
}
