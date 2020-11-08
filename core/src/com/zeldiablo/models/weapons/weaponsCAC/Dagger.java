package com.zeldiablo.models.weapons.weaponsCAC;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Dagger extends Cac {

    public Dagger(){
        super("Dagger", 1, 16, 40, 1);
    }

    @Override
    public void setPositionBody(BodyDef bd, float x, float y, float angle, float radius) {
        bd.position.set(x + (float) cos(angle) * (radius * 2), y + (float) sin(angle) * (radius * 2));
    }

    @Override
    public void draw(SpriteBatch sb) {
        sb.begin();
        // TODO : Ajouter texture Dagger
        sb.end();
    }

}
