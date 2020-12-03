package com.test.portals;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.zeldiablo.models.portals.Portal;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PortalTest {

    Portal p;

    @BeforeEach
    void ini() {
        p = new Portal(0, 0, new Vector2(0,0), new World(new Vector2(0,0), true));
    }


}