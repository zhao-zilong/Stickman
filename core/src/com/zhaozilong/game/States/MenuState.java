package com.zhaozilong.game.States;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.TextInputListener;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.net.ServerSocket;
import com.badlogic.gdx.net.ServerSocketHints;
import com.badlogic.gdx.net.Socket;
import com.badlogic.gdx.net.SocketHints;
import com.zhaozilong.game.Stickman;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by zhaozilong on 2016/10/11.
 */

public class MenuState extends State {

    private Texture background;
    private Texture playBtn;
    private Texture waitBtn;
    private Texture practiceBtn;

    public String text = "waiting";

    public boolean isInputTreating = false;

    public MenuState(GameStateManager gsm) {

        super(gsm);
        cam.setToOrtho(false, Stickman.WIDTH / 2, Stickman.HEIGHT / 2);
        background = new Texture("background.png");
        playBtn = new Texture("menu/start.png");
        waitBtn = new Texture("menu/wait.png");
        practiceBtn = new Texture("menu/practice.png");

    }

    @Override
    public void handleInput() {
        // getHeight == 100 means we have replaced 'wait' by 'waiting'
        if(waitBtn.getHeight() == 100){
            ServerSocketHints hints = new ServerSocketHints();
            hints.acceptTimeout = 50000;
            ServerSocket server = Gdx.net.newServerSocket(Net.Protocol.TCP, 11111, hints);
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
            gsm.set(new PlayState(gsm, client));
        }
        if(text != "waiting" || Gdx.input.justTouched()){

            System.out.println(Gdx.input.getX()/2+" "+ Gdx.input.getY()/2);
//            System.out.println("play: "+(cam.position.x - waitBtn.getWidth() / 2)+"  "+(cam.position.x + waitBtn.getWidth() / 2)+" "
//                    +cam.position.y*5/3+" "+(cam.position.y*5/3 - 100));
//            System.out.println("x: "+cam.position.x+" y:"+cam.position.y);
            if(text != "waiting" || Gdx.input.getX()/2>(cam.position.x - playBtn.getWidth() / 2) && Gdx.input.getX()/2<(cam.position.x + playBtn.getWidth() / 2)
               && Gdx.input.getY()/2<cam.position.y*2/3 && Gdx.input.getY()/2>(cam.position.y*2/3 - 100)){

                if(!isInputTreating) {
                    Gdx.input.getTextInput(new MyTextInputListener(), "Ip address of host", "localhost", "");
                }
                isInputTreating = true;
                if(text == "waiting"){
                    return;
                }
                System.out.println("before create socket final");
                SocketHints hints = new SocketHints();
                Socket client = Gdx.net.newClientSocket(Net.Protocol.TCP, this.text, 11111, hints);
                try {
                    client.getOutputStream().write("hello server, i am client\n".getBytes());
                    String response = new BufferedReader(new InputStreamReader(client.getInputStream())).readLine();
                    Gdx.app.log("socket client: ", "got server message: " + response);
                } catch (IOException e) {
                    Gdx.app.log("socket client: ", "an error occured", e);
                }
                gsm.set(new PlayState(gsm, client));

            }

            if(Gdx.input.getX()/2>(cam.position.x - waitBtn.getWidth() / 2) && Gdx.input.getX()/2<(cam.position.x + waitBtn.getWidth() / 2)
                    && Gdx.input.getY()/2<cam.position.y*5/3 && Gdx.input.getY()/2>(cam.position.y*5/3 -100)){
                System.out.println("wait");
                waitBtn = new Texture("menu/waiting.png");
            }

            if(Gdx.input.getX()/2>(cam.position.x - practiceBtn.getWidth() / 2) && Gdx.input.getX()/2<(cam.position.x + practiceBtn.getWidth() / 2)
                    && Gdx.input.getY()/2<cam.position.y*6/5 && Gdx.input.getY()/2>(cam.position.y*6/5 -100)){
                System.out.println("practice");
                gsm.set(new PracticeState(gsm));

            }


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
        sb.draw(playBtn, cam.position.x - playBtn.getWidth() / 2, cam.position.y*4/3 );
        sb.draw(waitBtn, cam.position.x - waitBtn.getWidth() / 2, cam.position.y/3);
        sb.draw(practiceBtn, cam.position.x - waitBtn.getWidth() / 2, cam.position.y*4/5);
        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        waitBtn.dispose();
        System.out.println("Menu disposed");
    }


    public class MyTextInputListener implements TextInputListener {
        @Override
        public void input (String txt) {
            text = txt;
            System.out.println("in input");
        }

        @Override
        public void canceled () {
        }
    }
}


