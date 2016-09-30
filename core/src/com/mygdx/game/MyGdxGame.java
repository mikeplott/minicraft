package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.scenes.scene2d.Stage;
import javafx.scene.text.Text;
import javafx.stage.Screen;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	float x, y, xv, yv, velocity;
	TextureRegion down;
	TextureRegion up;
	TextureRegion right;
	TextureRegion left;
	TextureRegion stand;
	TextureRegion grass;
	boolean goRight = true;
	boolean goLeft = false;
	boolean goUp = false;
	boolean goDown = false;
	boolean isSprinting = false;
	//boolean xBoundary = false;
	//boolean yBoundary = false;

	//	Stage setting = new Stage();
//	Camera camera;
//	TextureAtlas.AtlasSprite atlas;
//	Texture background;

	static final int WIDTH = 16;
	static final int HEIGHT = 16;
	static final int DRAW_WIDTH = WIDTH * 3;
	static final int DRAW_HEIGHT = HEIGHT * 3;
	static final float MAX_VELOCITY = 100f;
	static final float FRICTION = 0.8f;
	//static final int MAX_WIDTH = Gdx.graphics.getWidth();
	//static final int MAX_HEIGHT = Gdx.graphics.getHeight();

	//static final float MAX_WIDTH = Gdx.graphics.getWidth();
	//static final float MAX_HEIGHT = Gdx.graphics.getHeight();

	
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
		//left.flip(true, false);
		grass = grid[0][0];
		stand = grid[6][2];



		// test code from http://www.aurelienribon.com/blog/2012/06/tutorial-animated-grass-using-libgdx/
		//trying to draw grass using the sprite across the screen.
//		float screenW = Gdx.graphics.getWidth();
//		float screenH = Gdx.graphics.getHeight();
//		float w = 1;
//		float h = w * screenH / screenW;
//
//		camera = new OrthographicCamera(w, h);
//		batch = new SpriteBatch();
//
		//atlas = new TextureAtlas.AtlasSprite();



	}

	@Override
	public void render () {

		TextureRegion player;

		movement();
		 //background = new TextureRegion(grass, 0, 0, MAX_WIDTH, MAX_HEIGHT);
		//SpriteBatch spriteBatch = new SpriteBatch();
		//batch.draw(grass,0,0,MAX_WIDTH,MAX_HEIGHT);
		//batch.draw(grass, x, y, MAX_WIDTH, MAX_HEIGHT);

		Gdx.gl.glClearColor(1, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		batch.begin();


		if (goRight) {
			player = right;
			batch.draw(player, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}
		if (goLeft) {
			player = left;
			batch.draw(player, x + DRAW_WIDTH, y, DRAW_WIDTH * -1, DRAW_HEIGHT);
		}
		if (goUp) {
			player = up;
			batch.draw(player, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}

		if (goDown) {
			player = down;
			batch.draw(player, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}




		//batch.draw(grass, MAX_WIDTH, MAX_HEIGHT);
		//batch.draw(grass, MAX_WIDTH, MAX_HEIGHT);
//		if (goUp) {
//			player = up;
//			//batch.draw(player, x, y, DRAW_WIDTH, DRAW_HEIGHT);
//		}
//		else if (!goUp) {
//			player = down;
//			//batch.draw(player, x, y, DRAW_WIDTH, DRAW_HEIGHT * -1);
//		}
//		else if (goRight) {
//			player = right;
//			//batch.draw(player, x, y, DRAW_WIDTH, DRAW_HEIGHT);
//		}
//		else if (!goRight) {
//			player = left;
//			//batch.draw(player, x, y, DRAW_WIDTH * -1, DRAW_HEIGHT);
//		}
//		else {
//			player = right;
//			//batch.draw(player, x, y, DRAW_WIDTH, DRAW_HEIGHT);
//		}


		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	public void movement() {
		if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
			isSprinting = true;
		}
		else isSprinting = false;

		if (Gdx.input.isKeyPressed(Input.Keys.W)) {
			if (isSprinting) {
				yv = MAX_VELOCITY * 4;
			}
			else {
				yv = MAX_VELOCITY * 2;
			}
//			if (yv < 0) {
//				yv =
//			}
			goRight = false;
			goDown = false;
			goLeft = false;
			goUp = true;

		}
		if (Gdx.input.isKeyPressed(Input.Keys.S)) {
			if (isSprinting) {
				yv = MAX_VELOCITY * -4;
			}
			else {
				yv = MAX_VELOCITY * -2;
			}
			goLeft = false;
			goRight = false;
			goUp = false;
			goDown = true;
		}
		if (Gdx.input.isKeyPressed(Input.Keys.A)) {
			if (isSprinting) {
				xv = MAX_VELOCITY * -4;
			}
			else {
				xv = MAX_VELOCITY * -2;
			}
			goUp = false;
			goRight = false;
			goDown = false;
			goLeft = true;
		}
		if(Gdx.input.isKeyPressed(Input.Keys.D)) {
			if (isSprinting) {
				xv = MAX_VELOCITY * 4;
			}
			else {
				xv = MAX_VELOCITY * 2;

			}
			goLeft = false;
			goUp = false;
			goDown = false;
			goRight = true;
		}

//		if (x > MAX_WIDTH) {
//			xBoundary = true;
//		}
//		if (x < 0) {
//			xBoundary = false;
//		}



		x += xv * Gdx.graphics.getDeltaTime();
		y += yv * Gdx.graphics.getDeltaTime();
		if (x < 0) {
			x = Gdx.graphics.getWidth();
		}
		if (x > Gdx.graphics.getWidth()) {
			x = 0;
		}
		if (y < 0) {
			y = Gdx.graphics.getHeight();
		}
		if (y > Gdx.graphics.getHeight()) {
			y = 0;
		}
//
//		if (x > MAX_WIDTH) {
//			x = 0;
//		}
//		if (x < 0) {
//			x = MAX_WIDTH;
//		}

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
