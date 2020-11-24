package com.zeldiablo.views;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g3d.utils.MeshBuilder;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.badlogic.gdx.video.VideoPlayer;
import com.badlogic.gdx.video.VideoPlayerCreator;

import java.io.FileNotFoundException;

public class MainMenu extends ScreenAdapter {

    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    private Skin skin;
    private VideoPlayer videoPlayer;

    public MainMenu() {
        // L'atlas et le skin vont permettre de créer, modifier et d'afficher les éléments (les boutons) plus facilement
        this.atlas = new TextureAtlas("images/buttons/main_menu_buttons.atlas");
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"), this.atlas);

        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), this.camera);
        this.viewport.apply();

        this.camera.position.set(this.camera.viewportWidth/2, this.camera.viewportHeight/2, 0);
        this.camera.update();

        this.stage = new Stage(this.viewport, this.batch);
        this.videoPlayer = VideoPlayerCreator.createVideoPlayer();
        this.videoPlayer.setLooping(true);
        try {
            this.videoPlayer.play(Gdx.files.internal("video/main_menu.mp4"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(this.stage);

        // Va permettre le positionnement des boutons dans la fenêtre
        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.bottom();

        // Deux boutons qui vont permettre de jouer ou de quitter l'application
        TextButton playButton = new TextButton("Jouer", this.skin, "silver_button");
        TextButton exitButton = new TextButton("Quitter", this.skin, "red_button");

        // On ajoute a chaque bouton son action
        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ((Game)Gdx.app.getApplicationListener()).setScreen(new GameScreen());
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.app.exit();
            }
        });

        // On ajoute les boutons à la table
        mainTable.add(playButton).width(200).height(50).pad(5);
        mainTable.row();// Permet de sauter une ligne
        mainTable.add(exitButton).width(200).height(50).pad(5).padBottom(50);

        // Et on ajoute la table à l'écran
        this.stage.addActor(mainTable);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(.1f, .12f, .16f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        this.stage.act();
        this.stage.draw();
    }

    /**
     * @param width
     * @param height
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        this.viewport.update(width, height);
        this.camera.position.set(this.camera.viewportWidth/2, this.camera.viewportHeight/2, 0);
        this.camera.update();
    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        this.skin.dispose();
        this.atlas.dispose();
    }
}
