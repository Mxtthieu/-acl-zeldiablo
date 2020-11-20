package com.zeldiablo.controllers;

import com.badlogic.gdx.physics.box2d.*;
import com.zeldiablo.models.GameWorld;
import com.zeldiablo.models.Player;
import com.zeldiablo.models.monsters.Monster;
import com.zeldiablo.models.portals.Portal;
import com.zeldiablo.models.traps.Projectile;
import com.zeldiablo.models.traps.Trap;
import com.zeldiablo.models.weapons.weaponsCAC.Cac;
import com.zeldiablo.models.weapons.weaponsCAC.Sword;

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
        checkPortalCollision(a,b);
        checkAttackCollision(a,b);

    }

    @Override
    public void endContact(Contact contact) {

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

    private void checkPortalCollision(Body a, Body b){
        if(a.getUserData() instanceof Portal && b.getUserData() instanceof Player){
            Portal por = (Portal)a.getUserData();
            isTp = true;
            portal = por;
        } else if (b.getUserData() instanceof Portal && a.getUserData() instanceof Player){
            Portal por = (Portal)b.getUserData();
            isTp = true;
            portal = por;
        }
    }

    private void checkAttackCollision(Body a, Body b) {
        if(a.getUserData() instanceof Cac && b.getUserData() instanceof Monster){
            Cac weapon = player.getWeapon();
            weapon.effect(b);
        } else if (b.getUserData() instanceof Cac && a.getUserData() instanceof Monster){
            Cac weapon = player.getWeapon();
            weapon.effect(a);
        }
    }
}

