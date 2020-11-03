package com.zeldiablo;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.utils.Timer;
import com.zeldiablo.views.GameScreen;

public class Zeldiablo extends Game {
	private Timer.Task tache;

	public void create () {
		setScreen(new GameScreen());
	}

	public void dispose () {
	}
}

