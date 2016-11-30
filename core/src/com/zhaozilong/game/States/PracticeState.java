package com.zhaozilong.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.utils.Array;
import com.zhaozilong.game.Stickman;
import com.zhaozilong.game.sprites.Man;
import com.zhaozilong.game.sprites.Obstacle;

import java.util.Random;


/**
 * Created by zhaozilong on 2016/10/13.
 */

public class PracticeState extends State {

    public static final int OBS_SPACING = 150;
    private static final int OBS_COUNT = 6;
    private static final int FROZENTIME = 1;
    private static final int TRANSPARENTEFFECT = 5;
    private static final int ACCELERATEEFFECT = 10;
    private static final int MINIEFFECT = 10;


    private Man man;
    private Texture background;
    private Texture ground;
    private Texture slots;
    private Texture back;
    private Texture magic = new Texture("magic/magic.png");
    private TextureRegion magicRegion;


    private Sound pain_hit;
    private Socket client;
    private int combo = 0;
    private boolean emit = true;
    private float highJumpControl = 0.18f;
    public Random rand = new Random();



    private int score;
    private String scorePoster;
    BitmapFont scoreBitmapFont;

    private Array<Obstacle> obstacles;
    private Array<Integer> magics;
    private float begintime = 0;
    private boolean isreleased = true;
    private boolean acceleAvailable;
    private boolean isAllowedLaunch = true;
    private float accelX;
    private float accelY;
    private float accelZ;
    private float initAccelX;
    private float initAccelY;
    private float initAccelZ;
    private float timeFrozen;




    public PracticeState(GameStateManager gsm) {
        super(gsm);

        magics = new Array<Integer>();

        man = new Man(50, 100);
        cam.setToOrtho(false, Stickman.WIDTH / 2, Stickman.HEIGHT / 2);
        background = new Texture("background.png");
        ground = new Texture("ground.png");
        slots = new Texture("slots.png");
        back = new Texture("back.png");

        pain_hit = Gdx.audio.newSound(Gdx.files.internal("music/pain_hit.ogg"));

        obstacles = new Array<Obstacle>();
        for(int i = 1; i<= OBS_COUNT; i++){
            obstacles.add(new Obstacle(i*(OBS_SPACING + Obstacle.OBSTACLE_WIDTH) + Stickman.WIDTH/OBS_COUNT));
        }
        Gdx.input.setInputProcessor(new MyInputProcessor());
        acceleAvailable = Gdx.input.isPeripheralAvailable(Input.Peripheral.Accelerometer);
        if(acceleAvailable){
            initAccelX = Gdx.input.getAccelerometerX();
            initAccelY = Gdx.input.getAccelerometerY();
            initAccelZ = Gdx.input.getAccelerometerZ();
        }
        score = 0;
        scorePoster = "score: 0";
        scoreBitmapFont = new BitmapFont();

    }

