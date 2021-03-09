package com.devkasatkin.jackthegiant.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.devkasatkin.jackthegiant.helpers.GameManager;
import com.devkasatkin.jackthegiant.scenes.Gameplay;
import com.devkasatkin.jackthegiant.scenes.MainMenu;

public class GameMain extends Game {
	private SpriteBatch batch;

	public SpriteBatch getBatch() {
		return batch;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		GameManager.getInstance().initializeGameData();
		setScreen(new MainMenu(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
