package com.zeldiablo;

import com.badlogic.gdx.Game;
import com.zeldiablo.views.GameScreen;

public class Zeldiablo extends Game {

	private GameScreen gameScreen;

	@Override
	public void create() {
		this.gameScreen = new GameScreen();
		setScreen(this.gameScreen);
	}
}

