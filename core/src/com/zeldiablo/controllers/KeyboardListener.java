package com.zeldiablo.controllers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.zeldiablo.models.Entity;
import com.zeldiablo.models.GameWorld;

/**
 * @author Sousa Ribeiro Pedro
 */
public class KeyboardListener extends ControllerAdapter {

    private final int SPEED_STEP = (int) Entity.SIZE;

    private Action action;
    private boolean pause;
    private boolean debug;
    private boolean reset;
    private boolean attack;

    private Vector2 step;
    private float x;
    private float y;

    public KeyboardListener() {
        this.pause = false;
        this.debug = true;
        this.attack = false;
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
        switch (keycode) {
            case Input.Keys.Z:
                this.y += SPEED_STEP;
                break;
            case Input.Keys.D:
                this.x += SPEED_STEP;
                break;
            case Input.Keys.S:
                this.y -= SPEED_STEP;
                break;
            case Input.Keys.Q:
                this.x -= SPEED_STEP;
                break;
            case Input.Keys.P:
                this.pause = !this.pause;
                break;
            case Input.Keys.O:
                this.debug = !this.debug;
                break;
            case Input.Keys.R:
                this.reset = true;
                break;
            case Input.Keys.ESCAPE:
                Gdx.app.exit();
                break;
            case Input.Keys.SPACE:
                this.attack = true;
                this.action = Action.Left_click;
                break;
            default:
                break;
        }

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
        switch (keycode) {
            case Input.Keys.Z:
                this.y -= SPEED_STEP;
                break;
            case Input.Keys.D:
                this.x -= SPEED_STEP;
                break;
            case Input.Keys.S:
                this.y += SPEED_STEP;
                break;
            case Input.Keys.Q:
                this.x += SPEED_STEP;
                break;
            default:
                break;
        }

        return true;
    }

    /**
     * Indique le déplacement à faire par le personnage
     * @return Vector2
     */
    public Vector2 getStep() {
        this.step.set(this.x, this.y);
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

    /**
     * Indique si le joueur à reset le monde
     * @return le boolean reset
     */
    public boolean isReset() { return this.reset;}

    public void setReset(boolean reset) { this.reset = reset; }

    public boolean isAttack() {
        if (attack){
            attack = !attack;
            return true;
        }
        return false;
    }

}
