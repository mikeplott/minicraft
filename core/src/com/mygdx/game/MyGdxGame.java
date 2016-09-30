package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import javafx.scene.text.Text;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	float x, y, xv, yv;
	TextureRegion down;
	TextureRegion up;
	TextureRegion right;
	TextureRegion left;
	boolean goRight;
	boolean goUp;

	static final int WIDTH = 16;
	static final int HEIGHT = 16;
	static final int DRAW_WIDTH = WIDTH * 3;
	static final int DRAW_HEIGHT = HEIGHT * 3;
	static final float MAX_VELOCITY = 100;
	static final float FRICTION = 0.8f;

	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("tiles.png");
		Texture tiles = new Texture("tiles.png");
		TextureRegion[][] grid = TextureRegion.split(tiles, WIDTH, HEIGHT);
		down = grid[6][0];
		up = grid[6][1];
		right = grid[6][3];
		left = new TextureRegion(right);
		left.flip(true, false);
	}

	@Override
	public void render () {
		movement();
		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		TextureRegion player;

		batch.begin();
		batch.draw(player, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	public void movement() {
		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			yv = MAX_VELOCITY * 4;
		}
		else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			yv = MAX_VELOCITY * -4;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			xv = MAX_VELOCITY * -4;
			goRight = false;
		}
		else if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			xv = MAX_VELOCITY * 4;
			goRight = true;
		}

		x += xv * Gdx.graphics.getDeltaTime();
		y += yv * Gdx.graphics.getDeltaTime();

		yv = decelerate(yv);
		xv = decelerate(xv);
	}

	public float decelerate (float velocity) {
		velocity *= FRICTION;
		if (Math.abs(velocity) < 50) {
			velocity = 0;
		}
		return velocity;
	}
}
