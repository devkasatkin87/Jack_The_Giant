package com.devkasatkin.jackthegiant.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.devkasatkin.jackthegiant.main.GameMain;
import com.devkasatkin.jackthegiant.helpers.GameInfo;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = GameInfo.WIDTH;
		config.height = GameInfo.HEIGHT;
		config.title = "Jack The Giant";
		new LwjglApplication(new GameMain(), config);
	}
}