    @Override
    protected void handleInput(){
        if(Gdx.input.justTouched()) {
            System.out.println(Gdx.input.getX() + " " + Gdx.input.getY());
            if (Gdx.input.getX() <= Stickman.WIDTH/2 && Gdx.input.getY() > Stickman.HEIGHT/2) {
                try {
                    man.roll();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
            if (Gdx.input.getX() >= Stickman.WIDTH/2 - 200
                    && Gdx.input.getY() <= 80){
                    gsm.set(new MenuState(gsm));
            }
        }

        if(acceleAvailable){


            accelX = Gdx.input.getAccelerometerX();

            float ecartX = initAccelX - accelX;

            if(ecartX > 3 && isAllowedLaunch == true){
                System.out.println("pouvoir3");

                if(magics.size > 2){
                    launchPouvoir(magics.get(2));
                    magics.removeIndex(2);
                }
                timeFrozen = System.nanoTime();
                isAllowedLaunch = false;
            }
            accelZ = Gdx.input.getAccelerometerZ();
            if(ecartX < -3 && isAllowedLaunch == true){
                System.out.println("pouvoir4");
                if(magics.size > 3){
                    launchPouvoir(magics.get(3));
                    magics.removeIndex(3);
                }
                timeFrozen = System.nanoTime();
                isAllowedLaunch = false;
            }

            accelY = Gdx.input.getAccelerometerY();

            float ecartY = initAccelY - accelY;

            if(ecartY > 3 && isAllowedLaunch == true){
                System.out.println("pouvoir1");
                if(magics.size > 0){
                    launchPouvoir(magics.get(0));
                    magics.removeIndex(0);
                }
                timeFrozen = System.nanoTime();
                isAllowedLaunch = false;
            }
            accelY = Gdx.input.getAccelerometerY();
            if(ecartY < -3 && isAllowedLaunch == true){
                System.out.println("pouvoir2");
                if(magics.size > 1){
                    launchPouvoir(magics.get(1));
                    magics.removeIndex(1);
                }
                timeFrozen = System.nanoTime();
                isAllowedLaunch = false;
            }


            if(isAllowedLaunch == false && ecartY < 2 && ecartY > -2
                    && ecartX < 2 && ecartX > -2 && (System.nanoTime()-timeFrozen)/1000000000.0f > FROZENTIME)
            {
                System.out.println("recover");
                isAllowedLaunch = true;
            }
       }

    }

    @Override
    public void update(float dt) {
        handleInput();
        man.update(dt);
        cam.position.x = man.getPosition().x + 200;



        for(Obstacle obs : obstacles){
            if(cam.position.x - (cam.viewportWidth / 2) > obs.getPosObs().x + obs.getObstacle().getWidth()){
                obs.repositionPractice(obs.getPosObs().x + Stickman.WIDTH / 2 + Obstacle.OBSTACLE_WIDTH);
            }
            if(obs.collides(man.getBounds()) && obs.isCounted() == false ) {
                //pain_hit.play(0.5f);
                obs.setCounted(true);
                this.combo = 0;
                if(!man.getIsTran()) {
                    this.score--;
                }
                //System.out.println(score);
                scorePoster = "score: "+score;

                //gsm.set(new MenuState(gsm));
            }
            //must test if the obstacle  has already counted, because update(float dt) is always refreshed.
            if(man.getPosition().x > obs.getPosObs().x + obs.getObstacle().getWidth() && obs.isCounted() == false){
                obs.setCounted(true);
                this.score++;
                scorePoster = "score: "+score;
                this.combo++;
                if(this.combo == OBS_COUNT){ this.combo = 1; emit = true;}
            }

        }
        if(this.combo == OBS_COUNT-1 && emit == true){
            System.out.println("in combo == 5");
            int magicNumber = rand.nextInt(3);
            System.out.println(magicNumber);
            if(magics.size < 7)
                magics.add(magicNumber);
            emit = false;
        }

        cam.update();
    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);

        float elapsedTime;
        if(man.getIsroll()){
            elapsedTime = (System.nanoTime()-man.getRollBeginTimeTime())/1000000000.0f;
            if(elapsedTime > 0.8f){
                man.recoveryRoll();
            }
        }
        if(man.getIsTran()){
            elapsedTime = (System.nanoTime()-man.getTranBeginTime())/1000000000.0f;
            if(elapsedTime > TRANSPARENTEFFECT){
                man.terminateTransparent();
            }
        }

        if(man.getIsAcce()){
            elapsedTime = (System.nanoTime()-man.getAcceBeginTime())/1000000000.0f;
            if(elapsedTime > ACCELERATEEFFECT){
                man.recoveryAcce();
            }
        }

        if(man.getIsMini()){
            elapsedTime = (System.nanoTime()-man.getMiniBeginTime())/1000000000.0f;
            if(elapsedTime > MINIEFFECT){
                man.terminateMiniature();
            }
        }

        if((System.nanoTime()-begintime)/1000000000.0f >= 0.15f && isreleased == false
                && man.getPosition().y == 100 &&Gdx.input.getX() > Stickman.WIDTH/2 && Gdx.input.getY() > Stickman.HEIGHT/2){
            man.jump();
            man.ResetGRAVITY(-25);
            isreleased = true;
        }

        sb.begin();
        sb.draw(background, cam.position.x - (cam.viewportWidth / 2), 0);
        sb.draw(ground, cam.position.x - (cam.viewportWidth / 2), 84);
        sb.draw(man.getTexture(), man.getPosition().x, man.getPosition().y);
        sb.draw(slots, cam.position.x - slots.getWidth() / 4, cam.position.y*4/3);
        for(Obstacle obs : obstacles){
            sb.draw(obs.getObstacle(), obs.getPosObs().x, obs.getPosObs().y);

        }
        for(int i = 0; i < magics.size; i++){
            magicRegion = new TextureRegion(magic, magics.get(i)*80+1, 0, 80, 80);
            sb.draw(magicRegion, i * 82 + cam.position.x - (slots.getWidth() / 4) + 1, cam.position.y * 4 / 3 + 1);
        }
        sb.draw(back, cam.position.x + Stickman.WIDTH/4 - 120, cam.position.y*2 - 40);
        scoreBitmapFont.setColor(0.0f, 0.0f, 0.0f, 1.0f);
        scoreBitmapFont.getData().setScale(2f);
        scoreBitmapFont.draw(sb, scorePoster, cam.position.x - Stickman.WIDTH/4 + 20, cam.position.y*2 - 20);



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


    public void launchPouvoir(int pouvoirNumver){
        Gdx.input.vibrate(2000);
        if(pouvoirNumver == 0){
            man.accelerate();
            return;
        }
        if(pouvoirNumver == 1){
            man.transparent();
            return;
        }
        if(pouvoirNumver == 2){
            man.miniature();
            return;
        }

    }




    public class MyInputProcessor implements InputProcessor {


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
            if((System.nanoTime()-begintime)/1000000000.0f < highJumpControl
                    && man.getPosition().y == 100 && Gdx.input.getX() > Stickman.WIDTH/2 && Gdx.input.getY() > Stickman.HEIGHT/2){
                System.out.println("long");
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
