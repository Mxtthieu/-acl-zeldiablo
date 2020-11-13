package com.zeldiablo.models.traps;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Timer;
import com.zeldiablo.models.GameWorld;
import com.zeldiablo.models.Player;

import java.util.ArrayList;

public class TrapDamage extends Trap {

    private int att;
    protected Timer timer;
    protected Timer.Task shotTask;

    public TrapDamage(final Vector2 pos, GameWorld gameworld) {
        super(pos, gameworld);
        this.att = 10;
        this.timer = new Timer();
        this.shotTask = new Timer.Task() {
            @Override
            public void run() {
                addProjectile();
            }
        };
        timer.scheduleTask(shotTask, 0.5f, 1.5f);
    }

    private void addProjectile() {
        Projectile p = new Projectile(this, new Vector2(this.pos.x + 1.5f,pos.y + 0.5f), this.gameWorld);
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

    @Override
    public void applyEffectToPlayer() {
        Player p = this.gameWorld.getPlayer();
        p.setHp(p.getHP() - 10);
    }

    @Override
    public void clearTimer() {
        timer.clear();
    }


    public int getAtt() {
        return att;
    }

}
