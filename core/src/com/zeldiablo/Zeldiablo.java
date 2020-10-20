package com.zeldiablo;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.zeldiablo.controllers.KeyboardListener;
import com.zeldiablo.controllers.MouseListener;
import com.zeldiablo.models.Player;

public class Zeldiablo extends Game {
	SpriteBatch batch;
	Box2DDebugRenderer debug;
	World world;
	Player player;

	float width = 1024;
	float height = 720;

	OrthographicCamera camera = new OrthographicCamera();
	FitViewport vp = new FitViewport(width, height, camera);

	KeyboardListener keyboard = new KeyboardListener();
	MouseListener mouse = new MouseListener();
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		debug = new Box2DDebugRenderer();

		vp.apply();
		camera.position.set(camera.viewportWidth/2, camera.viewportHeight/2, 0);
		camera.update();
		batch.setProjectionMatrix(camera.combined);

		world = new World(new Vector2(0, 0), true);
		player = new Player(world, "Pedro");

		InputMultiplexer multi = new InputMultiplexer();
		multi.addProcessor(keyboard);
		multi.addProcessor(mouse);
		Gdx.input.setInputProcessor(multi);
	}

	@Override
	public void render () {
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		Vector2 step = keyboard.getStep();

		float xP = this.player.getPosition().x;
		float yP = this.player.getPosition().y;

		float xM = this.mouse.getX();
		float yM = this.mouse.getY();

		float x = xM - xP;
		float y = yM - yP;

		float angle = (float) Math.atan(y/x);

		if (x < 0)
			angle += Math.PI;

		this.player.move(step.x, step.y, angle);

		batch.begin();
		debug.render(world, camera.combined);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}
