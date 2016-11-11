package com.zhaozilong.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.Random;

/**
 * Created by zhaozilong on 2016/10/15.
 */

public class Obstacle {

    public static final int OBSTACLE_WIDTH = 16;


    private static final int FLUCTUATION = 150;
    private static final int OBS_GAP = 100;
    private Texture obstacle;
    private Vector2 posObs;
    private Rectangle rec_obs;
    private Random rand;
    private boolean counted = false;


    public Obstacle(float x){
        obstacle = new Texture("obstacle.png");
        rand = new Random();
        if(rand.nextInt(10)>3)
            posObs = new Vector2(x , 100);
        else
            posObs = new Vector2(x , 155);

        rec_obs = new Rectangle(posObs.x, posObs.y, obstacle.getWidth(), obstacle.getHeight());
    }




    public Texture getObstacle() {
        return obstacle;
    }

    public Vector2 getPosObs() {
        return posObs;
    }

    public void reposition(float x){

        if(rand.nextInt(10)>3)
            posObs.set(x + rand.nextInt(60) , 100);
        else
            posObs.set(x + rand.nextInt(60) , 155);

        rec_obs.setPosition(posObs.x, posObs.y);
        this.counted = false;
    }


    public boolean collides(Rectangle player){
        return player.overlaps(rec_obs);
    }

    public void dispose(){
        obstacle.dispose();
    }

    public void setCounted(boolean counted) {
        this.counted = counted;
    }

    public boolean isCounted() {
        return counted;
    }
}
