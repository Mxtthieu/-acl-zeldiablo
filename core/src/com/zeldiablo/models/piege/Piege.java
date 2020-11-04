package com.zeldiablo.models.piege;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

public abstract class Piege {

    private boolean touch;
    private Vector2 pos;
    private Body bodyPiege;
    private float height;
    private float width;

    public Piege(Vector2 pos, float height, float width, World world){

        this.touch = true;
        this.pos = pos;
        this.height = height;
        this.width = width;

        BodyDef bodydef = new BodyDef();
        bodydef.type = BodyDef.BodyType.StaticBody;
        bodydef.position.set(this.pos);
        bodyPiege = world.createBody(bodydef);

        float[] vertice = {0f, 0f, 0f,this.height, this.width, this.height, this.width, 0f, 0f, 0f};
        FixtureDef fixtureDef = new FixtureDef();
        ChainShape shape = new ChainShape();
        shape.createChain(vertice);
        fixtureDef.shape = shape;
        //fixtureDef.isSensor = true;
        bodyPiege.setUserData(this);
        bodyPiege.createFixture(fixtureDef);
        shape.dispose();
    }

    public void effect() {

    }

    /**
     * Permet de déssiner le joueur sur l'ensemble de srpites présent sur l'écran
     * @param batch SpriteBatch qui regroupe tous les sprite déssiné à l'écran
     */
    public void draw(SpriteBatch batch){
        batch.begin();
        //batch.draw(); Ajout Sprite
        batch.end();
    }


}
