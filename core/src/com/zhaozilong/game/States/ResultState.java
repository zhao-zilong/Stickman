package com.zhaozilong.game.States;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zhaozilong.game.Stickman;

/**
 * Created by zhaozilong on 2016/11/23.
 */

public class ResultState extends State {

    private Texture background;
    private Texture billboard;
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

    }

    @Override
    protected void handleInput() {

    }

    @Override
    public void update(float dt) {

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
        sb.draw(billboard, cam.position.x - billboard.getWidth() / 2, cam.position.y );
        sb.end();
    }

    @Override
    public void dispose() {

    }
}
