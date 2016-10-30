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

public class MenuState extends State implements TextInputListener{

    private Texture background;
    private Texture playBtn;
    private Texture waitBtn;

    private String text = "waiting";

    public MenuState(GameStateManager gsm) {

        super(gsm);
        cam.setToOrtho(false, Stickman.WIDTH / 2, Stickman.HEIGHT / 2);
        background = new Texture("background.png");
        playBtn = new Texture("start.png");
        waitBtn = new Texture("wait.png");

    }

    @Override
    public void handleInput() {
        if(Gdx.input.justTouched()){

            System.out.println(Gdx.input.getX()+" "+ Gdx.input.getY());
            if(Gdx.input.getX()>430 && Gdx.input.getX()<860
               && Gdx.input.getY()<260 && Gdx.input.getY()>110){
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
                gsm.set(new PlayState(gsm, client));



            }

            if(Gdx.input.getX()>430 && Gdx.input.getX()<860
                    && Gdx.input.getY()<620 && Gdx.input.getY()>470){
                System.out.println("wait");
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
                gsm.set(new PlayState(gsm, client));

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
        sb.draw(playBtn, cam.position.x - playBtn.getWidth() / 2, cam.position.y+50 );
        sb.draw(waitBtn, cam.position.x - playBtn.getWidth() / 2, cam.position.y/3 );

        sb.end();
    }

    @Override
    public void dispose() {
        background.dispose();
        playBtn.dispose();
        System.out.println("Menu disposed");
    }

    @Override
    public void input(String text) {
        this.text = text;


    }

    @Override
    public void canceled() {
        this.text = "Cancelled";
    }
}
