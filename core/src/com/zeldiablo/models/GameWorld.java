package com.zeldiablo.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.zeldiablo.models.monsters.Monster;
import com.zeldiablo.models.monsters.Skeleton;
import com.zeldiablo.models.portals.Portal;
import com.zeldiablo.models.traps.Trap;
import com.zeldiablo.models.traps.TrapDamage;
import com.zeldiablo.views.GameScreen;

public class GameWorld {

    // --- Variables static qui définissent la taille du monde virtuel
    public static final int WIDTH = 80;
    public static final int HEIGHT = 60;

    // --- Eléments du jeu
    private GameScreen screen;
    private World world;
    private Player player;
    private Maze maze;
    private Monster monster;

    // --- Données Téleportation
    public boolean isTp;
    public Portal portal;

    public GameWorld(GameScreen s) {
        this.screen = s;
        this.world = new World(new Vector2(0, 0), true);
        this.player = new Player(this, "Tester");
        this.monster = new Skeleton(this, 50, 50, this.player);
        this.maze = new Maze(this);
        this.isTp = false;
        Trap p = new TrapDamage(new Vector2(30,30),world);
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
                p.getBody().setTransform(por.getPosPortalExit().x +3 ,por.getPosPortalExit().y ,0f);
                maze.loadMaze(por.getExitPortalNumMaze());
            }
        }
    }

    public void reset() {
        this.maze.resetMaze();
        this.maze.loadMaze(1);
        this.world.destroyBody(this.player.getBody());
        this.player = new Player(this, "TESTER");
    }

    public void atk(){
        this.player.attack(screen.getAngle());
    }
}
