package com.zeldiablo.models;

public interface Effectable {

    /**
     * Diminue les points de vie de l'objet par un nombre hp de points.
     * @param hp Points de vie à retirer
     * @return int - hp après execution de la méthode
     */
    public int decreaseHP(int hp);

    /**
     * Augmente les points de vie de l'objet par un nombre hp de points.
     * @param hp Points de vie à ajouter
     * @return int - hp après execution de la méthode
     */
    public int increaseHP(int hp);
}
