package com.mygdx.game;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.*;

public class MyGdxGame extends ApplicationAdapter {
	SpriteBatch batch;
	Texture img;
	float x, y, xv, yv, totalTime, randomX, randomY;
	//float gameWidth = Gdx.graphics.getWidth();
	//float gameHeight = Gdx.graphics.getHeight();
	TextureRegion down;
	TextureRegion up;
	TextureRegion right;
	TextureRegion left;
	TextureRegion stand;
	TextureRegion grass;
	TextureRegion upStep;
	TextureRegion downStep;
	TextureRegion player;
	TextureRegion zombieRight;
	TextureRegion zombieLeft;
	TextureRegion zombieUp;
	TextureRegion zombieDown;
	TextureRegion zombieStand;
	Animation walkRight;
	Animation walkLeft;
	Animation walkUp;
	Animation walkDown;
	boolean goRight = true;
	boolean goLeft = false;
	boolean goUp = false;
	boolean goDown = false;
	boolean isSprinting = false;
	static final int WIDTH = 16;
	static final int HEIGHT = 16;
	static final int DRAW_WIDTH = WIDTH * 3;
	static final int DRAW_HEIGHT = HEIGHT * 3;
	static final float MAX_VELOCITY = 100f;
	static final float FRICTION = 0.8f;
	//static final float MAX_WIDTH = Gdx.graphics.getWidth();
	//static final float MAX_HEIGHT = Gdx.graphics.getHeight();
	static final float RANDOMX = (float) Math.random() * 600;
	static final float RANDOMY = (float) Math.random() * 430;

//	randomX = (float) Math.random() * 100;
//	randomY = (float) Math.random() * 100;



	
	@Override
	public void create () {
		batch = new SpriteBatch();
		img = new Texture("tiles.png");
		Texture tiles = new Texture("tiles.png");
		TextureRegion[][] grid = TextureRegion.split(tiles, WIDTH, HEIGHT);
		TextureRegion[][] grassGrid = TextureRegion.split(tiles, 30, 8);
		zombieRight = grid[6][7];
		zombieStand = grid[6][6];
		zombieDown = grid[6][4];
		zombieUp = grid[6][5];
		zombieLeft = new TextureRegion(zombieRight);
		grass = grassGrid[0][0];
		down = grid[6][0];
		up = grid[6][1];
		right = grid[6][3];
		left = new TextureRegion(right);
		stand = grid[6][2];
		upStep = new TextureRegion(up);
		upStep.flip(true, false);
		downStep = new TextureRegion(down);
		downStep.flip(true, false);
		walkRight = new Animation(0.1f, stand, right);
		walkLeft = new Animation(0.1f, stand, left);
		walkUp = new Animation(0.1f, upStep, up);
		walkDown = new Animation(0.1f, downStep, down);
	}

