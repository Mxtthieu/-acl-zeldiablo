package com.zeldiablo.models;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Timer;
import com.zeldiablo.factories.SoundFactory;
import com.zeldiablo.models.enums.State;
import com.zeldiablo.models.monsters.Monster;
import com.zeldiablo.models.portals.Portal;
import com.zeldiablo.models.traps.Projectile;
import com.zeldiablo.models.traps.Trap;
import com.zeldiablo.models.traps.TrapDamage;
import com.zeldiablo.models.treasure.Treasure;
import com.zeldiablo.views.GameScreen;

import java.util.ArrayList;

public class GameWorld {

    // --- Variables static qui définissent la taille du monde virtuel
    public static final int WIDTH = 80;
    public static final int HEIGHT = 60;

    // --- Eléments du jeu
    private GameScreen screen;
    private GameState gameState;
    private ArrayList<Body> bodiesToDelet;
    private ArrayList<Body> bodies;
    private World world;
    private Player player;
    private Maze maze;
    private int score;
    private Timer timer;
    protected Timer.Task timeTask;
    private int time;

    // --- Bitmap
    private BitmapFont bitmapFont;

    // --- Données Téleportation
    public boolean isTp;
    public Portal portal;

    public GameWorld(GameScreen s, GameState gameState) {
        this.screen = s;
        this.gameState = gameState;
        this.bodiesToDelet = new ArrayList<>();
        this.bodies = new ArrayList<>();
        this.world = new World(new Vector2(0, 0), true);
        this.player = new Player(this, "Tester");
        this.maze = new Maze(this);
        this.maze.initMonster();
        this.isTp = false;
        this.bitmapFont = new BitmapFont();
        this.score = 0;
        this.time = 0;
        this.timer = new Timer();
        this.timeTask = new Timer.Task() {
            @Override
            public void run() {
                time++;
            }
        };
        timer.scheduleTask(timeTask,1.0f,1.0f);

    }

    /**
     * Demande au modèle Player de s'afficher sur le jeu
     * @param batch ensemble de sprite
     */
    public void draw(SpriteBatch batch, SpriteBatch batchText) {
        this.maze.draw(batch, batchText);
        this.player.draw(batch, batchText);
        drawPlayerLife(batchText);
        drawScore(batchText);
        drawTime(batchText);
    }

    private void drawTime(SpriteBatch batchText) {
        batchText.begin();
        Label text;
        Label.LabelStyle textStyle;
        textStyle = new Label.LabelStyle();
        textStyle.font = this.bitmapFont;
        text = new Label(Integer.toString(this.time),textStyle);
        text.setFontScale(2f,2f);
        text.setPosition(30, Gdx.graphics.getHeight() - 50);
        text.draw(batchText, 1);
        batchText.end();
    }

    private void drawScore(SpriteBatch batchText) {
        batchText.begin();
        Label text;
        Label.LabelStyle textStyle;
        textStyle = new Label.LabelStyle();
        textStyle.font = this.bitmapFont;
        text = new Label("Score : " + this.score,textStyle);
        text.setFontScale(2f,2f);
        text.setPosition((Gdx.graphics.getWidth() / 2f) - text.getWidth(), Gdx.graphics.getHeight() - 50);
        text.draw(batchText, 1);
        batchText.end();
    }

    private void drawPlayerLife(SpriteBatch batch) {
        batch.begin();
        Label text;
        Label.LabelStyle textStyle;
        textStyle = new Label.LabelStyle();
        textStyle.font = this.bitmapFont;
        text = new Label(Integer.toString(this.player.getHP()),textStyle);
        text.setFontScale(2f,2f);
        text.setPosition(30, 30);
        text.draw(batch, 1);
        batch.end();
    }

    public World getWorld() {
        return this.world;
    }

    public Player getPlayer() {
        return this.player;
    }

    /***
     * Cette Fonction teleporte le joueur au portail de sortie par rapport au portail selectionner
     * @param p Player
     * @param por Portal
     */
    public void teleport(Player p, Portal por){
        //Si le portail est actif je peux teleporter
        if(por.isActif()) {
            SoundFactory.getInstance().portal.play();
            // Je rend le portail de sortie inactif pour eviter de teleporter en boucle
            por.exitPortalDelai();
            por.delai();
            // Je teleporte le joueur a la position du portail de sortie.
            p.getBody().setTransform(por.getPosPortalExit().x ,por.getPosPortalExit().y ,0f);
            // Si le portail de sortie n'est pas dans le meme labyrinthe on teleporte le joueur dans l'autre
            if (!por.exitSameMaze()) {
                loadMaze(por.getExitPortalNumMaze(),por.getPosPortalExit().x + ((p.getBody().getAngle()) * p.getRadius()*2),por.getPosPortalExit().y);
            }
        }
    }

    public void loadMaze(int num, float playerX, float playerY) {
        this.gameState.setState(State.LOADING);
        this.maze.resetMaze();
        this.world.destroyBody(this.player.getBody());
        this.player.createBody(playerX, playerY);
        this.maze.loadMaze(num);
        this.maze.initMonster();
        this.gameState.setState(State.IN_PROGRESS);
    }

    public void atk(){
        this.player.attack(screen.getAngle());
    }

    public void deleteBodies() {
        if(bodiesToDelet.size() > 0){
            world.destroyBody(bodiesToDelet.get(0));
            bodiesToDelet.clear();
        }
    }

    public void addBodyToDelete(Body body) {
        this.bodiesToDelet.add(body);
    }

    public void addBody(Body body) {
        this.bodies.add(body);
    }

    public ArrayList<Body> getBodies(){
        return bodies;
    }

    public GameState getGameState() {
        return gameState;
    }

    /**
     * Retourne une grille de booléen indiquant, pour chaque case, si elle est libre ou non.
     * @return grille de booléen
     */
    public boolean[][] generateGrid() {
        boolean[][] grid = new boolean[GameWorld.HEIGHT][GameWorld.WIDTH];
        for (int j = 0; j < GameWorld.HEIGHT; j++)
            for (int i = 0; i < GameWorld.WIDTH; i++)
                grid[j][i] = true;

        int x, y;
        ArrayList<Vector2> tmp = this.maze.getWallsCoord();
        for (Vector2 vec : tmp) {
            x = (int) vec.x;
            y = (int) vec.y;
            grid[y][x] = false;
        }
        return grid;
    }

    public void startTimer() {
        timer.start();
    }

    public void stopTimer(){
        timer.stop();
    }

    public void increaseScore(int score){
        this.score += score;
    }

    public void deleteTreasureMaze(Treasure treasure) {
        this.maze.deleteTreasure(treasure);
    }

    public void deleteMonsterMaze(Monster monster) {
        this.maze.deleteMonster(monster);
    }

    public void reset() {
        this.world = new World(new Vector2(0, 0), true);
        this.player = new Player(this, "Tester");
        this.maze = new Maze(this);
        this.maze.initMonster();
    }

    public Maze getMaze(){
        return this.maze;
    }

    public int getScore(){
        return this.score;
    }
}
