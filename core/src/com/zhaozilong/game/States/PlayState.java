package com.zhaozilong.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Array;
import com.zhaozilong.game.Stickman;
import com.zhaozilong.game.sprites.Man;
import com.zhaozilong.game.sprites.Obstacle;


/**
 * Created by zhaozilong on 2016/10/13.
 */

public class PlayState extends State {

    public static final int OBS_SPACING = 150;
    private static final int OBS_COUNT = 5;


    private Man man;
    private Texture background;
    //private Obstacle obs;

    private Array<Obstacle> obstacles;


    public PlayState(GameStateManager gsm) {
        super(gsm);
   //     man = new Texture("man.png");
        man = new Man(50, 100);
        cam.setToOrtho(false, Stickman.WIDTH / 2, Stickman.HEIGHT / 2);
        background = new Texture("background.png");
   //     obs = new Obstacle(0);

        obstacles = new Array<Obstacle>();
        for(int i = 1; i<= OBS_COUNT; i++){
            obstacles.add(new Obstacle(i*(OBS_SPACING + Obstacle.OBSTACLE_WIDTH) + 200));
        }
    }

    @Override
    protected void handleInput() {
        if(Gdx.input.justTouched())
            if(Gdx.input.getX() > 584&& man.getPosition().y == 100)
            man.jump();

    }

    @Override
    public void update(float dt) {
        handleInput();
        man.update(dt);
        cam.position.x = man.getPosition().x + 200;




        for(Obstacle obs : obstacles){
            if(cam.position.x - (cam.viewportWidth / 2) > obs.getPosObs().x + obs.getObstacle().getWidth()){

                    obs.reposition(obs.getPosObs().x + ((Obstacle.OBSTACLE_WIDTH + OBS_SPACING) * 5 ) + Obstacle.OBSTACLE_WIDTH);
            }
            if(obs.collides(man.getBounds()))
                gsm.set(new PlayState(gsm));
        }

        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);

        sb.begin();
        sb.draw(background, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(man.getTexture(), man.getPosition().x, man.getPosition().y);
        for(Obstacle obs : obstacles){
            sb.draw(obs.getObstacle(), obs.getPosObs().x, obs.getPosObs().y);

        }
//        sb.draw(obs.getObstacle(), obs.getPosObs().x, obs.getPosObs().y);
        sb.end();

    }

    @Override
    public void dispose() {

    }
}
