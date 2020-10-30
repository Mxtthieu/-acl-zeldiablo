package com.zeldiablo.controllers;

import com.badlogic.gdx.Gdx;

public class MouseListener extends ControllerAdapter {

    private float x;
    private float y;

    public MouseListener() {
        this.x = 0;
        this.y = 0;
    }

    /**
     * Appelée quand la souris bouge sans cliquer.
     *
     * @param screenX coordonée X du pointeur
     * @param screenY coordonée Y du pointeur
     * @return si l'entrée à été traitée
     */
    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        this.x = screenX;
        this.y = Gdx.graphics.getHeight() - screenY;
        return true;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
