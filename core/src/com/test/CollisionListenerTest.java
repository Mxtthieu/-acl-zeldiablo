package com.test;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.zeldiablo.controllers.CollisionListener;
import com.zeldiablo.models.GameState;
import com.zeldiablo.models.GameWorld;
import com.zeldiablo.models.Player;
import com.zeldiablo.models.monsters.Monster;
import com.zeldiablo.models.monsters.Skeleton;
import com.zeldiablo.models.portals.Portal;
import com.zeldiablo.models.traps.Projectile;
import com.zeldiablo.models.traps.Trap;
import com.zeldiablo.models.traps.TrapDamage;
import com.zeldiablo.models.weapons.weaponsCAC.Cac;
import com.zeldiablo.models.weapons.weaponsCAC.Sword;
import com.zeldiablo.views.GameScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CollisionListenerTest {
    CollisionListener cl;
    GameWorld gm;
    Projectile p;
    Cac s;
    Monster m;
    Trap t;
    Portal p1;
    Portal p2;

    @BeforeEach
    void ini(){
        gm = new GameWorld(new GameScreen(), new GameState());
        cl = new CollisionListener(gm);
        t = new TrapDamage(new Vector2(0,0), gm,0);
        p = new Projectile(t, new Vector2(0,0), 0, gm);
        m = new Skeleton(gm, 0, 0, new Player(gm, ""));
        s = new Sword(gm);
        p1 = new Portal(0, 0, new Vector2(0,0), 0,  new World(new Vector2(0,0), true));
        p2 = new Portal(0, 0, new Vector2(30,30), 0,  new World(new Vector2(0,0), true));
        p1.setExitPortal(p2);
    }
    /*
    @Test
    void checkProjectileCollisionTest(){
        // Projectile - Player
        gm.getPlayer().setHp(100);
        checkAttackCollision(p.getBodyProjectil(),gm.getPlayer().getBody());
        assertTrue(gm.getPlayer().getHP() == 90);
        gm.getPlayer().setHp(1);
        checkAttackCollision(p.getBodyProjectil(),gm.getPlayer().getBody());
        assertTrue(gm.getPlayer().getHP() == 0);
        assertFalse(gm.getPlayer().getHP() == -9);
        // Player - Projectile
        gm.getPlayer().setHp(100);
        checkAttackCollision(gm.getPlayer().getBody(),p.getBodyProjectil());
        assertTrue(gm.getPlayer().getHP() == 90);
        gm.getPlayer().setHp(1);
        checkAttackCollision(gm.getPlayer().getBody(),p.getBodyProjectil());
        assertTrue(gm.getPlayer().getHP() == 0);
        assertFalse(gm.getPlayer().getHP() == -9);
        // Projectile - Trap
        gm.getPlayer().setHp(100);
        checkAttackCollision(p.getBodyProjectil(), t.getBody());
        assertTrue(gm.getPlayer().getHP() == 100);
        // Trap - Projectile
        gm.getPlayer().setHp(100);
        checkAttackCollision(t.getBody(), p.getBodyProjectil());
        assertTrue(gm.getPlayer().getHP() == 100);
        // Projectile - Monster
        gm.getPlayer().setHp(100);
        m.setHp(100);
        checkAttackCollision(m.getBody(),p.getBodyProjectil());
        assertTrue(gm.getPlayer().getHP() == 100);
        assertTrue(m.getHP() == 100);
        // Monster - Projectile
        gm.getPlayer().setHp(100);
        m.setHp(100);
        checkAttackCollision(p.getBodyProjectil(), m.getBody());
        assertTrue(gm.getPlayer().getHP() == 100);
        assertTrue(m.getHP() == 100);
    }

    @Test
    void checkPortalCollisionTest(){
        // Portal - Player
        gm.getPlayer().setPosition(0,0);
        checkAttackCollision(p1.getBodyPortal(),gm.getPlayer().getBody());
        assertTrue(gm.getPlayer().getPosition() == new Vector2(30,30));
        // Player - Portal
        gm.getPlayer().setPosition(0,0);
        checkAttackCollision(gm.getPlayer().getBody(),p1.getBodyPortal());
        assertTrue(gm.getPlayer().getPosition() == new Vector2(30,30));
        // Projectile - Portal
        checkAttackCollision(p.getBodyProjectil(), p1.getBodyPortal());
        assertTrue(gm.getPlayer().getPosition() == new Vector2(0,0));
        assertFalse(gm.getPlayer().getPosition() == new Vector2(30,30));
        // Portal - Projectile
        checkAttackCollision(p1.getBodyPortal(),p.getBodyProjectil());
        assertTrue(gm.getPlayer().getPosition() == new Vector2(0,0));
        assertFalse(gm.getPlayer().getPosition() == new Vector2(30,30));
        // Portal - Monster
        Vector2 pos_ini = m.getPosition();
        checkAttackCollision(m.getBody(),p1.getBodyPortal());
        assertTrue(gm.getPlayer().getPosition() == new Vector2(0,0));
        assertFalse(gm.getPlayer().getPosition() == new Vector2(30,30));
        assertTrue(m.getPosition() == pos_ini);
        assertFalse(m.getPosition() == new Vector2(30,30));
        // Monster - Portal
        checkAttackCollision(p1.getBodyPortal(),m.getBody());
        assertTrue(gm.getPlayer().getPosition() == new Vector2(0,0));
        assertFalse(gm.getPlayer().getPosition() == new Vector2(30,30));
        assertTrue(m.getPosition() == pos_ini);
        assertFalse(m.getPosition() == new Vector2(30,30));
    }

    @Test
    private void checkAttackCollisionTest() {
        // Cac - Monster
        m.setHp(100);
        checkAttackCollision(s.getHitbox(), m.getBody());
        assertTrue(m.getHp()==90);
        m.setHp(1);
        checkAttackCollision(s.getHitbox(), m.getBody());
        assertTrue(m.getHp()==0);
        assertFalse(m.getHp()==-1);
        // Monster - Cac
        m.setHp(100);
        checkAttackCollision(m.getBody(), s.getHitbox());
        assertTrue(m.getHp()==90);
        m.setHp(1);
        checkAttackCollision(m.getBody(), s.getHitbox());
        assertTrue(m.getHp()==0);
        assertFalse(m.getHp()==-1);
        // Cac - Portal
        m.setHp(100);
        checkAttackCollision(s.getHitbox(), p1.getBodyPortal());
        assertTrue(m.getHp()==100);
        // Portal - Cac
        checkAttackCollision(p1.getBodyPortal(), s.getHitbox());
        assertTrue(m.getHp()==100);
        // Cac - Projectile
        checkAttackCollision(s.getHitbox(), p.getBodyProjectil());
        assertTrue(m.getHp()==100);
        // Projectile - Cac
        checkAttackCollision(p.getBodyProjectil(), s.getHitbox());
        assertTrue(m.getHp()==100);
        // Cac - Rien
        Body b;
        BodyDef bd = new BodyDef();
        World w = new World(new Vector2(0,0), true);
        b = w.createBody(bd);
        checkAttackCollision(s.getHitbox(), b);
        assertTrue(m.getHp()==100);
        // Rien - Cac
        checkAttackCollision(b, s.getHitbox());
        assertTrue(m.getHp()==100);
    }*/
}