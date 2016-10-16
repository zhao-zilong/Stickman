package com.zhaozilong.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
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
    private Texture ground;
    private Sound pain_hit;
    //private Obstacle obs;

    private Array<Obstacle> obstacles;
    private float begintime = 0;
    private boolean isreleased = true;


    public PlayState(GameStateManager gsm) {
        super(gsm);

        man = new Man(50, 100);
        cam.setToOrtho(false, Stickman.WIDTH / 2, Stickman.HEIGHT / 2);
        background = new Texture("background.png");
        ground = new Texture("ground.png");
   //     obs = new Obstacle(0);
        pain_hit = Gdx.audio.newSound(Gdx.files.internal("pain_hit.ogg"));

        obstacles = new Array<Obstacle>();
        for(int i = 1; i<= OBS_COUNT; i++){
            obstacles.add(new Obstacle(i*(OBS_SPACING + Obstacle.OBSTACLE_WIDTH) + 200));
        }
        Gdx.input.setInputProcessor(new MyInputProcessor());
    }

    @Override
    protected void handleInput(){
        if(Gdx.input.justTouched()) {
//            if (Gdx.input.getX() > 584 && man.getPosition().y == 100){
//                while(isreleased == false && (System.nanoTime()-begintime)/1000000000.0f < 0.2){}
//                if(isreleased == true)
//                    System.out.println("short");
//                else
//                    System.out.println("long");
////                man.jump();
//            }
             if (Gdx.input.getX() <= 584) {
               try {
                   man.roll();
               }
               catch(InterruptedException e){
                   System.out.println(e.getMessage());
               }
            }
        }



    }

    @Override
    public void update(float dt) {
        handleInput();
        man.update(dt);
        cam.position.x = man.getPosition().x + 200;

    //    System.out.println(cam.viewportWidth);


        for(Obstacle obs : obstacles){
            if(cam.position.x - (cam.viewportWidth / 2) > obs.getPosObs().x + obs.getObstacle().getWidth()){

                    obs.reposition(obs.getPosObs().x + ((Obstacle.OBSTACLE_WIDTH + OBS_SPACING) * 5 ) + Obstacle.OBSTACLE_WIDTH);
            }
            if(obs.collides(man.getBounds()))
                  pain_hit.play(0.5f);
           //     gsm.set(new PlayState(gsm));
        }

        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);

        float elapsedTime;
        if(man.getIsroll()){
            elapsedTime = (System.nanoTime()-man.getNanoTime())/1000000000.0f;
            if(elapsedTime > 0.8f){
                man.Recovery();
            }
        }

        if((System.nanoTime()-begintime)/1000000000.0f >= 0.15f && isreleased == false
                && man.getPosition().y == 100 &&Gdx.input.getX() > 584){
            man.jump();
            man.ResetGRAVITY(-25);
            isreleased = true;
        }

        sb.begin();
        sb.draw(background, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(ground, cam.position.x - (cam.viewportWidth / 2), 84);
        sb.draw(man.getTexture(), man.getPosition().x, man.getPosition().y);
        for(Obstacle obs : obstacles){
            sb.draw(obs.getObstacle(), obs.getPosObs().x, obs.getPosObs().y);

        }

        sb.end();

    }

    @Override
    public void dispose() {
        background.dispose();
        man.dispose();
        for(Obstacle obs: obstacles){
            obs.dispose();
        }
        pain_hit.dispose();
        System.out.print("play state disposed");
    }

    public class MyInputProcessor implements InputProcessor {

     //   public long begintime;

        public boolean keyDown (int keycode) {
            return false;
        }

        public boolean keyUp (int keycode) {
            return false;
        }

        public boolean keyTyped (char character) {
            return false;
        }

        public boolean touchDown (int x, int y, int pointer, int button) {
            begintime = System.nanoTime();
            isreleased = false;
            return false;
        }

        public boolean touchUp (int x, int y, int pointer, int button) {
            if((System.nanoTime()-begintime)/1000000000.0f < 0.15f
                    && man.getPosition().y == 100 && Gdx.input.getX() > 584){
                man.jump();

            }
            isreleased = true;
            return false;
        }

        public boolean touchDragged (int x, int y, int pointer) {
            return false;
        }

        public boolean mouseMoved (int x, int y) {
            return false;
        }

        public boolean scrolled (int amount) {
            return false;
        }
    }
    }
