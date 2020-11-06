package com.zeldiablo.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

public interface Weapon {
    public void draw(SpriteBatch sb);

    public int getDamage();

    public int getRange();

    public float getAtkspeed();

    public int getWidth();

    public String getName();

    public Body getBodyWeapon();

    public int getReach();

    public void attack(float x, float y, float angle, World world);


}
