package com.zeldiablo.models;

import com.zeldiablo.models.enums.State;

public class GameState {

    private State state;

    /**
     * GameState servira à gérer l'état de notre jeu
     */
    public GameState() { }


    public boolean isPaused() { return state == State.PAUSED; }

    public boolean isReset() { return state == State.RESET; }

    public boolean isLoading() { return state == State.LOADING; }

    public void setState(State state) { this.state = state; }

    public boolean isLost() { return state == State.LOST; }
}
