package com.test.monsters;

import com.badlogic.gdx.Game;
import com.zeldiablo.models.GameState;
import com.zeldiablo.models.GameWorld;
import com.zeldiablo.models.Player;
import com.zeldiablo.models.monsters.Skeleton;
import com.zeldiablo.views.GameScreen;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SkeletonTest extends Game {
    GameWorld gm;
    Skeleton ske;
    GameScreen gs;

    @BeforeEach
    void ini(){
        gm = new GameWorld(gs, new GameState());
        ske = new Skeleton(gm, 0, 0, new Player(gm, ""));
    }

    @Test
    void decreaseHPTest() {
        ske.decreaseHP(5);
        assertTrue(ske.getHP() == 95);
        ske.setHp(2);
        ske.decreaseHP(5);
        assertTrue(ske.getHP() == 0);
        assertFalse(ske.getHP() == -3);
    }

    @Test
    void increaseHPTest() {
        ske.increaseHP(5);
        assertTrue(ske.getHp() == 105);
    }

    @Override
    public void create() {
        gs = new GameScreen();
    }
}