	@Override
	public void render () {
		totalTime += Gdx.graphics.getDeltaTime();
		movement();
		Gdx.gl.glClearColor(0, 1, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.begin();
		renderBackground();

		if (RANDOMX >= Gdx.graphics.getWidth() || RANDOMY >= Gdx.graphics.getHeight()) {
			randomX = Gdx.graphics.getWidth();
			randomY = Gdx.graphics.getHeight();
			batch.draw(zombieStand, randomX, randomY, DRAW_WIDTH, DRAW_HEIGHT);
		}
		else {
			randomX = RANDOMX;
			randomY = RANDOMY;
		}


		 // Code that uses the animations that were created from the sprite sheet. I tried to refactor this massive beast
		 // into its own method but had a hard time with the correct arguements and return values i would have to return
		 // and left it in here since it called the batch.draw() method.

		if (goRight && xv == 0) {
			player = right;
			batch.draw(player, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}
		else if (goRight && xv > 0) {
			player = walkRight.getKeyFrame(totalTime, true);
			batch.draw(player, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}
		else if (goLeft && xv == 0) {
			player = left;
			batch.draw(player, x + DRAW_WIDTH, y, DRAW_WIDTH * -1, DRAW_HEIGHT);
		}
		else if (goLeft && xv < 0) {
			player = walkLeft.getKeyFrame(totalTime, true);
			batch.draw(player, x + DRAW_WIDTH, y, DRAW_WIDTH * -1, DRAW_HEIGHT);
		}
		else if (goUp && yv == 0) {
			player = up;
			batch.draw(player, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}
		else if (goUp && yv > 0) {
			player = walkUp.getKeyFrame(totalTime, true);
			batch.draw(player, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}
		else if (goDown && yv == 0) {
			player = down;
			batch.draw(player, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}
		else if (goDown && yv < 0) {
			player = walkDown.getKeyFrame(totalTime, true);
			batch.draw(player, x, y, DRAW_WIDTH, DRAW_HEIGHT);
		}

//		if (goRight && xv == 0) {
//			player = right;
//			batch.draw(player, x, y, DRAW_WIDTH, DRAW_HEIGHT);
//		}
//		else if (goRight && xv > 0) {
//			player = walkRight.getKeyFrame(totalTime, true);
//			batch.draw(player, x, y, DRAW_WIDTH, DRAW_HEIGHT);
//		}
//		else if (goLeft && xv == 0) {
//			player = left;
//			batch.draw(player, x + DRAW_WIDTH, y, DRAW_WIDTH * -1, DRAW_HEIGHT);
//		}
//		else if (goLeft && xv < 0) {
//			player = walkLeft.getKeyFrame(totalTime, true);
//			batch.draw(player, x + DRAW_WIDTH, y, DRAW_WIDTH * -1, DRAW_HEIGHT);
//		}
//		else if (goUp && yv == 0) {
//			player = up;
//			batch.draw(player, x, y, DRAW_WIDTH, DRAW_HEIGHT);
//		}
//		else if (goUp && yv > 0) {
//			player = walkUp.getKeyFrame(totalTime, true);
//			batch.draw(player, x, y, DRAW_WIDTH, DRAW_HEIGHT);
//		}
//		else if (goDown && yv == 0) {
//			player = down;
//			batch.draw(player, x, y, DRAW_WIDTH, DRAW_HEIGHT);
//		}
//		else if (goDown && yv < 0) {
//			player = walkDown.getKeyFrame(totalTime, true);
//			batch.draw(player, x, y, DRAW_WIDTH, DRAW_HEIGHT);
//		}
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
		img.dispose();
	}

	public void enemyMovement() {

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
		x += xv * Gdx.graphics.getDeltaTime();
		y += yv * Gdx.graphics.getDeltaTime();
		if (x < -30) {
			x = Gdx.graphics.getWidth();
		}
		if (x > Gdx.graphics.getWidth()) {
			x = -30;
		}
		if (y < -30) {
			y = Gdx.graphics.getHeight();
		}
		if (y > Gdx.graphics.getHeight()) {
			y = -30;
		}
		yv = decelerate(yv);
		xv = decelerate(xv);
	}


		// I had a hard time replicating what we did in class so i worked around that by assigning boolean values to each
		// possible keystroke. This way i have an absolute control on what can and will happen when a key is pressed.

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
		x += xv * Gdx.graphics.getDeltaTime();
		y += yv * Gdx.graphics.getDeltaTime();
		if (x < -30) {
			x = Gdx.graphics.getWidth();
		}
		if (x > Gdx.graphics.getWidth()) {
			x = -30;
		}
		if (y < -30) {
			y = Gdx.graphics.getHeight();
		}
		if (y > Gdx.graphics.getHeight()) {
			y = -30;
		}
		yv = decelerate(yv);
		xv = decelerate(xv);
	}

		// method to limit character movement. without it the character would keep moving. taken from super koalio in class

	public float decelerate (float velocity) {
		velocity *= FRICTION;
		if (Math.abs(velocity) < 50) {
			velocity = 0;
		}
		return velocity;
	}

		// I've seen a few different examples of how to make a texture repeat across the screen to create a background
		// in this instance a grass sprite however, I failed to replicate it so I hard coded it to fill the screen.

	public void renderBackground() {
		batch.draw(grass, 0, 0, DRAW_WIDTH * 5, DRAW_HEIGHT * 2);
		batch.draw(grass, 0, 95, DRAW_WIDTH * 5, DRAW_HEIGHT * 2);
		batch.draw(grass, 0, 190, DRAW_WIDTH * 5, DRAW_HEIGHT * 2);
		batch.draw(grass, 0, 275, DRAW_WIDTH * 5, DRAW_HEIGHT * 2);
		batch.draw(grass, 0, 360, DRAW_WIDTH * 5, DRAW_HEIGHT * 3);
		batch.draw(grass, 240, 0, DRAW_WIDTH * 5, DRAW_HEIGHT * 2);
		batch.draw(grass, 240, 95, DRAW_WIDTH * 5, DRAW_HEIGHT * 2);
		batch.draw(grass, 240, 190, DRAW_WIDTH * 5, DRAW_HEIGHT * 2);
		batch.draw(grass, 240, 275, DRAW_WIDTH * 5, DRAW_HEIGHT * 2);
		batch.draw(grass, 240, 360, DRAW_WIDTH * 5, DRAW_HEIGHT * 3);
		batch.draw(grass, 480, 0, DRAW_WIDTH * 5, DRAW_HEIGHT * 2);
		batch.draw(grass, 480, 95, DRAW_WIDTH * 5, DRAW_HEIGHT * 2);
		batch.draw(grass, 480, 190, DRAW_WIDTH * 5, DRAW_HEIGHT * 2);
		batch.draw(grass, 480, 275, DRAW_WIDTH * 5, DRAW_HEIGHT * 2);
		batch.draw(grass, 480, 360, DRAW_WIDTH * 5, DRAW_HEIGHT * 3);
	}
}











		// was trying to make the grass sprite fill by rendering with using a for loop to increment x, y position

//		for (int i = 0; i < maxWidth && i < madHeight; i++) {
//			bgX = i;
//			bgY = i;
//		}
//		for (int j = 0; j < madHeight; j++) {
//			bgY += 8;
//		}
//batch.draw(grass, bgX, bgY, DRAW_WIDTH * 2, DRAW_HEIGHT);

		// found a snippet with documentation on the site cited below. could not replicate the results.

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
//

		// first try at creating a window boundary to make the player appear on the opposite side if passed
		// the window boundary.

//		if (x > MAX_WIDTH) {
//			x = 0;
//		}
//		if (x < 0) {
//			x = MAX_WIDTH;
//		}
//		if (x > MAX_WIDTH) {
//			xBoundary = true;
//		}
//		if (x < 0) {
//			xBoundary = false;
//		}
//			if (yv < 0) {
//				yv =
//			}

		// first attempt and manipulating sprites

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

		// another attempt of trying to render a sprite as the background

//background = new TextureRegion(grass, 0, 0, MAX_WIDTH, MAX_HEIGHT);
//SpriteBatch spriteBatch = new SpriteBatch();
//batch.draw(grass,0,0,MAX_WIDTH,MAX_HEIGHT);
//batch.draw(grass, x, y, MAX_WIDTH, MAX_HEIGHT);
//static final int MAX_WIDTH = Gdx.graphics.getWidth();
//static final int MAX_HEIGHT = Gdx.graphics.getHeight();

//static final float MAX_WIDTH = Gdx.graphics.getWidth();
//static final float MAX_HEIGHT = Gdx.graphics.getHeight();
//boolean xBoundary = false;
//boolean yBoundary = false;

//	Stage setting = new Stage();
//	Camera camera;
//	TextureAtlas.AtlasSprite atlas;
//	Texture background;
//left.flip(true, false);

