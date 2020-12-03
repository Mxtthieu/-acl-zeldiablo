package com.zeldiablo.models.monsters;

import com.zeldiablo.controllers.Direction;
import com.zeldiablo.factories.TextureFactory;
import com.zeldiablo.models.GameWorld;
import com.zeldiablo.models.Player;

public class Skeleton extends Monster {

    public Skeleton(GameWorld gameWorld, float x, float y, Player target) {
        super(gameWorld, x, y, target, 100);
        this.animations.put(Direction.Down, TextureFactory.INSTANCE.getAnimatedSkeletonDown());
        this.animations.put(Direction.Left, TextureFactory.INSTANCE.getAnimatedSkeletonLeft());
        this.animations.put(Direction.Right, TextureFactory.INSTANCE.getAnimatedSkeletonRight());
        this.animations.put(Direction.Up, TextureFactory.INSTANCE.getAnimatedSkeletonUp());
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
        return null;
    }

    @Override
    public float getRadius() {
        return 0;
    }

    /**
     * Diminue les points de vie de l'objet par un nombre hp de points.
     *
     * @param hp Points de vie à retirer
     * @return int - hp après execution de la méthode
     */
    @Override
    public int decreaseHP(int hp) {
        this.hp -= hp;
        this.hp = Math.max(0, this.hp);
        return this.hp;
    }

    /**
     * Augmente les points de vie de l'objet par un nombre hp de points.
     *
     * @param hp Points de vie à ajouter
     * @return int - hp après execution de la méthode
     */
    @Override
    public int increaseHP(int hp) {
        if (hp > 0) {
            this.hp += hp;
        }
        return this.hp;
    }

    @Override
    public void effect(GameWorld gameWorld) {
        gameWorld.getPlayer().decreaseHP(10);
    }
}
