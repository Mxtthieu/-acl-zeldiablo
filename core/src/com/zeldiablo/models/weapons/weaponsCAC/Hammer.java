package com.zeldiablo.models.weapons.weaponsCAC;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Hammer extends Cac {

    public Hammer(){
        super("Hammer", 1, 38, 45, 1);
    }

    @Override
    public void draw(SpriteBatch sb) {
        sb.begin();
        // TODO : Ajouter texture Hammer
        sb.end();
    }

}
