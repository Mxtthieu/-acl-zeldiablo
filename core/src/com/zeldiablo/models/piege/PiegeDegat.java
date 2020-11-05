package com.zeldiablo.models.piege;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.zeldiablo.models.Player;

public class PiegeDegat extends Piege{

    private int att;

    public PiegeDegat(Vector2 pos, World world) {
        super(pos, world);
        this.att = 10;
    }

    /**
     * @author Vasaune Christian
     * Permet de déssiner le joueur sur l'ensemble de srpites présent sur l'écran
     * @param batch SpriteBatch qui regroupe tous les sprite déssiné à l'écran
     */
    public void draw(SpriteBatch batch){
        batch.begin();
        //batch.draw(); Ajout Sprite
        batch.end();
    }

    public int getAtt() {
        return att;
    }

    @Override
    public void effect(Player p) {
        System.out.println("Joueur : " + p.getName() + " à subit : " + getAtt());
    }
}
