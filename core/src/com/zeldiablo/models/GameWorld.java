package com.zeldiablo.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.zeldiablo.views.GameScreen;

import java.util.ArrayList;

public class GameWorld {

    // --- Variables static qui définissent la taille du monde virtuel
    public static final int WIDTH = 1024;
    public static final int HEIGHT = 720;

    // --- Eléments du jeu
    private GameScreen screen;
    private World world;
    private Player player;
    private ArrayList<Portal>portals;
    private Monster monster;

    // --- Données Téleportation
    public boolean isTp;
    public Portal portal;

    public GameWorld(GameScreen s) {
        this.screen = s;
        this.world = new World(new Vector2(0, 0), true);
        this.player = new Player(this.world, "Tester");
        this.portals = new ArrayList<>();
        this.isTp = false;
        this.portal = null;
        Portal p1 = new Portal(1, new Vector2(600, 300),this.world);
        Portal p2 = new Portal(1, new Vector2(300, 40),this.world);
        Portal p3 = new Portal(1, new Vector2(500, 600),this.world);
        Portal p4 = new Portal(1, new Vector2(200, 200),this.world);
        p2.setExitPortal(p1);
        p1.setExitPortal(p2);
        p3.setExitPortal(p4);
        p4.setExitPortal(p3);
        this.portals.add(p1);
        this.portals.add(p2);
        this.portals.add(p3);
        this.portals.add(p4);
        createCollisionListener();

        this.monster = new Skeleton(this.world, GameWorld.WIDTH - 20, GameWorld.HEIGHT - 20, this.player);
    }

    /**
     * Demande au modèle Player de s'afficher sur le jeu
     * @param batch ensemble de sprite
     */
    public void draw(SpriteBatch batch) {
        this.player.draw(batch);
        this.monster.draw(batch);
        for(Portal p :portals){
            p.draw(batch);
        }
    }

    public World getWorld() {
        return this.world;
    }

    public Player getPlayer() {
        return this.player;
    }

    /***
     * Fonction pour mettre en place une gestion de collision
     ***/
    private void createCollisionListener() {

        world.setContactListener(new ContactListener() {

            @Override
            public void beginContact(Contact contact) {

                Object obj;
                // je regarde ici si l'hors d'un contact entre 2 bodys si l'un des deux est le personnage
                if(contact.getFixtureA().getBody() == getPlayer().getBody()){
                    obj = contact.getFixtureB().getBody().getUserData();
                }else{
                    obj = contact.getFixtureA().getBody().getUserData();
                }

                if(obj != null){
                    // Si l'objet en contact avec le personnage est un portail alors je teleporte le personnage
                    if(obj.getClass().getSimpleName().equals("Portal")) {
                        Portal por = ((Portal) obj);
                        isTp = true;
                        portal = por;
                    }

                }

            }

            @Override
            public void endContact(Contact contact) {


                Object obj;
                // je regarde ici si l'hors d'un contact entre 2 bodys si l'un des deux est le personnage
                if(contact.getFixtureA().getBody() == getPlayer().getBody()){
                    obj = contact.getFixtureB().getBody().getUserData();
                }else{
                    obj = contact.getFixtureA().getBody().getUserData();
                }

                if(obj != null){
                    // Si l'objet en contact avec le personnage est un portail alors je met le portail en actif
                    if(obj.getClass().getSimpleName().equals("Portal")) {
                        Portal por = ((Portal) obj);
                        por.setActif(true);

                    }

                }
            }

            @Override
            public void preSolve(Contact contact, Manifold oldManifold) {

            }
            @Override
            public void postSolve(Contact contact, ContactImpulse impulse) {
            }

        });
    }

    /***
     * Cette Fonction teleport le joueur au portail de sortie par rapport au portail selectionner
     * @param p Player
     * @param por Portal
     */
    public void teleport(Player p, Portal por){
        //Si le portail est actif je peux teleporter
        if(por.isActif()) {
            // Si le portail de sortie est dans le meme labyrinthe on teleporte le joueur
            if (por.exitSameMaze()) {
				/*
				A remplir ....
				*/
                // Temporaire
                // Je rend le portail de sortie inactif pour eviter de teleporter en boucle
                por.setExitPortalActif(false);
                // Je teleporte le joueur a la position du portail de sortie.
                p.getBody().setTransform(por.getPosPortalExit().x,por.getPosPortalExit().y,0f);
            } else {
                // Si le portail de sortie n'est pas situer dans le meme labirynthe on charge le nouveau labirynthe et on teleporte le joueur

            }
        }
    }
}
