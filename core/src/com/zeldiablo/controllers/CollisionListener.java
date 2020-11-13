package com.zeldiablo.controllers;

import com.badlogic.gdx.physics.box2d.*;
import com.zeldiablo.models.GameWorld;
import com.zeldiablo.models.Player;
import com.zeldiablo.models.portals.Portal;
import com.zeldiablo.models.traps.Projectile;
import com.zeldiablo.models.traps.Trap;

public class CollisionListener implements ContactListener {

    private boolean isTp;
    private Portal portal;
    private Player player;
    private GameWorld gameWorld;

    public CollisionListener(GameWorld gameWorld){
        this.isTp = false;
        this.portal = null;
        this.player = gameWorld.getPlayer();
        this.gameWorld = gameWorld;

    }
    @Override
    public void beginContact(Contact contact) {

        Body a = contact.getFixtureA().getBody();
        Body b = contact.getFixtureB().getBody();

        checkProjectileCollision(a,b);

        Object obj = null;
        // je regarde ici si l'hors d'un contact entre 2 bodys si l'un des deux est le personnage
        if(contact.getFixtureA().getBody() == player.getBody()){
            obj = contact.getFixtureB().getBody().getUserData();
        }else if(contact.getFixtureB().getBody() == player.getBody()){
            obj = contact.getFixtureA().getBody().getUserData();
        }

        if(obj != null) {
            // Si l'objet en contact avec le personnage est un portail alors je teleporte le personnage
            if (obj.getClass().getSimpleName().equals("Portal")) {
                System.out.println(contact.getFixtureB().getBody().getUserData());
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

    private void checkProjectileCollision(Body a, Body b){
        if(a.getUserData() instanceof Projectile && !(b.getUserData() instanceof Trap)){
            this.gameWorld.addBodyToDelete(a);
            Projectile pro = (Projectile)a.getUserData();
            pro.effect(b);
        } else if (b.getUserData() instanceof Projectile && !(a.getUserData() instanceof Trap)){
            this.gameWorld.addBodyToDelete(b);
            Projectile pro = (Projectile)b.getUserData();
            pro.effect(a);
        }
    }
}

