package com.zeldiablo.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

/**
 * @author Vasaune Christian
 */
public class Portal {

    private Vector2 posPortal;
    private Body bodyPortal;
    private int numMaze;
    private Portal exitPortal;
    private static float taille;
    private boolean actif;

    public Portal(int num, Vector2 pos, World world){

        this.numMaze = num;
        this.actif = true;
        this.posPortal = pos;

        taille = (1/40f)* GameWorld.WIDTH;
        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.StaticBody;
        bodydef.position.set(posPortal);
        bodyPortal = world.createBody(bodydef);

        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(taille);
        fixtureDef.shape = shape;
        //fixtureDef.isSensor = true;
        bodyPortal.setUserData(this);
        bodyPortal.createFixture(fixtureDef);
        shape.dispose();

    }

    /**
     * Permet mettre le portail en actif  true = on peut l'utiliser false = on peut pas
     * @param actif Booelan
     */
    public void setActif(boolean actif) {
        this.actif = actif;
    }

    /**
     * Permet de mettre le portail de sortie en actif  true = on peut l'utiliser false = on peut pas
     * @param actif Booelan
     */
    public void setExitPortalActif(boolean actif){
        this.exitPortal.setActif(actif);
    }

    /**
     * Permet d'initialisé un portail de sortie
     * "Important sinon ca ne marchera pas"
     * @param exitPortal Portal
     */
    public void setExitPortal(Portal exitPortal) {
        this.exitPortal = exitPortal;
    }

    /***
     * Methode permettant de retourner le numero au quel le labirynthe appartient
     * @return int le numero de labirynthe
     ***/
    public int getNumMaze() {
        return numMaze;
    }

    /***
     * Methode permettant de retourner la position du portail
     * @return Vector2 position du portail
     ***/
    public Vector2 getPosPortal() {
        return posPortal;
    }

    /***
     * Methode permettant de retourner la position du portail de sortie
     * @return Vector2 position du portail de sortie
     ***/
    public Vector2 getPosPortalExit() {
        return exitPortal.getPosPortal();
    }

    /***
     * Methode permettant de retourner la position du portail de sortie
     * @return Vector2 position du portail de sortie
     ***/
    public int getExitPortalNumMaze(){
        return this.exitPortal.getNumMaze();

    }

    /***
     * Methode permettant de savoir si les portails se trouve dans le meme monde ou pas
     * @return boolean true si vrai false sinon
     ***/
    public boolean exitSameMaze(){
        return getNumMaze() == getExitPortalNumMaze();
    }

    /***
     * Methode permettant de savoir si le portail est actif
     * @return boolean true si vrai false sinon
     ***/
    public boolean isActif() {
        return actif;
    }

    /**
     * Permet de déssiner le joueur sur l'ensemble de srpites présent sur l'écran
     * @param batch SpriteBatch qui regroupe tous les sprite déssiné à l'écran
     */
    public void draw(SpriteBatch batch){
        batch.begin();
        //batch.draw(); Ajout Sprite
        batch.end();
    }

    /**
     * @return le body du portail
     */
    public Body getBodyPortal() {
        return bodyPortal;
    }


}
