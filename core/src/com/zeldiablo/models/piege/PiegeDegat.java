package com.zeldiablo.models.piege;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.zeldiablo.models.Player;
import com.zeldiablo.models.Projectil;

import java.util.ArrayList;

public class PiegeDegat extends Piege{

    private int att;
    protected Timer timer;
    protected Timer.Task shotTask;
    private ArrayList<Projectil>projectils;
    private ArrayList<Projectil>projectilsSup;

    public PiegeDegat(final Vector2 pos, final World world) {

        super(pos, world);
        this.att = 10;
        projectils = new ArrayList<>();
        projectilsSup = new ArrayList<>();
        this.timer = new Timer();
        this.shotTask = new Timer.Task() {
            @Override
            public void run() {
                Projectil p = new Projectil(new Vector2(pos.x + 1.5f,pos.y + 0.5f), world);
                p.setDirection();
                projectils.add(p);

            }
        };
        timer.scheduleTask(shotTask, 0.5f, 1.5f);

        for (Projectil p : projectils){
            if(p.isTouch()){
                projectilsSup.add(p);
                world.destroyBody(p.getBodyProjectil());
            }
        }
        projectils.removeAll(projectilsSup);

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

    }

    public ArrayList<Projectil> getProjectils() {
        return projectils;
    }
}
