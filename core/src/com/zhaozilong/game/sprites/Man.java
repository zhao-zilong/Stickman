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
    private int MOVEMENT = 200;
    private Vector3 position;
    private Vector3 velocity;
    private Rectangle bounds;


    private boolean isroll = false;
    private boolean istransparent = false;
    private boolean isAcce = false;
    private long rollBeginTime;
    private long tranBeginTime;
    private long acceBeginTime;

    private Animation  manAnimation;
    private Texture texture;


    private Sound jumpsound;

    public Man(int x, int y){
        position = new Vector3(x, y ,0);
        velocity = new Vector3(0, 0 ,0);
        //man = new Texture("man.png");

        texture = new Texture("animation/runanimation.png");
        manAnimation = new Animation(new TextureRegion(texture), 9, 0.3f);
        bounds = new Rectangle(x, y ,texture.getWidth() / 18, texture.getHeight());
        jumpsound = Gdx.audio.newSound(Gdx.files.internal("music/jump_sound.ogg"));
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

    public void accelerate(){
          MOVEMENT = 300;
          acceBeginTime = System.nanoTime();
          isAcce = true;
    }
    public void recoveryAcce(){
          MOVEMENT = 200;
          isAcce = false;
    }

    public void ResetGRAVITY(int gravity){
        GRAVITY = gravity;
    }

    public void roll() throws InterruptedException{
        isroll = true;
        rollBeginTime = System.nanoTime();
        if(istransparent == false) {
            texture = new Texture("animation/rollanimation.png");
        }
        else{
            texture = new Texture("animation/rollanimationTransparent.png");
        }
        manAnimation = new Animation(new TextureRegion(texture), 9, 0.3f);
        bounds = new Rectangle(position.x, position.y ,texture.getWidth() / 12, texture.getHeight());

    }
    public void transparent(){
        istransparent = true;
        tranBeginTime = System.nanoTime();
        if(isroll){
            texture = new Texture("animation/rollanimationTransparent.png");
        }
        else{
            texture = new Texture("animation/runanimationTransparent.png");
        }
        manAnimation = new Animation(new TextureRegion(texture), 9, 0.3f);
        bounds = new Rectangle(position.x, position.y ,texture.getWidth() / 12, texture.getHeight());
    }

    public void recoveryRoll(){
        if(istransparent == true){
            texture = new Texture("animation/runanimationTransparent.png");
        }
        else {
            texture = new Texture("animation/runanimation.png");
        }
        manAnimation = new Animation(new TextureRegion(texture), 9, 0.3f);
        bounds = new Rectangle(position.x, position.y ,texture.getWidth() / 12, texture.getHeight());
        isroll = false;
    }
    public void terminateTransparent(){
        istransparent = false;
        if(isroll == true){
            texture = new Texture("animation/rollanimation.png");
        }
        else{
            texture = new Texture("animation/runanimation.png");
        }
        manAnimation = new Animation(new TextureRegion(texture), 9, 0.3f);
        bounds = new Rectangle(position.x, position.y ,texture.getWidth() / 12, texture.getHeight());

    }


    public boolean getIsroll(){
        return isroll;
    }
    public boolean getIsTran() { return istransparent; }
    public boolean getIsAcce() { return isAcce; }
    public long getRollBeginTimeTime(){
        return rollBeginTime;
    }
    public long getTranBeginTime() { return tranBeginTime; }
    public long getAcceBeginTime() { return acceBeginTime; }

    public Rectangle getBounds(){
        return bounds;
    }
    public void dispose(){
        texture.dispose();
        jumpsound.dispose();
    }
}
