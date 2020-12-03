package com.zeldiablo.models.weapons.weaponsCAC;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.zeldiablo.models.GameWorld;
import com.zeldiablo.models.monsters.Monster;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Sword extends Cac {

    public Sword(){
        super("Sword", 1, 2, 4, 10);
    }

    @Override
    public void setPositionBody(BodyDef bd, float x, float y, float angle, float radius) {
        bd.position.set(x + (float) cos(angle) * ((radius*2) + 1), y + (float) sin(angle) * ((radius*2) + 1));
        System.out.println(angle);
    }

    @Override
    public void draw(SpriteBatch sb) {
        sb.begin();
        // TODO : Ajouter texture Sword
        sb.end();
    }

    @Override
    public void effect(Body b) {
        Monster monster = (Monster)b.getUserData();
        monster.decreaseHP(this.damage);
        System.out.println(monster.getHp());
    }


}
