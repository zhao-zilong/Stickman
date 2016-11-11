package com.zhaozilong.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zhaozilong.game.States.GameStateManager;
import com.zhaozilong.game.States.MenuState;
public class Stickman extends ApplicationAdapter {

	public static  int WIDTH ;
	public static  int HEIGHT ;

	public static final String TITLE = "Stickman";

	private GameStateManager gsm;



	private SpriteBatch batch;

	private Music music;
	
	@Override
	public void create () {
		 WIDTH = Gdx.app.getGraphics().getWidth(); //1280
		System.out.println("width: "+WIDTH);
	     HEIGHT = Gdx.app.getGraphics().getHeight(); //768
		System.out.println("width: "+HEIGHT);
		batch = new SpriteBatch();
		gsm = new GameStateManager();
		music = Gdx.audio.newMusic(Gdx.files.internal("music.ogg"));
		music.setLooping(true);
		music.setVolume(0.1f);
		music.play();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		gsm.push(new MenuState(gsm));
	}

	@Override
	public void render () {

		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		gsm.update(Gdx.graphics.getDeltaTime());
		gsm.render(batch);

//		batch.begin();
//		batch.draw(img, 0, 0);
//		batch.end();
	}
	
//	@Override
	public void dispose () {
		music.dispose();
	}
}
