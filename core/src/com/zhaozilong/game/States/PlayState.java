package com.zhaozilong.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
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
    private static final int OBS_COUNT = 6;


    private Man man;
    private Texture background;
    private Texture ground;
    private Sound pain_hit;
<<<<<<< HEAD
    private Socket client;
    private int score = 0;
    private int combo = 0;
    private boolean emit = true;
    private float highJumpControl = 0.18f;
=======
    //private Obstacle obs;
>>>>>>> e59db892767182d2cb1d68b93c77f5739acb1ec2

    private Array<Obstacle> obstacles;
    private float begintime = 0;
    private boolean isreleased = true;
<<<<<<< HEAD
    private boolean acceleAvailable;
    private boolean isAllowedLaunch = true;
    private float accelX;
    private float accelY;
    private float accelZ;
    private float initAccelX;
    private float initAccelY;
    private float initAccelZ;


    //public PlayState(GameStateManager gsm, Socket client) {
    public PlayState(GameStateManager gsm) {
        super(gsm);
    //    this.client = client;
=======


    public PlayState(GameStateManager gsm) {
        super(gsm);

>>>>>>> e59db892767182d2cb1d68b93c77f5739acb1ec2
        man = new Man(50, 100);
        cam.setToOrtho(false, Stickman.WIDTH / 2, Stickman.HEIGHT / 2);
        background = new Texture("background.png");
        ground = new Texture("ground.png");
   //     obs = new Obstacle(0);
        pain_hit = Gdx.audio.newSound(Gdx.files.internal("pain_hit.ogg"));

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
    }

    @Override
    protected void handleInput(){
        if(Gdx.input.justTouched()) {
<<<<<<< HEAD
            System.out.println(Gdx.input.getX() + " " + Gdx.input.getY());
            if (Gdx.input.getX() <= Stickman.WIDTH/2 && Gdx.input.getY() > Stickman.HEIGHT/2) {
                try {
                    man.roll();
                } catch (InterruptedException e) {
                    System.out.println(e.getMessage());
                }
            }
        }
//        try {
//            if(client.getInputStream().available() > 0){
//                String message = new BufferedReader(new InputStreamReader(client.getInputStream())).readLine();
//                Gdx.app.log("socket server: ", "got client message: " + message);
//            }
//        }catch (IOException e) {
//            Gdx.app.log("socket server: ", "an error occured", e);
//        }



        if(acceleAvailable){


            accelX = Gdx.input.getAccelerometerX();

            float ecartX = initAccelX - accelX;

            if(ecartX > 2 && isAllowedLaunch == true){
                System.out.println("pouvoir3");
                isAllowedLaunch = false;
            }
            accelZ = Gdx.input.getAccelerometerZ();
            if(ecartX < -2 && isAllowedLaunch == true){
                System.out.println("pouvoir4");
                isAllowedLaunch = false;
            }

            accelY = Gdx.input.getAccelerometerY();

            float ecartY = initAccelY - accelY;

            if(ecartY > 2 && isAllowedLaunch == true){
                System.out.println("pouvoir1");
                isAllowedLaunch = false;
            }
            accelY = Gdx.input.getAccelerometerY();
            if(ecartY < -2 && isAllowedLaunch == true){
                System.out.println("pouvoir2");
                isAllowedLaunch = false;
            }


            if(isAllowedLaunch == false && ecartY < 1 && ecartY > -1
                    && ecartX < 1 && ecartX > -1)
            {
                System.out.println("recover");
                isAllowedLaunch = true;
            }
            //System.out.println(Gdx.input.getAccelerometerX()+" "+accelY+" "+accelZ);
=======
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
>>>>>>> e59db892767182d2cb1d68b93c77f5739acb1ec2
        }
          //    System.out.println(Gdx.input.getAccelerometerX()+" "+Gdx.input.getAccelerometerY()+" "+Gdx.input.getAccelerometerZ());



    }

    @Override
    public void update(float dt) {
        handleInput();
        man.update(dt);
        cam.position.x = man.getPosition().x + 200;



        for(Obstacle obs : obstacles){
            if(cam.position.x - (cam.viewportWidth / 2) > obs.getPosObs().x + obs.getObstacle().getWidth()){
<<<<<<< HEAD
                obs.reposition(obs.getPosObs().x + Stickman.WIDTH / 2 + Obstacle.OBSTACLE_WIDTH);
            }
            if(obs.collides(man.getBounds())) {
                //pain_hit.play(0.5f);
                this.combo = 0;
                this.score--;
                //gsm.set(new MenuState(gsm));
            }
            if(man.getPosition().x > obs.getPosObs().x + obs.getObstacle().getWidth() && obs.isCounted() == false){
                obs.setCounted(true);
                this.score++;
                this.combo++;
                if(this.combo == OBS_COUNT){ this.combo = 1; emit = true;}
            }

        }
//        if(this.combo == OBS_COUNT-1 && emit == true){
//            System.out.println("in combo == 3");
//            try{
//            this.client.getOutputStream().write("using a skill here\n".getBytes());
//
//            } catch (IOException e) {
//              Gdx.app.log("socket server: ", "an error occured", e);
//            }
//            emit = false;
//        }
=======

                    obs.reposition(obs.getPosObs().x + ((Obstacle.OBSTACLE_WIDTH + OBS_SPACING) * 5 ) + Obstacle.OBSTACLE_WIDTH);
            }
            if(obs.collides(man.getBounds()))
            //      pain_hit.play(0.5f);
                  gsm.set(new MenuState(gsm));
        }
>>>>>>> e59db892767182d2cb1d68b93c77f5739acb1ec2

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
<<<<<<< HEAD
                && man.getPosition().y == 100 &&Gdx.input.getX() > Stickman.WIDTH/2 && Gdx.input.getY() > Stickman.HEIGHT/2){
=======
                && man.getPosition().y == 100 &&Gdx.input.getX() > 584){
>>>>>>> e59db892767182d2cb1d68b93c77f5739acb1ec2
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
<<<<<<< HEAD
            if((System.nanoTime()-begintime)/1000000000.0f < highJumpControl
                    && man.getPosition().y == 100 && Gdx.input.getX() > Stickman.WIDTH/2 && Gdx.input.getY() > Stickman.HEIGHT/2){
=======
            if((System.nanoTime()-begintime)/1000000000.0f < 0.15f
                    && man.getPosition().y == 100 && Gdx.input.getX() > 584){
>>>>>>> e59db892767182d2cb1d68b93c77f5739acb1ec2
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
