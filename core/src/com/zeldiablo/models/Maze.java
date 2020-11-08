package com.zeldiablo.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.zeldiablo.models.enums.MazeObjects;
import com.zeldiablo.models.piege.Piege;
import com.zeldiablo.models.piege.PiegeDegat;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Maze {

    private GameWorld gameWorld;

    private File mazeFile;

    private ArrayList<Body> wallList;
    private ArrayList<Piege> trapList;
    private ArrayList<Portal> portalList;

    private int currentNumMaze;

    public Maze(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        this.wallList = new ArrayList<>();
        this.trapList = new ArrayList<>();
        this.portalList = new ArrayList<>();
        this.currentNumMaze = 0;
        loadMaze(1);
    }

    /**
     * La fonction loadMaze permet de charger un labyrinthe avec un fichier
     */
    public void loadMaze(int num) {

        World world = gameWorld.getWorld();
        for (Body wall: wallList){
            world.destroyBody(wall);
        }
        wallList.clear();
        this.currentNumMaze = num;
        this.mazeFile = new File("./core/assets/maze/Maze" + this.currentNumMaze);
        readObjects();

    }

    /**
     * La fonction readObjects lit et place les différents objets du fichier dans le monde
     * (piege, mur, etc..)
     */
    public void readObjects() {
        try {
            FileReader fr = new FileReader(this.mazeFile);

            BufferedReader bufferedReader = new BufferedReader(fr);

            String c;
            int line = 0;
            boolean normalRead = true;
            boolean portalRead = false;

            while ((c = bufferedReader.readLine()) != null) {

                if(c.equals("Portals Links:")){
                    normalRead = false;
                    portalRead = true;
                }
                if(c.equals("FIN")){
                    normalRead = true;
                    portalRead = false;
                }

                if (normalRead){

                    for (int column = 0; column < c.length(); column++) {
                        switch (c.charAt(column)) {
                            case 'W':
                                addWall(line, column);
                                break;
                            case 'T':
                                addTrap(line, column);
                                break;
                            case 'P':
                                addPortal(line, column);
                                break;
                            default:
                                break;
                        }
                    }
                    line++;
                }

                if(portalRead){
                    String[] a;
                    String[] p1;
                    String[] p2;
                    Portal por1;
                    Portal por2;
                    if(!c.equals("Portals Links:") && !c.equals("Fin")) {
                        a = c.split("\\|");
                        p1 = a[1].split(",");
                        p2 = a[2].split(",");
                        por1 = new Portal(this.currentNumMaze,Integer.valueOf(p1[2]),new Vector2(Integer.valueOf(p1[0]) +1,GameWorld.HEIGHT -Integer.valueOf(p1[1])), gameWorld.getWorld());
                        por2 = new Portal(this.currentNumMaze,Integer.valueOf(p2[2]),new Vector2(Integer.valueOf(p2[0]) +1,GameWorld.HEIGHT -Integer.valueOf(p2[1])), gameWorld.getWorld());
                        por1.setExitPortal(por2);
                        por2.setExitPortal(por1);
                        this.portalList.add(por1);
                        this.portalList.add(por2);
                    }
                }
            }
            bufferedReader.close();

        } catch (IOException e) {
            System.err.println("Unable to read the file.");
        }


    }

    /**
     * Ajoute un portail au monde
     * @param i la ligne dans le fichier
     * @param j la colonne dans le fichier
     */
    private void addPortal(int i, int j) {
        World world = gameWorld.getWorld();
        this.portalList.add(new Portal(this.currentNumMaze,this.currentNumMaze,new Vector2(j+1,GameWorld.HEIGHT - (i+1)), world));

    }

    /**
     * Ajoute un piege au monde
     * @param i la ligne dans le fichier
     * @param j la colonne dans le fichier
     */
    private void addTrap(int i, int j) {
        World world = gameWorld.getWorld();
        this.trapList.add(new PiegeDegat(new Vector2(j+1,GameWorld.HEIGHT - (i+1)), world));
    }

    /**
     * Ajoute un mur dans le monde
     * @param i la ligne dans le fichier
     * @param j la colonne dans le fichier
     */
    public void addWall(int i, int j) {

        World world = gameWorld.getWorld();

        Vector2 positionMur = new Vector2(j+1,GameWorld.HEIGHT - (i+1));

        BodyDef bodyDef1 = new BodyDef();
        bodyDef1.type = BodyDef.BodyType.StaticBody;
        bodyDef1.position.set(positionMur);

        Body body = world.createBody(bodyDef1);

        CircleShape shape = new CircleShape();
        shape.setRadius(1/90f * GameWorld.WIDTH);
        shape.setPosition(new Vector2(0, 0));

        FixtureDef fixtureDef1 = new FixtureDef();
        fixtureDef1.shape = shape;
        fixtureDef1.density = 0f; //Densité
        fixtureDef1.restitution = 0f; //Elasticité
        fixtureDef1.friction = 1f; //Friction
        body.createFixture(fixtureDef1);
        body.setUserData("W");

        this.wallList.add(body);

    }

    public void draw(SpriteBatch batch) {
        for(Portal p :portalList){
            if(p.getNumMaze() == this.currentNumMaze) {
                p.draw(batch);
            }
        }
        for(Piege p :trapList){
            p.draw(batch);
        }

    }
}
