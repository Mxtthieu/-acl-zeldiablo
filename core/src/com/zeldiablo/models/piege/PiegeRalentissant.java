package com.zeldiablo.models.piege;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class PiegeRalentissant extends Piege{

    private int ralentissement;

    public PiegeRalentissant(Vector2 pos, World world) {
        super(pos, 40, 30, world);
        this.ralentissement = 5;
    }

    /**
     * @autor Vasaune Christian
     * Permet de déssiner le joueur sur l'ensemble de srpites présent sur l'écran
     * @param batch SpriteBatch qui regroupe tous les sprite déssiné à l'écran
     */
    public void draw(SpriteBatch batch){
        batch.begin();
        //batch.draw(); Ajout Sprite
        batch.end();
    }

    /**
     * @autor Vasaune Christian
     *
     */
    @Override
    public void effect() {
        super.effect();
    }

    /**
     * @autor Vasaune Christian
     *
     * @return int valeure de ralentissement
     */
    public int getRalentissement() {
        return ralentissement;
    }

    /**
     * @autor Vasaune Christian
     *
     * @param ralentissement int valeur du ralentisselent
     */
    public void setRalentissement(int ralentissement) {
        this.ralentissement = ralentissement;
    }
}
