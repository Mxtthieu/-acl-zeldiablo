package com.zeldiablo.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.zeldiablo.models.piege.Piege;
import com.zeldiablo.models.piege.PiegeDegat;
import com.zeldiablo.models.piege.PiegeRalentissant;
import com.zeldiablo.views.GameScreen;

import java.util.ArrayList;

public class GameWorld {

    // --- Variables static qui définissent la taille du monde virtuel
    public static final int WIDTH = 80;
    public static final int HEIGHT = 60;

    // --- Eléments du jeu
    private GameScreen screen;
    private World world;
    private Player player;
    private Maze maze;
    // --- Données Téleportation
    public boolean isTp;
    public Portal portal;

    public GameWorld(GameScreen s) {
        this.screen = s;
        this.world = new World(new Vector2(0, 0), true);
        this.player = new Player(this, "Tester");
        this.maze = new Maze(this);
        this.isTp = false;
    }

    /**
     * Demande au modèle Player de s'afficher sur le jeu
     * @param batch ensemble de sprite
     */
    public void draw(SpriteBatch batch) {
        this.player.draw(batch);
        this.maze.draw(batch);
    }

    public World getWorld() {
        return this.world;
    }

    public Player getPlayer() {
        return this.player;
    }

    /***
     * Cette Fonction teleporte le joueur au portail de sortie par rapport au portail selectionner
     * @param p Player
     * @param por Portal
     */
    public void teleport(Player p, Portal por){
        //Si le portail est actif je peux teleporter
        if(por.isActif()) {
            // Je rend le portail de sortie inactif pour eviter de teleporter en boucle
            por.setExitPortalActif(false);
            // Je teleporte le joueur a la position du portail de sortie.
            p.getBody().setTransform(por.getPosPortalExit().x ,por.getPosPortalExit().y ,0f);
            // Si le portail de sortie n'est pas dans le meme labyrinthe on teleporte le joueur dans l'autre
            if (!por.exitSameMaze()) {
                maze.loadMaze(por.getExitPortalNumMaze());
            }
        }
    }

    public void reset() {
        this.maze.resetMaze();
        this.maze.loadMaze(0);
        this.world.destroyBody(this.player.getBody());
        this.player = new Player(this, "TESTER");
    }
}
