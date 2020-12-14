package com.zeldiablo.views;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class MainMenu extends ScreenAdapter {

    private SpriteBatch batch;
    protected Stage stage;
    private Viewport viewport;
    private OrthographicCamera camera;
    private TextureAtlas atlas;
    private Skin skin;
    private Sprite title;

    private int yPos;

    public MainMenu() {
        // L'atlas et le skin vont permettre de créer, modifier et d'afficher les éléments (les boutons) plus facilement
        this.atlas = new TextureAtlas("images/buttons/main_menu_buttons.atlas");
        this.skin = new Skin(Gdx.files.internal("skin/uiskin.json"), this.atlas);
        this.title = new Sprite(new Texture(Gdx.files.internal("images/title.png")));

        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera();
        this.viewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight(), this.camera);
        this.viewport.apply();

        this.camera.position.set(this.camera.viewportWidth/2, this.camera.viewportHeight/2, 0);
        this.camera.update();

        this.stage = new Stage(this.viewport, this.batch);
        this.yPos = Gdx.graphics.getHeight();       // Va servire a mémoriser la hauteur du titre
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

        // On place le titre en haut de l'écran
        this.title.setPosition(Gdx.graphics.getWidth()/2 - this.title.getWidth()/2, Gdx.graphics.getHeight());

        // Deux boutons qui vont permettre de jouer ou de quitter l'application
        TextButton editorButton = new TextButton("Editeur", this.skin, "silver_button");
        TextButton playButton = new TextButton("Jouer", this.skin, "silver_button");
        TextButton exitButton = new TextButton("Quitter", this.skin, "red_button");

        // On ajoute a chaque bouton son action
        editorButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                String editorJar = Gdx.files.getLocalStoragePath() + "/editor.jar";
                try {
                    runProcess("java -jar " + editorJar);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
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
        mainTable.add(editorButton).width(200).height(50).pad(5);
        mainTable.row();// Permet de sauter une ligne
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

        // on fait descendre le titre du jeu jusqu'à arriver au centre
        if (this.yPos >= Gdx.graphics.getHeight()/2) {
            this.yPos--;
            this.title.setY(this.yPos);
        }

        // On récupère le Batch utilisé par le menu et on déssine le titre dessus
        Batch batch = this.stage.getBatch();
        batch.begin();
        this.title.draw(batch);
        batch.end();

        this.stage.act();   // On actualise
        this.stage.draw();  // et on déssine
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

    private static void printLines(String cmd, InputStream ins) throws Exception {
        String line = null;
        try (BufferedReader in = new BufferedReader(
                new InputStreamReader(ins))) {
            while ((line = in.readLine()) != null) {
                System.out.println(cmd + " " + line);
            }
        }
    }

    private static void runProcess(String command) throws Exception {
        Process pro = Runtime.getRuntime().exec(command);
        printLines(command + " stdout:", pro.getInputStream());
        printLines(command + " stderr:", pro.getErrorStream());
        pro.waitFor();
        System.out.println(command + " exitValue() " + pro.exitValue());
    }
}
