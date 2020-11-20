package com.zeldiablo.views;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import com.zeldiablo.controllers.CollisionListener;
import com.zeldiablo.controllers.KeyboardListener;
import com.zeldiablo.controllers.MouseListener;
import com.zeldiablo.factories.TextureFactory;
import com.zeldiablo.models.GameState;
import com.zeldiablo.models.GameWorld;
import com.zeldiablo.models.Maze;
import com.zeldiablo.models.Player;
import com.zeldiablo.models.enums.State;
import com.zeldiablo.models.traps.Projectile;
import com.zeldiablo.models.traps.Trap;
import com.zeldiablo.models.traps.TrapDamage;

public class GameScreen extends ScreenAdapter {

    // Ces deux attributs permettent de déterminer la vitesse du jeu
    static final float STEP_TIME = 1f/45f;
    private float accumulator = 0;
    private float angle;

    private SpriteBatch batch;              // Ensemble de sprites contenu par le jeu
    private Box2DDebugRenderer debug;       // Rendu de debug pour vérifier le placement des corps du jeu

    private GameWorld game;                 // Instance du jeu
    private GameState gameState;            // Instance de l'état du jeu
    private KeyboardListener keyboard;      // Controleur du clavier
    private MouseListener mouse;            // Controleur de la souris
    private CollisionListener collision;    // Controleur de la collision

    private OrthographicCamera camera;      // La caméra du jeu

    public GameScreen() {
        this.batch = new SpriteBatch();
        this.debug = new Box2DDebugRenderer();

        // --- Instanciation des modèles --- //
        this.gameState = new GameState();
        this.game = new GameWorld(this, this.gameState);
        this.keyboard = new KeyboardListener();
        this.mouse = new MouseListener();
        this.collision = new CollisionListener(this.game);

        // --- Ajout des controleurs au jeu --- //
        InputMultiplexer multi = new InputMultiplexer();        // Permet d'ajouter plusieurs écouteurs au jeu
        multi.addProcessor(this.keyboard);
        multi.addProcessor(this.mouse);
        Gdx.input.setInputProcessor(multi);                      // Ajout de la liste d'écouteurs
        this.game.getWorld().setContactListener(this.collision); // Ajout des effects de collision au monde
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        if(this.gameState.isReset()){
            this.reset();
            this.gameState.setState(State.IN_PROGRESS);
        }

        // --- Gestion de la pause --- //
        Trap t;
        if(this.keyboard.isPaused()){
            this.gameState.setState(State.PAUSED);
            this.drawPause();
            for(Body b :this.game.getBodies()){
                if(b.getUserData() instanceof Trap){
                    t = (Trap)b.getUserData();
                    t.pause();
                }
            }
        } else {
            this.gameState.setState(State.IN_PROGRESS);
            for(Body b :this.game.getBodies()){
                if(b.getUserData() instanceof Trap){
                    t = (Trap)b.getUserData();
                    t.play();
                }
            }
        }

        if (this.keyboard.isReset()) {
            this.gameState.setState(State.RESET);
            this.keyboard.setReset(false);
        }

        if (this.keyboard.isAttack()){
            this.game.atk();
        }

        if (this.keyboard.isDebug()) {

            this.game.draw(this.batch);
            batch.begin();
            debug.render(this.game.getWorld(), camera.combined);
            batch.end();
        } else {
            if(!this.gameState.isLoading()){
                this.game.draw(this.batch);
            }
        }

        if(!this.gameState.isPaused()){
            this.update();
        }
    }

    private void reset() {
        this.game.loadMaze(1, 10,10);
    }

    /**
     * Méthode privée. Permet de mettre à jour tous les modèles selon les actions des controleurs.
     */
    private void update() {

        // --- Fin de Gestion --- //

        // --- Gestion du déplacement et de la rotation du personnage dans le jeu --- //
        Vector2 step = keyboard.getStep();
        Player p = this.game.getPlayer();

            // Récupération des coordonées du joueur
        float xP = p.getPosition().x;
        float yP = p.getPosition().y;

            // Récupération des coordonées de la souris
        float xM = GameWorld.WIDTH * this.mouse.getX() / Gdx.graphics.getWidth();
        float yM = GameWorld.HEIGHT * this.mouse.getY() / Gdx.graphics.getHeight();

            // Calcule de la distance entre les deux
        float x = xM - xP;
        float y = yM - yP;

        // Un peu de magie (et de la trigo) et on obtient l'angle
        this.angle = (float) Math.atan(y/x);
        if (x < 0)
            angle += Math.PI;

        p.move(step.x, step.y, angle);
        // --- Fin de la gestion --- //

        // --- Gestion de la téléportation ---//
        if(this.collision.isTp()){
            game.teleport(p,this.collision.getPortal());
            this.collision.setTp(false);
        }

        // --- Destruction des bodies --- //
        this.game.deleteBodies();

        // --- Fin de Gestion ---//
        this.stepWorld();

    }

    /**
     * Méthode privée. Permet le calcule et l'application d'un temps d'attente entre deux frames. Cela permet au
     * jeu de garder une certaine fréquence d'image.
     */
    private void stepWorld() {
        float delta = Gdx.graphics.getDeltaTime();
        accumulator += Math.min(delta, 0.25f);

        while (accumulator >= STEP_TIME) {
            accumulator -= STEP_TIME;
            this.game.getWorld().step(STEP_TIME, 6, 2);
        }
    }

    /**
     * Appelé lorsque la fenêtre du jeu est redimensionnée
     * @param width nouvelle largeur
     * @param height nouvelle hauteur
     */
    @Override
    public void resize(int width, int height) {
        // --- Définition de la caméra du jeu --- //
        this.camera = new OrthographicCamera((float)GameWorld.WIDTH,GameWorld.HEIGHT);
        this.camera.position.set((float)GameWorld.WIDTH / 2, (float) GameWorld.HEIGHT / 2, 0);
        this.camera.update();
        this.batch.setProjectionMatrix(camera.combined);
    }

    @Override
    public void dispose() {
        this.batch.dispose();
    }


    private void drawPause() {
            batch.begin();
            batch.draw(TextureFactory.INSTANCE.getPause(), 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
            batch.end();
    }


    public float getAngle() {
        return angle;
    }

    public MouseListener getMouse() {
        return mouse;
    }
}
