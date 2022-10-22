package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img, coinImg;
	MyAtlasAnim run, stand, tmpA;
	private Music music;
	private Sound sound;
	MyInputProcessor myInputProcessor;
	  private  float x,y;
	  int dir =0, step =1;



	@Override
	public void create () {
		myInputProcessor = new MyInputProcessor();
		Gdx.input.setInputProcessor(myInputProcessor);

		music=Gdx.audio.newMusic(Gdx.files.internal("Sirenia_-_Dim_Days_Of_Dolor_47887109.mp3"));
		music.setVolume(0.125f);
		music.setLooping(true);
		music.pause();

		sound=Gdx.audio.newSound(Gdx.files.internal("77fae3ab5b341cd.mp3"));

		batch = new SpriteBatch();
		coinImg = new Texture("g.png");

		stand = new MyAtlasAnim("atlas/unnamed.atlas","stand", 8, true, "Sirenia_-_Dim_Days_Of_Dolor_47887109.mp3");
		run = new MyAtlasAnim("atlas/unnamed.atlas","run", 8, true, "Sirenia_-_Dim_Days_Of_Dolor_47887109.mp3");
		tmpA = stand;

	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);


		tmpA = stand;
		dir = 0;
		//float x = Gdx.input.getX() - anim.draw().getRegionWidth()/2.0f;
		//float y = Gdx.graphics.getHeight() - Gdx.input.getY() - anim.draw().getRegionHeight()/2.0f;

		if (Gdx.input.isButtonJustPressed(Input.Buttons.LEFT)) sound.play();

		if (myInputProcessor.getOutString().contains("A")) {
			dir = -1;
			//x--;
			tmpA = run;
		};
		if (myInputProcessor.getOutString().contains("D")){
			dir = 1;
			//x++;
			tmpA = run;
		}
		if (myInputProcessor.getOutString().contains("W")) {
			y++;
			tmpA = run;
		}
		if (myInputProcessor.getOutString().contains("S")) {
			y--;
			tmpA = run;
		}
		if (myInputProcessor.getOutString().contains("Space")) {
			x= Gdx.graphics.getWidth()/2 ;
			y= Gdx.graphics.getHeight()/2 ;

		}

		if (dir == -1) x-= step;
		if (dir == 1) x+= step;

		TextureRegion tmp = tmpA.draw();
		if (!tmpA.draw().isFlipX() & dir == -1) tmpA.draw().flip(true,false);
		if (tmpA.draw().isFlipX() & dir == 1) tmpA.draw().flip(true,false);

		tmpA.setTime(Gdx.graphics.getDeltaTime());
		//System.out.println(myInputProcessor.getOutString());

		batch.begin();
		batch.draw(tmpA.draw(), x, y);
		batch.end();
	}


	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		run.dispose();
		stand.dispose();
		music.dispose();
		sound.dispose();
	}
}
