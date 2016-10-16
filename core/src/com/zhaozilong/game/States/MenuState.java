package com.zhaozilong.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zhaozilong.game.Stickman;

/**
 * Created by zhaozilong on 2016/10/11.
 */

public class MenuState extends State{

    private Texture background;
    private Texture playBtn;

    public MenuState(GameStateManager gsm) {

        super(gsm);
        background = new Texture("background.png");
        playBtn = new Texture("start.png");

    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
    //        dispose();
        }
    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.begin();
        sb.draw(background, 0, 0, Stickman.WIDTH, Stickman.HEIGHT);
        sb.draw(playBtn, (Stickman.WIDTH / 2) - (playBtn.getWidth() / 2), Stickman.HEIGHT / 3);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        System.out.println("Menu disposed");
    }
}
