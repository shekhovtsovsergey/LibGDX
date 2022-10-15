package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img, coinImg;
	MyAnim anim;


	@Override
	public void create () {
		batch = new SpriteBatch();
		coinImg = new Texture("g.png");
		anim = new MyAnim("g.png",1,8,15, Animation.PlayMode.LOOP);

	}

	@Override
	public void render () {
		ScreenUtils.clear(1, 1, 1, 1);

		anim.setTime(Gdx.graphics.getDeltaTime());
		float x = Gdx.input.getX() - anim.draw().getRegionWidth()/2.0f;
		float y = Gdx.graphics.getHeight() - Gdx.input.getY() - anim.draw().getRegionHeight()/2.0f;


		batch.begin();
		batch.draw(anim.draw(), x, y);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
		anim.dispose();
	}
}
