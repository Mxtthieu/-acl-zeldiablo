package com.zeldiablo.models;

import com.zeldiablo.models.enums.State;

public class GameState {

    private State state;

    /**
     * GameState servira à gérer l'état de notre jeu
     */
    public GameState() { }


    public boolean isPaused() { return state == State.PAUSED; }

    public void setState(State state) { this.state = state; }
}
