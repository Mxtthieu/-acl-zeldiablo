package com.zeldiablo.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.zeldiablo.views.GameScreen;

public class GameWorld {

    // --- Variables static qui définissent la taille du monde virtuel
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 720;

    // --- Eléments du jeu
    private GameScreen screen;
    private World world;
    private Player player;

    public GameWorld(GameScreen s) {
        this.screen = s;
        this.world = new World(new Vector2(0, 0), true);
        this.player = new Player(this.world, "Tester");
    }

    /**
     * Demande au modèle Player de s'afficher sur le jeu
     * @param batch ensemble de sprite
     */
    public void draw(SpriteBatch batch) {
        this.player.draw(batch);
    }

    public World getWorld() {
        return this.world;
    }

    public Player getPlayer() {
        return this.player;
    }

    public void atk(){
        this.player.attack(screen.getAngle(), screen.getMouse());
    }
}
