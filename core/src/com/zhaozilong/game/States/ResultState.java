package com.zhaozilong.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zhaozilong.game.Stickman;

/**
 * Created by zhaozilong on 2016/11/23.
 */

public class ResultState extends State {

    private Texture background;
    private Texture billboard;
    private Texture back;
    public ResultState(GameStateManager gsm, int score, int score_enemy) {
        super(gsm);
        cam.setToOrtho(false, Stickman.WIDTH / 2, Stickman.HEIGHT / 2);
        background = new Texture("background.png");

        if(score>score_enemy){
            billboard = new Texture("menu/win.png");

        }
        else if(score == score_enemy){
            billboard = new Texture("menu/tie.png");
        }
        else{
            billboard = new Texture("menu/lose.png");

        }
            back = new Texture("back.png");

    }

    @Override
    protected void handleInput() {
        if (Gdx.input.getX() >= Stickman.WIDTH/2 - 200
                && Gdx.input.getY() <= 80){
            gsm.set(new MenuState(gsm));
        }
    }

    @Override
    public void update(float dt) {
        handleInput();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(billboard, cam.position.x - billboard.getWidth() / 2, cam.position.y );
        sb.draw(back, cam.position.x + Stickman.WIDTH/4 - 120, cam.position.y*2 - 40);
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
