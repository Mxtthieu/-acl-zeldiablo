package com.zeldiablo.controllers;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.zeldiablo.models.Player;
import com.zeldiablo.models.Portal;
import com.zeldiablo.models.piege.Piege;
import com.zeldiablo.models.piege.PiegeDegat;

public class CollisionListener implements ContactListener {

    private boolean isTp;
    private Portal portal;
    private Player player;

    public CollisionListener(Player p){
        this.isTp = false;
        this.portal = null;
        this.player = p;

    }
    @Override
    public void beginContact(Contact contact) {

        Object obj;
        // je regarde ici si l'hors d'un contact entre 2 bodys si l'un des deux est le personnage
        if(contact.getFixtureA().getBody() == player.getBody()){
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

            // Si l'objet en contact avec le personnage est un piege
            if(obj.getClass().getSimpleName().equals("PiegeDegat")) {
                PiegeDegat piege = ((PiegeDegat) obj);
                System.out.println("tu vien de perdre : " + piege.getAtt());

            }

        }

    }

    @Override
    public void endContact(Contact contact) {


        Object obj;
        // je regarde ici si l'hors d'un contact entre 2 bodys si l'un des deux est le personnage
        if(contact.getFixtureA().getBody() == player.getBody()){
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

    public boolean isTp() {
        return isTp;
    }

    public Portal getPortal() {
        return portal;
    }

    public void setTp(boolean tp) {
        isTp = tp;
    }
}

