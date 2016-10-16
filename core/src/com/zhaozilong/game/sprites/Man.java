package com.zhaozilong.game.sprites;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by zhaozilong on 2016/10/15.
 */

public class Man {

    private static final int GRAVITY = -30;
    private static final int MOVEMENT = 160;
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;

    private Texture man;

    public Man(int x, int y){
        position = new Vector3(x, y ,0);
        velocity = new Vector3(0, 0 ,0);
        man = new Texture("man.png");
        bounds = new Rectangle(x, y ,man.getWidth(), man.getHeight());
    }

    public void update(float dt){

        if(position.y > 0)
            velocity.add(0, GRAVITY, 0);

        velocity.scl(dt);
        position.add(MOVEMENT * dt, velocity.y, 0);



        if(position.y < 100 )
            position.y = 100;

        velocity.scl(1/dt);
        bounds.setPosition(position.x, position.y);
    }
//    public void changeTexture(String name){
//        man =
//    }

    public Vector3 getPosition() {
        return position;
    }

    public Texture getTexture() {
        return man;
    }

    public void jump(){
        velocity.y = 700;
    }
    public Rectangle getBounds(){
        return bounds;
    }
}
