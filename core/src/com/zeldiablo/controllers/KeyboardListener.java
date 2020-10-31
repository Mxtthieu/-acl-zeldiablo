package com.zeldiablo.controllers;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

/**
 * @author Sousa Ribeiro Pedro
 */
public class KeyboardListener extends ControllerAdapter {

    private Direction direction;
    private boolean pause;
    private boolean debug;
    private Vector2 step;

    public KeyboardListener() {
        this.direction = Direction.None;
        this.pause = false;
        this.debug = true;
        this.step = new Vector2();
    }

    /**
     * Appelée lorsque une touche du clavier est préssée
     *
     * @param keycode une des constantes dans {@link Input.Keys}
     * @return si l'entrée à été traitée
     */
    @Override
    public boolean keyDown(int keycode) {
        float x = this.step.x;
        float y = this.step.y;
        switch (keycode) {
            case Input.Keys.UP:
                this.direction = Direction.Up;
                y+=10;
                break;
            case Input.Keys.RIGHT:
                this.direction = Direction.Right;
                x+=10;
                break;
            case Input.Keys.DOWN:
                this.direction = Direction.Down;
                y-=10;
                break;
            case Input.Keys.LEFT:
                this.direction = Direction.Left;
                x-=10;
                break;
            case Input.Keys.D:
                this.debug = !this.debug;
                break;
            case Input.Keys.P:
                this.pause = !this.pause;
                break;
            default:
                this.direction = Direction.None;
                break;
        }

        this.step.set(x, y);
        return true;
    }

    /**
     * Appelée lorsque une touche du clavier est relachée
     *
     * @param keycode une des constantes dans {@link Input.Keys}
     * @return si l'entrée à été traitée
     */
    @Override
    public boolean keyUp(int keycode) {
        float x = this.step.x;
        float y = this.step.y;
        switch (keycode) {
            case Input.Keys.UP:
            case Input.Keys.DOWN:
                y = 0;
                break;
            case Input.Keys.RIGHT:
            case Input.Keys.LEFT:
                x = 0;
                break;
            default:
                break;
        }

        this.step.set(x, y);
        return true;
    }

    /**
     * Indique le déplacement à faire par le personnage
     * @return Vector2
     */
    public Vector2 getStep() {
        return this.step;
    }

    /**
     * Indique si le mode debug est activé ou non
     * @return mode debug activé
     */
    public boolean isDebug() {
        return this.debug;
    }

    /**
     * Indique si le mode pause est activé ou non
     * @return le boolean est en pause
     */
    public boolean isPaused() {
        return this.pause;
    }
}
