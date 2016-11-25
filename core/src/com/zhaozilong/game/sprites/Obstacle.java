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
    //actually the total number is TOTALOBS plus the initial obtacles
    public static final int TOTALOBS = 44;


    private Texture obstacle;
    private Vector2 posObs;
    private Rectangle rec_obs;
    private Random rand;
    private boolean counted = false;
    private static int counter = 0;
    private static int idObs = 0;

    private int[][] plan={{30,100},{35,155},{30,155},{52,100},{10,100},{10,155},{60,155},{5,100},{23,100},{11,100},
                          {17,155},{30,100},{13,155},{34,100},{43,100},{1,155},{32,100},{49,100},{22,155},{21,155},
                          {12,100},{23,100},{34,155},{43,155},{38,155},{49,100},{30,100},{51,100},{31,100},{5,155},
                          {55,155},{30,100},{2,100},{20,100},{30,155},{27,100},{18,155},{6,155},{9,155},{45,100},
                          {30,155},{29,155},{4,100},{12,100},{53,100},{55,100},{36,100},{4,155},{19,100},{15,100},
                          {34,100},{42,155},{47,155},{12,100}};


    public Obstacle(float x){
        obstacle = new Texture("obstacle.png");
        idObs++;
        rand = new Random();
//        if(rand.nextInt(10)>3)
//            posObs = new Vector2(x , 100);
//        else
//            posObs = new Vector2(x , 155);
        if(idObs == 1 || idObs == 2 || idObs == 4){
            posObs = new Vector2(x , 100);
        }
        else
        {
            posObs = new Vector2(x , 155);
        }

        rec_obs = new Rectangle(posObs.x, posObs.y, obstacle.getWidth(), obstacle.getHeight());
    }




    public Texture getObstacle() {
        return obstacle;
    }

    public Vector2 getPosObs() {
        return posObs;
    }

    public void reposition(float x){

        if(getcounter() < TOTALOBS) {
//            if (rand.nextInt(10) > 3)
//                posObs.set(x + rand.nextInt(60), 100);
//            else
//                posObs.set(x + rand.nextInt(60), 155);


            posObs.set(x + plan[this.counter][0],plan[this.counter][1]);
            rec_obs.setPosition(posObs.x, posObs.y);
            //reset the counted
            this.counted = false;
            this.counter++;
            System.out.println("counter: "+this.counter);
        }
        else{
            posObs.set(x, 84);
            rec_obs.setPosition(posObs.x, posObs.y);
        }



        //System.out.println("outside reposition");
    }
    public void repositionPractice(float x) {
        if (rand.nextInt(10) > 3)
            posObs.set(x + rand.nextInt(60), 100);
        else
            posObs.set(x + rand.nextInt(60), 155);

        rec_obs.setPosition(posObs.x, posObs.y);
        //reset the counted
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

    public static int getcounter() { return counter; }
}
