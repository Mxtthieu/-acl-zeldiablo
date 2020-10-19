package com.zeldiablo.controllers;

import com.badlogic.gdx.Input;

public class KeyboardListener extends ControllerAdapter {

    private Direction direction;
    private boolean pause;
    private boolean debug;

    public KeyboardListener() {
        this.direction = Direction.None;
        this.pause = false;
        this.debug = true;
    }

    /**
     * Appelée lorsque une touche du clavier est préssée
     *
     * @param keycode une des constantes dans {@link Input.Keys}
     * @return si l'entrée à été traitée
     */
    @Override
    public boolean keyDown(int keycode) {
        switch (keycode) {
            case Input.Keys.UP:
                this.direction = Direction.Up;
                break;
            case Input.Keys.RIGHT:
                this.direction = Direction.Right;
                break;
            case Input.Keys.DOWN:
                this.direction = Direction.Down;
                break;
            case Input.Keys.LEFT:
                this.direction = Direction.Left;
                break;
            case Input.Keys.P:
                this.pause = !this.pause;
                break;
            case Input.Keys.D:
                this.debug = !this.debug;
                break;
            default:
                this.direction = Direction.None;
                break;
        }
        return super.keyDown(keycode);
    }
}
