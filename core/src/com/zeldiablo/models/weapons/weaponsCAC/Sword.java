package com.zeldiablo.models.weapons.weaponsCAC;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Timer;
import com.zeldiablo.factories.TextureFactory;
import com.zeldiablo.models.GameWorld;
import com.zeldiablo.models.monsters.Monster;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Sword extends Cac {

    public Sword(GameWorld gameWorld){
        super("Sword", 1, 2, 4, 10, gameWorld);
        this.animation = TextureFactory.INSTANCE.getAnimatedSlashAttack();
    }

    @Override
    public void setPositionBody(BodyDef bd, float x, float y, float angle, float radius) {
        this.angle = (float) Math.toDegrees((double) angle);
        bd.position.set(x + (float) cos(angle) * ((radius*2) + 1), y + (float) sin(angle) * ((radius*2) + 1));
    }

    @Override
    public void draw(SpriteBatch sb) {
        if (this.attacking) {
            this.tmpAnim += Gdx.graphics.getDeltaTime();
            TextureRegion region = (TextureRegion) this.animation.getKeyFrame(this.tmpAnim);
            float x = hitbox.getPosition().x;
            float y = hitbox.getPosition().y;
            sb.begin();
            sb.draw(region, x-reach/2, y-width/2, 0, 0, reach, width, 1, 1, this.angle);
            sb.end();
        } else {
            this.tmpAnim = Gdx.graphics.getDeltaTime();
        }
    }

    @Override
    public void effect(Body b) {
        Monster monster = (Monster)b.getUserData();
        monster.decreaseHP(this.damage);
        this.gameWorld.getGameStats().increaseScore();
    }

}
