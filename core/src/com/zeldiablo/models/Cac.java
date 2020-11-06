package com.zeldiablo.models;

import com.badlogic.gdx.physics.box2d.World;

public abstract class Cac implements Weapon{
    private float atkspeed;
    private int width;
    private int reach;
    private int damage;

    public Cac(){
    }

    public Cac(float as, int w, int r, int d){
        this.atkspeed = as;
        this.width = w;
        this.reach = r;
        this.damage = d;
    }

    public float getAtkspeed() {
        return atkspeed;
    }

    public int getWidth() {
        return width;
    }

    public int getReach() {
        return reach;
    }

    public int getDamage() {
        return damage;
    }

    public void attack(float x, float y, float angle, World world){}
}
