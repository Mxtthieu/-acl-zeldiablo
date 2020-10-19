package com.zeldiablo.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zeldiablo.controllers.KeyboardListener;
import com.zeldiablo.factories.TextureFactory;
import com.zeldiablo.models.GameState;
import com.zeldiablo.models.GameWorld;
import com.zeldiablo.models.enums.State;

public class GameScreen extends ScreenAdapter {

    private SpriteBatch gameBatch;
    private GameState gameState;
    private GameWorld gameWorld;
    private KeyboardListener kl;
    private OrthographicCamera orthographicCamera;

    /**
     * GameScreen servira pour la boucle principale ainsi que le rendu à l'écran
     */
    public GameScreen() {
        this.kl = new KeyboardListener();

        InputMultiplexer listeners = new InputMultiplexer();
        listeners.addProcessor(kl);

        Gdx.input.setInputProcessor(listeners);

        this.gameState = new GameState();
        this.gameWorld = new GameWorld();

        this.gameBatch = new SpriteBatch();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(gameState.isPaused()){
            this.drawPause();
        }

        this.update();
    }


    private void update() {
        if(this.kl.isPaused()){
            this.gameState.setState(State.PAUSED);
        } else {
            this.gameState.setState(State.IN_PROGRESS);
        }
    }

    private void drawPause() {
        gameBatch.begin();
        gameBatch.draw(TextureFactory.INSTANCE.getPause(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        gameBatch.end();
    }


}
