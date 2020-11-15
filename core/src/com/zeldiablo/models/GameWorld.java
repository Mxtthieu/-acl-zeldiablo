package com.zeldiablo.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Array;
import com.zeldiablo.models.monsters.Monster;
import com.zeldiablo.models.monsters.Skeleton;
import com.zeldiablo.models.portals.Portal;
import com.zeldiablo.models.traps.Projectile;
import com.zeldiablo.models.traps.Trap;
import com.zeldiablo.models.traps.TrapDamage;
import com.zeldiablo.views.GameScreen;

import java.util.ArrayList;

public class GameWorld {

    // --- Variables static qui définissent la taille du monde virtuel
    public static final int WIDTH = 80;
    public static final int HEIGHT = 60;

    // --- Eléments du jeu
    private GameScreen screen;
    private ArrayList<Body> bodiesToDelet;
    private World world;
    private Player player;
    private Maze maze;
    private boolean pause;

    // --- Données Téleportation
    public boolean isTp;
    public Portal portal;

    public GameWorld(GameScreen s) {
        this.screen = s;
        this.bodiesToDelet = new ArrayList<>();
        this.world = new World(new Vector2(0, 0), true);
        this.player = new Player(this, "Tester");
        this.maze = new Maze(this);
        this.isTp = false;
        this.pause = false;
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
                p.getBody().setTransform(por.getPosPortalExit().x + 3 ,por.getPosPortalExit().y ,0f);
                loadMaze(por.getExitPortalNumMaze());
            }
        }
    }

    public void loadMaze(int num) {
        this.maze.resetMaze();
        this.player = new Player(this, "TESTER");
        this.maze.loadMaze(num);
    }

    public void atk(){
        this.player.attack(screen.getAngle());
    }

    public void deleteBodies() {
        if(bodiesToDelet.size() > 0){
            world.destroyBody(bodiesToDelet.get(0));
            bodiesToDelet.clear();
        }
    }

    public void addBodyToDelete(Body body) {
        this.bodiesToDelet.add(body);
    }

    /**
     * Permet d'activer ou désactiver la pause des éléments de GameWorld
     */
    public void switchPause() {
        if (this.pause) {
            this.maze.setPause(true);
        }
    }
}
