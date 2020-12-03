package com.test;

import com.zeldiablo.models.GameState;
import com.zeldiablo.models.enums.State;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GameStateTest {
    GameState gs;

    @BeforeEach
    void ini(){
        gs = new GameState();
    }

    @Test
    void StateTest(){
        gs.setState(State.PAUSED);
        assertTrue(gs.isPaused());
        gs.setState(State.RESET);
        assertTrue(gs.isReset());
        gs.setState(State.LOADING);
        assertTrue(gs.isLoading());
    }

}