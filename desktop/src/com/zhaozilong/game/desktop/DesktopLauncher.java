package com.zhaozilong.game.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.zhaozilong.game.Stickman;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();

		config.width = Stickman.WIDTH;
		config.height = Stickman.HEIGHT;
		config.title = Stickman.TITLE;

		new LwjglApplication(new Stickman(), config);
	}
}
