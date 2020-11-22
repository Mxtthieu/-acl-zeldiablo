package com.zeldiablo;

import com.badlogic.gdx.Game;
import com.zeldiablo.views.MainMenu;

public class Zeldiablo extends Game {

	@Override
	public void create() {
		setScreen(new MainMenu());
	}
}

