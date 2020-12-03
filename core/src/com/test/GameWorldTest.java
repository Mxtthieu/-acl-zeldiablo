package com.test;


import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.zeldiablo.models.GameState;
import com.zeldiablo.models.GameWorld;
import com.zeldiablo.models.portals.Portal;
import com.zeldiablo.views.GameScreen;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameWorldTest {

    World world;
    public GameWorldTest(){
         world = new World(new Vector2(0,0), true);
    }

    @Test
    void teleportTest() {
        Portal p = new Portal(0, 0, new Vector2(0,0), 0, this.world);
        Portal p2 = new Portal(0, 0, new Vector2(30,30), 0, this.world);
        p.setExitPortal(p2);
        GameWorld gm = new GameWorld(new GameScreen(), new GameState());
        assertTrue(gm.getPlayer().getPosition()==new Vector2(0,0));
        gm.teleport(gm.getPlayer(), p);
        assertTrue(gm.getPlayer().getPosition()==new Vector2(30,30));
    }
}