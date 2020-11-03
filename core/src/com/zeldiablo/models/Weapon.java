package com.zeldiablo.models;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.Body;

public class Weapon {
    private int damage;
    private int range;
    private int atkspeed;
    private int width;
    private int reach;
    private String name;
    private Body bodyWeapon;

    public Weapon(){
        this.damage = 1;
        this.range = 1;
        this.atkspeed = 1;
        this.width = 20;
        this.reach = 50;
    }

    public void draw(SpriteBatch sb){
        sb.begin();
        //TODO: Draw l'animation d'attaque selon l'arme en main
        sb.end();
    }

    public int getDamage() {
        return damage;
    }

    public int getRange() {
        return range;
    }

    public int getAtkspeed() {
        return atkspeed;
    }

    public int getWidth() {
        return width;
    }

    public String getName() {
        return name;
    }

    public Body getBodyWeapon() {
        return bodyWeapon;
    }

    public int getReach() {
        return reach;
    }
}
