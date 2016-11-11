package com.zhaozilong.game.sprites;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;

/**
 * Created by zhaozilong on 2016/10/15.
 */

public class Man {

    private static  int GRAVITY = -30;
    private static final int MOVEMENT = 200;
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;


    private boolean isroll = false;
    private long rollBeginTime;

    private Animation  manAnimation;
    private Texture texture;


    private Sound jumpsound;

    public Man(int x, int y){
        position = new Vector3(x, y ,0);
        velocity = new Vector3(0, 0 ,0);
        //man = new Texture("man.png");

        texture = new Texture("runanimation.png");
        manAnimation = new Animation(new TextureRegion(texture), 9, 0.3f);
        bounds = new Rectangle(x, y ,texture.getWidth() / 12, texture.getHeight());
        jumpsound = Gdx.audio.newSound(Gdx.files.internal("jump_sound.ogg"));
    }

    public void update(float dt){
        manAnimation.update(dt);

        if(position.y > 0)
            velocity.add(0, GRAVITY, 0);

        velocity.scl(dt);
        position.add(MOVEMENT * dt, velocity.y, 0);



        if(position.y < 100 )
            position.y = 100;

        velocity.scl(1/dt);
        bounds.setPosition(position.x, position.y);
    }


    public Vector3 getPosition() {
        return position;
    }

    public TextureRegion getTexture() {
        return manAnimation.getFrame();
    }

    public void jump(){

        velocity.y = 700;
        if(GRAVITY != -30)
            GRAVITY = -30;
        jumpsound.play(0.5f);

    }
    public void ResetVelocity(int v){
        velocity.y = v;
    }
    public void ResetGRAVITY(int gravity){
        GRAVITY = gravity;
    }

    public void roll() throws InterruptedException{
        isroll = true;
        rollBeginTime = System.nanoTime();
        texture = new Texture("rollanimation.png");
        manAnimation = new Animation(new TextureRegion(texture), 9, 0.3f);
        bounds = new Rectangle(position.x, position.y ,texture.getWidth() / 12, texture.getHeight());




    }

    public void Recovery(){
        texture = new Texture("runanimation.png");
        manAnimation = new Animation(new TextureRegion(texture), 9, 0.3f);
        bounds = new Rectangle(position.x, position.y ,texture.getWidth() / 12, texture.getHeight());
        isroll = false;
    }

    public boolean getIsroll(){
        return isroll;
    }
    public long getNanoTime(){
        return rollBeginTime;
    }

    public Rectangle getBounds(){
        return bounds;
    }
    public void dispose(){
        texture.dispose();
        jumpsound.dispose();
    }
}
