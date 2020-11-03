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
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.zeldiablo.controllers.KeyboardListener;
import com.zeldiablo.controllers.MouseListener;
import com.zeldiablo.models.Player;
import com.zeldiablo.models.Portal;

import java.util.Random;

public class Zeldiablo extends Game {
	SpriteBatch batch;
	Box2DDebugRenderer debug;
	World world;
	Player player;

	boolean tp = false;
	Portal por = null;

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
		Portal p4 = new Portal(1, new Vector2(600, 300),world);
		Portal p3 = new Portal(1, new Vector2(300, 40),world);
		Portal p2 = new Portal(1, new Vector2(500, 600),world);
		Portal p1 = new Portal(1, new Vector2(200, 200),world);
		p2.setExitPortal(p1);
		p1.setExitPortal(p2);
		p3.setExitPortal(p4);
		p4.setExitPortal(p3);

		createCollisionListener();
		createTestBody();

		InputMultiplexer multi = new InputMultiplexer();
		multi.addProcessor(keyboard);
		multi.addProcessor(mouse);
		Gdx.input.setInputProcessor(multi);

	}

	@Override
	public void render () {

		world.step(1f/60f, 6, 2);
		Gdx.gl.glClearColor(0, 0, 0, 1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		//createCollisionListener();

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

		// Temporaire
		if (tp){
			// Je rend le portail de sortie inactif pour eviter de teleporter en boucle
			this.por.setExitPortalActif(false);
			// Je teleporte le joueur a la position du portail de sortie.
			this.player.getBody().setTransform(this.por.getPosPortalExit().x,this.por.getPosPortalExit().y,0f);
			tp = false;
		}
		// Fin Temporaire

		batch.begin();
		debug.render(world, camera.combined);
		batch.end();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}

	/**
	 * Fonction pour la version de développement permettant de générer facilmenet des body statique
	 */
	private void createTestBody() {
		Random rand = new Random();

		float[] pos = {15f, 15f, 15f, 705f, 1009f, 705f, 1009f, 15f, 15f, 15f};
		BodyDef bd = new BodyDef();
		bd.type = BodyDef.BodyType.StaticBody;
		bd.position.set(0, 0);

		Body body = world.createBody(bd);
		FixtureDef fixture = new FixtureDef();
		ChainShape shape = new ChainShape();
		shape.createChain(pos);
		fixture.shape = shape;
		fixture.isSensor = false;

		body.setUserData("M");
		body.createFixture(fixture);
		shape.dispose();
	}

	/***
	 * Fonction pour mettre en place une gestion de collision
	 ***/

	private void createCollisionListener() {

		world.setContactListener(new ContactListener() {

			@Override
			public void beginContact(Contact contact) {

				Object obj;
				// je regarde ici si l'hors d'un contact entre 2 bodys si l'un des deux est le personnage
				if(contact.getFixtureA().getBody() == getPlayer().getBody()){
					obj = contact.getFixtureB().getBody().getUserData();
				}else{
					obj = contact.getFixtureA().getBody().getUserData();
				}

				if(obj != null){
					// Si l'objet en contact avec le personnage est un portail alors je teleporte le personnage
					if(obj.getClass().getSimpleName().equals("Portal")) {
						Portal por = ((Portal) obj);
						teleport(getPlayer(), por);
					}

				}

			}

			@Override
			public void endContact(Contact contact) {


				Object obj;
				// je regarde ici si l'hors d'un contact entre 2 bodys si l'un des deux est le personnage
				if(contact.getFixtureA().getBody() == getPlayer().getBody()){
					obj = contact.getFixtureB().getBody().getUserData();
				}else{
					obj = contact.getFixtureA().getBody().getUserData();
				}

				if(obj != null){
					// Si l'objet en contact avec le personnage est un portail alors je met le portail en actif
					if(obj.getClass().getSimpleName().equals("Portal")) {
						Portal por = ((Portal) obj);
						por.setActif(true);

					}

				}
			}

			@Override
			public void preSolve(Contact contact, Manifold oldManifold) {

			}
			@Override
			public void postSolve(Contact contact, ContactImpulse impulse) {
			}

		});
	}

	private Player getPlayer(){
		return this.player;
	}

	private void teleport(Player p, Portal por){
		//Si le portail est actif je peux teleporter
		if(por.isActif()) {
			// Si le portail de sortie est dans le meme labyrinthe on teleporte le joueur
			if (por.exitSameMaze()) {
				/*
				A remplir ....
				*/

				// Temporaire
				this.por = por;
				this.tp = true;
			} else {
				// Si le portail de sortie n'est pas situer dans le meme labirynthe on charge le nouveau labirynthe et on teleporte le joueur

			}
		}
	}
}

