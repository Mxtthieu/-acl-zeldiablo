package com.zeldiablo.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zeldiablo.factories.TextureFactory;

public class GameWorld {
    private static int widthWorld = 80;
    private static int heightWorld = 60;

    public GameWorld() {}

    public void draw(SpriteBatch gameBatch){

    }

    public int getWidthWorld() {
        return widthWorld;
    }

    public int getHeightWorld() {
        return heightWorld;
    }
}
