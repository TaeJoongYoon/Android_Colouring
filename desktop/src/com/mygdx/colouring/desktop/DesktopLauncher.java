package com.mygdx.colouring.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.mygdx.colouring.Coloring;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Coloring";
		config.width = 480;
		config.height = 800;

		new LwjglApplication(new Coloring(), config);
	}
}
