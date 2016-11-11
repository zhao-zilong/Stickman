package com.zhaozilong.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zhaozilong.game.Stickman;

/**
 * Created by zhaozilong on 2016/10/11.
 */

public class MenuState extends State{

    private Texture background;
    private Texture playBtn;

    public MenuState(GameStateManager gsm) {

        super(gsm);
        cam.setToOrtho(false, Stickman.WIDTH / 2, Stickman.HEIGHT / 2);
        background = new Texture("background.png");
        playBtn = new Texture("start.png");

    }

    @Override
    public void handleInput() {
<<<<<<< HEAD
        // getHeight == 100 means we have replaced 'wait' by 'waiting'
        if(waitBtn.getHeight() == 100){
            ServerSocketHints hints = new ServerSocketHints();
            hints.acceptTimeout = 50000;
            ServerSocket server = Gdx.net.newServerSocket(Net.Protocol.TCP, 9999, hints);
            // wait for the next client connection
            System.out.println("waiting client");

            Socket client = server.accept(null);
            // read message and send it back
            try {
                String message = new BufferedReader(new InputStreamReader(client.getInputStream())).readLine();
                Gdx.app.log("socket server: ", "got client message: " + message);
                client.getOutputStream().write("Hi welcome to server\n".getBytes());
            } catch (IOException e) {
                Gdx.app.log("socket server: ", "an error occured", e);
            }
            //gsm.set(new PlayState(gsm, client));
        }
        if(Gdx.input.justTouched()){

            System.out.println(Gdx.input.getX()/2+" "+ Gdx.input.getY()/2);
//            System.out.println("play: "+(cam.position.x - waitBtn.getWidth() / 2)+"  "+(cam.position.x + waitBtn.getWidth() / 2)+" "
//                    +cam.position.y*5/3+" "+(cam.position.y*5/3 - 100));
//            System.out.println("x: "+cam.position.x+" y:"+cam.position.y);
            if(Gdx.input.getX()/2>(cam.position.x - playBtn.getWidth() / 2) && Gdx.input.getX()/2<(cam.position.x + playBtn.getWidth() / 2)
               && Gdx.input.getY()/2<cam.position.y*2/3 && Gdx.input.getY()/2>(cam.position.y*2/3 - 100)){
                Gdx.input.getTextInput(this, "Ip address of host", "localhost", "");
                while(text == "waiting"){

                    try{
                        Thread.sleep(500);
                    }
                    catch(InterruptedException e) {
                        System.out.println(e.getMessage());
                    }

                }
                System.out.println("before create socket");
                SocketHints hints = new SocketHints();
                Socket client = Gdx.net.newClientSocket(Net.Protocol.TCP, this.text, 9999, hints);
                try {
                    client.getOutputStream().write("hello server, i am client\n".getBytes());
                    String response = new BufferedReader(new InputStreamReader(client.getInputStream())).readLine();
                    Gdx.app.log("socket client: ", "got server message: " + response);
                } catch (IOException e) {
                    Gdx.app.log("socket client: ", "an error occured", e);
                }
                //gsm.set(new PlayState(gsm, client));

            }

            if(Gdx.input.getX()/2>(cam.position.x - waitBtn.getWidth() / 2) && Gdx.input.getX()/2<(cam.position.x + waitBtn.getWidth() / 2)
                    && Gdx.input.getY()/2<cam.position.y*5/3 && Gdx.input.getY()/2>(cam.position.y*5/3 -100)){
                System.out.println("wait");
                waitBtn = new Texture("waiting.png");
            }

            else{

                gsm.set(new PlayState(gsm));
            }

=======
        if(Gdx.input.justTouched()){
            gsm.set(new PlayState(gsm));
    //        dispose();
>>>>>>> e59db892767182d2cb1d68b93c77f5739acb1ec2
        }
    }

    @Override
    public void update(float dt) {
        handleInput();

    }

    @Override
    public void render(SpriteBatch sb) {
        sb.setProjectionMatrix(cam.combined);
        sb.begin();
        sb.draw(background, 0, 0);
<<<<<<< HEAD
        sb.draw(playBtn, cam.position.x - playBtn.getWidth() / 2, cam.position.y*4/3 );
        sb.draw(waitBtn, cam.position.x - waitBtn.getWidth() / 2, cam.position.y/3);
=======
        sb.draw(playBtn, cam.position.x - playBtn.getWidth() / 2, cam.position.y );
>>>>>>> e59db892767182d2cb1d68b93c77f5739acb1ec2
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        System.out.println("Menu disposed");
    }
}
