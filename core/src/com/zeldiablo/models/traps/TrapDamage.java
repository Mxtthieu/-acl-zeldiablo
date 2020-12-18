package com.zeldiablo.models.traps;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import com.zeldiablo.factories.TextureFactory;
import com.zeldiablo.models.GameWorld;
import com.zeldiablo.models.Player;

import java.util.ArrayList;
import java.util.List;

public class TrapDamage extends Trap {

    private int att;
    private float angle;
    protected Timer timer;
    protected Timer.Task shotTask;
    private List<Projectile> projectileList;

    public TrapDamage(final Vector2 pos, GameWorld gameworld, float angle) {
        super(pos, angle, gameworld);
        this.att = 10;
        this.angle = angle;
        this.timer = new Timer();
        this.projectileList = new ArrayList<>();
        this.shotTask = new Timer.Task() {
            @Override
            public void run() {
                addProjectile();
            }
        };
        timer.scheduleTask(shotTask, 0f, 2f);
    }

    private void addProjectile() {
        Projectile p = new Projectile(this, new Vector2(this.pos.x + (float)Math.cos(angle)*2 ,pos.y + (float)Math.sin(angle)*2f + 0.5f), angle, this.gameWorld);
        this.projectileList.add(p);
    }

    /**
     * @author Vasaune Christian
     * Permet de déssiner le joueur sur l'ensemble de srpites présent sur l'écran
     * @param batch SpriteBatch qui regroupe tous les sprite déssiné à l'écran
     */
    public void draw(SpriteBatch batch){
        Texture tex = TextureFactory.INSTANCE.getCannon();
        float x = this.bodyPiege.getPosition().x;
        float y = this.bodyPiege.getPosition().y;
        float size = getWidth();
        Sprite sprite = new Sprite(tex);
        sprite.rotate((float)Math.toDegrees(angle));
        sprite.setOrigin(size/2, size/2);
        sprite.setPosition(x-size/2, y-size/2);
        sprite.setSize(size*2, size*2);

        batch.begin();
        sprite.draw(batch);
        batch.end();

        for (Projectile p : this.projectileList)
            p.draw(batch);
    }

    @Override
    public void applyEffectToPlayer() {
        Player p = this.gameWorld.getPlayer();
        p.decreaseHP(10);
    }

    @Override
    public void clearTimer() {
        timer.clear();
    }

    @Override
    public void pause() {
        timer.stop();
    }

    @Override
    public void play() {
        timer.start();
    }

    @Override
    public void removeProjectile(Projectile p) {
        if (p != null) {
            if (this.projectileList.contains(p)) {
                this.projectileList.remove(p);
            }
        }
    }
}
