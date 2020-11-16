package com.zeldiablo.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public enum TextureFactory {
    INSTANCE;

    private Texture pause;
    private Texture grass;

    private static final float FRAMERATE = 1/4f;
    private TextureAtlas animeTree;
    private TextureAtlas player_right;
    private TextureAtlas player_left;
    private TextureAtlas player_up;
    private TextureAtlas player_down;

    TextureFactory() {
        this.pause = new Texture(Gdx.files.internal("images/Pause.png"));
        this.grass = new Texture("images/grass.jpg");

        this.animeTree = new TextureAtlas("images/tree/pack.atlas");
        this.player_right = new TextureAtlas("images/player/walk_right.atlas");
        this.player_left = new TextureAtlas("images/player/walk_left.atlas");
        this.player_up = new TextureAtlas("images/player/walk_up.atlas");
        this.player_down = new TextureAtlas("images/player/walk_down.atlas");
    }

    public Texture getPause() { return pause; }

    public Texture getGrass() {
        return this.grass;
    }

    public Animation getAnimatedTree() {
        Array<Sprite> img = this.animeTree.createSprites();
        return new Animation(FRAMERATE, img, Animation.PlayMode.LOOP);
    }

    public Animation getAnimatedPlayerRight() {
        Array<Sprite> img = this.player_right.createSprites();
        return new Animation(FRAMERATE, img, Animation.PlayMode.LOOP);
    }

    public Animation getAnimatedPlayerLeft() {
        Array<Sprite> img = this.player_left.createSprites();
        return new Animation(FRAMERATE, img, Animation.PlayMode.LOOP);
    }

    public Animation getAnimatedPlayerUp() {
        Array<Sprite> img = this.player_up.createSprites();
        return new Animation(FRAMERATE, img, Animation.PlayMode.LOOP);
    }

    public Animation getAnimatedPlayerDown() {
        Array<Sprite> img = this.player_down.createSprites();
        return new Animation(FRAMERATE, img, Animation.PlayMode.LOOP);
    }
}
