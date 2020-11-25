package com.zeldiablo.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public enum TextureFactory {
    INSTANCE;

    private Texture pause;
    private Texture grass;

    private static final float FRAMERATE = 1/4f;

    // Decor
    private TextureAtlas animeTree;
    private TextureAtlas animGreenPortal;
    private TextureAtlas animPurplePortal;

    // Player
    private TextureAtlas player_right;
    private TextureAtlas player_left;
    private TextureAtlas player_up;
    private TextureAtlas player_down;

    // Skeleton
    private TextureAtlas skeleton_right;
    private TextureAtlas skeleton_left;
    private TextureAtlas skeleton_up;
    private TextureAtlas skeleton_down;

    // Trap
    private Texture cannon;
    private Texture cannonball;

    // Attack
    private TextureAtlas slash_attack;

    TextureFactory() {
        this.pause = new Texture(Gdx.files.internal("images/Pause.png"));
        this.grass = new Texture("images/grass.jpg");

        // Decor
        this.animeTree = new TextureAtlas("images/tree/pack.atlas");
        this.animGreenPortal = new TextureAtlas("images/portal/green_portal.atlas");
        this.animPurplePortal = new TextureAtlas("images/portal/purple_portal.atlas");

        // Texture Player
        this.player_right = new TextureAtlas("images/player/walk_right.atlas");
        this.player_left = new TextureAtlas("images/player/walk_left.atlas");
        this.player_up = new TextureAtlas("images/player/walk_up.atlas");
        this.player_down = new TextureAtlas("images/player/walk_down.atlas");

        // Texture Skeleton
        this.skeleton_right = new TextureAtlas("images/monster/skeleton/walk_right.atlas");
        this.skeleton_left = new TextureAtlas("images/monster/skeleton/walk_left.atlas");
        this.skeleton_up = new TextureAtlas("images/monster/skeleton/walk_up.atlas");
        this.skeleton_down = new TextureAtlas("images/monster/skeleton/walk_down.atlas");

        // Trap
        this.cannon = new Texture("images/trap/cannon.png");
        this.cannonball = new Texture("images/trap/cannonball.png");

        // Attack
        this.slash_attack = new TextureAtlas("images/attack/slash_attack.atlas");
    }

    public Texture getPause() { return pause; }

    public Texture getGrass() {
        return this.grass;
    }

    public Texture getCannon() {
        return this.cannon;
    }

    public Texture getCannonball() {
        return this.cannonball;
    }

    public Animation getAnimatedTree() {
        Array<Sprite> img = this.animeTree.createSprites();
        return new Animation(FRAMERATE, img, Animation.PlayMode.LOOP);
    }

    public Animation getAnimatedGreenPortal() {
        Array<Sprite> img = this.animGreenPortal.createSprites();
        return new Animation(FRAMERATE, img, Animation.PlayMode.LOOP);
    }

    public Animation getAnimatedPurplePortal() {
        Array<Sprite> img = this.animPurplePortal.createSprites();
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

    public Animation getAnimatedSkeletonRight() {
        Array<Sprite> img = this.skeleton_right.createSprites();
        return new Animation(FRAMERATE, img, Animation.PlayMode.LOOP);
    }

    public Animation getAnimatedSkeletonLeft() {
        Array<Sprite> img = this.skeleton_left.createSprites();
        return new Animation(FRAMERATE, img, Animation.PlayMode.LOOP);
    }

    public Animation getAnimatedSkeletonUp() {
        Array<Sprite> img = this.skeleton_up.createSprites();
        return new Animation(FRAMERATE, img, Animation.PlayMode.LOOP);
    }

    public Animation getAnimatedSkeletonDown() {
        Array<Sprite> img = this.skeleton_down.createSprites();
        return new Animation(FRAMERATE, img, Animation.PlayMode.LOOP);
    }

    public Animation getAnimatedSlashAttack() {
        Array<Sprite> img = this.slash_attack.createSprites();
        return new Animation(1/20f, img, Animation.PlayMode.NORMAL);
    }
}
