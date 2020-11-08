package com.zeldiablo.models.piege;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.zeldiablo.models.Player;

public class PiegeMonster extends Piege {

    public PiegeMonster(Vector2 pos, World world) {
        super(pos, world);
    }

    @Override
    public void effect(Player p) {

    }
}
