package com.test.weapons.weaponsCAC;

import com.zeldiablo.models.GameState;
import com.zeldiablo.models.GameWorld;
import com.zeldiablo.models.Player;
import com.zeldiablo.models.monsters.Monster;
import com.zeldiablo.models.monsters.Skeleton;
import com.zeldiablo.models.weapons.weaponsCAC.Cac;
import com.zeldiablo.models.weapons.weaponsCAC.Sword;
import com.zeldiablo.views.GameScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SwordTest {
    Monster m;
    Cac s;
    GameWorld gm;

    @BeforeEach
    void ini(){
        gm = new GameWorld(new GameScreen(), new GameState());
        s = new Sword(gm);
        m = new Skeleton(gm, 0, 0, new Player(gm, ""));
    }

    @Test
    void effectTest(){
        assertTrue(m.getHp()==100);
        s.effect(m.getBody());
        assertTrue(m.getHp()==90);
        m.setHp(9);
        s.effect(m.getBody());
        assertTrue(m.getHp()==0);
        assertFalse(m.getHp()==-1);
    }
}