package com.devkasatkin.jackthegiant.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.devkasatkin.jackthegiant.scenes.Gameplay;

public class GameMain extends Game {
	private SpriteBatch batch;

	public SpriteBatch getBatch() {
		return batch;
	}

	@Override
	public void create () {
		batch = new SpriteBatch();
		setScreen(new Gameplay(this));
	}

	@Override
	public void render () {
		super.render();
	}
}
