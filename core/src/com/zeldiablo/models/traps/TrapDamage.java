package com.zeldiablo.models.traps;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.zeldiablo.models.Player;

import java.util.ArrayList;

public class TrapDamage extends Trap {

    private int att;
    protected Timer timer;
    protected Timer.Task shotTask;
    private ArrayList<Projectile> projectiles;
    private ArrayList<Projectile> projectilsSup;

    public TrapDamage(final Vector2 pos, final World world) {

        super(pos, world);
        this.att = 10;
        projectiles = new ArrayList<>();
        projectilsSup = new ArrayList<>();
        this.timer = new Timer();
        this.shotTask = new Timer.Task() {
            @Override
            public void run() {
                Projectile p = new Projectile(new Vector2(pos.x + 1.5f,pos.y + 0.5f), world);
                p.setDirection();
                projectiles.add(p);

            }
        };
        timer.scheduleTask(shotTask, 0.5f, 1.5f);

        for (Projectile p : projectiles){
            if(p.isTouch()){
                projectilsSup.add(p);
                world.destroyBody(p.getBodyProjectil());
            }
        }
        projectiles.removeAll(projectilsSup);

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

    public ArrayList<Projectile> getProjectils() {
        return projectiles;
    }
}
