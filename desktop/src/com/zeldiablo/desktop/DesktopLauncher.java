package com.zeldiablo.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.zeldiablo.Zeldiablo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.a = 8;
		config.height = 720;
		config.width = 1024;
		new LwjglApplication(new Zeldiablo(), config);
	}
}
