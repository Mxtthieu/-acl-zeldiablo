package com.zeldiablo.factories;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;

public enum TextureFactory {
    INSTANCE;

    private Texture pause;


    TextureFactory() {
        this.pause = new Texture(Gdx.files.internal("images/Pause.png"));
    }

    public Texture getPause() { return pause; }
}
