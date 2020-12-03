package com.test.traps;

import com.badlogic.gdx.math.Vector2;
import com.zeldiablo.models.GameState;
import com.zeldiablo.models.GameWorld;
import com.zeldiablo.models.traps.Projectile;
import com.zeldiablo.models.traps.TrapDamage;
import com.zeldiablo.views.GameScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TrapDamageTest {
    TrapDamage t;
    GameWorld gm;

    @BeforeEach
    void ini(){
        gm = new GameWorld(new GameScreen(), new GameState());
        t = new TrapDamage(new Vector2(0,0), gm,0);
    }

    @Test
    void applyEffectToPlayerTest(){
        t.applyEffectToPlayer();
        assertTrue(gm.getPlayer().getHP() == 90);
        gm.getPlayer().setHp(9);
        t.applyEffectToPlayer();
        assertTrue(gm.getPlayer().getHP() == 0);
        assertFalse(gm.getPlayer().getHP() == -1);
    }
}