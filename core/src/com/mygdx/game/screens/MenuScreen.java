package com.mygdx.game.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class MenuScreen implements Screen {
    Game game;
    Texture fon,sign;
    SpriteBatch batch;
    int x;
    Rectangle rectangle;
    private final Music music;



    public MenuScreen(Game game) {
        this.game = game;
        fon = new Texture("fon.jpg");
        sign = new Texture("Sign.png");
        x=Gdx.graphics.getWidth()/2 - sign.getWidth()/2;
        rectangle = new Rectangle(x,0, sign.getWidth(), sign.getHeight());
        batch = new SpriteBatch();
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/ChecksForFree.mp3"));
        music.setLooping(true);
        music.setVolume(0.2f);
        music.play();

    }


    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {

        batch.begin();
        batch.draw(fon,0,0, Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        batch.draw(sign,x,0);
        batch.end();

        //ScreenUtils.clear(Color.GREEN);
        if (Gdx.input.isTouched()){
            if (rectangle.contains(Gdx.input.getX(),Gdx.graphics.getHeight() - Gdx.input.getY())){
                dispose();
                game.setScreen(new GameScreen(game));
                music.stop();

            }
        }
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        this.fon.dispose();
        this.sign.dispose();
        this.batch.dispose();
        this.music.dispose();

    }
}


