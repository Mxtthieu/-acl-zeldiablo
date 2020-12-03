package com.test.traps;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.math.Vector2;
import com.zeldiablo.models.GameState;
import com.zeldiablo.models.GameWorld;
import com.zeldiablo.models.Player;
import com.zeldiablo.models.monsters.Monster;
import com.zeldiablo.models.monsters.Skeleton;
import com.zeldiablo.models.traps.Projectile;
import com.zeldiablo.models.traps.Trap;
import com.zeldiablo.models.traps.TrapDamage;
import com.zeldiablo.views.GameScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ProjectileTest {
    Projectile p;
    GameWorld gm;

    @BeforeEach
    void ini(){
        gm = new GameWorld(new GameScreen(), new GameState());
        p = new Projectile(new TrapDamage(new Vector2(0,0), gm,0),
                new Vector2(0,0), 0, gm);
    }

    @Test
    void effectMonster(){
        Monster ske = new Skeleton(gm, 0, 0, new Player(gm, ""));
        p.effect(ske.getBody());
        assertFalse(ske.getHp()==90);
        assertTrue(ske.getHp()==100);
    }

    @Test
    void effectPlayer(){
        p.effect(gm.getPlayer().getBody());
        assertTrue(gm.getPlayer().getHP()==90);
        gm.getPlayer().setHp(9);
        p.effect(gm.getPlayer().getBody());
        assertFalse(gm.getPlayer().getHP()==-1);
        assertTrue(gm.getPlayer().getHP()==0);
    }
}