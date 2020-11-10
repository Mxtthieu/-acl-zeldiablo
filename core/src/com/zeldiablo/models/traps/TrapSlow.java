package com.zeldiablo.models.traps;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.zeldiablo.models.Player;

public class TrapSlow extends Trap {

    private int ralentissement;

    public TrapSlow(Vector2 pos, World world) {
        super(pos, world);
        this.ralentissement = 5;
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

    /**
     * @author Vasaune Christian
     *
     */
    @Override
    public void effect(Player p) {
        System.out.println("Joueur : " + p.getName() + "est ralentie de : " + getRalentissement());
    }

    /**
     * @author Vasaune Christian
     *
     * @return int valeure de ralentissement
     */
    public int getRalentissement() {
        return ralentissement;
    }

    /**
     * @author Vasaune Christian
     *
     * @param ralentissement int valeur du ralentisselent
     */
    public void setRalentissement(int ralentissement) {
        this.ralentissement = ralentissement;
    }
}
