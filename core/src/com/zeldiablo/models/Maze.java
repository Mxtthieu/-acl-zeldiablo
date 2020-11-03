package com.zeldiablo.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.zeldiablo.models.enums.MazeObjects;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Maze {

    private GameWorld gameWorld;

    private File mazeFile;

    private ArrayList<Body> wallList;

    public Maze(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        this.wallList = new ArrayList<>();
        loadMaze();
        readObjects();
    }

    public void loadMaze() {

        World world = gameWorld.getWorld();
        for (Body wall: wallList){
            world.destroyBody(wall);
        }
        wallList.clear();

        this.mazeFile = new File("./core/assets/maze/Maze0");

    }

    public void readObjects() {
        try {
            FileReader fr = new FileReader(this.mazeFile);

            BufferedReader bufferedReader = new BufferedReader(fr);

            String c;
            int line = 0;

            while ((c = bufferedReader.readLine()) != null)
            {
                for(int column = 0; column < c.length(); column++){
                    switch (c.charAt(column)){
                        case 'W':
                            addWall(line, column);
                            break;
                        default:
                            break;
                    }
                }
                line++;
            }
            bufferedReader.close();

        } catch (IOException e) {
            System.err.println("Unable to read the file.");
        }


    }

    public void addWall(int i, int j) {

        World world = gameWorld.getWorld();

        Vector2 positionMur = new Vector2(j+1,i+1);

        BodyDef bodyDef1 = new BodyDef();
        bodyDef1.type = BodyDef.BodyType.StaticBody;
        bodyDef1.position.set(positionMur);

        Body body = world.createBody(bodyDef1);

        CircleShape shape = new CircleShape();
        shape.setRadius((1/60f)*GameWorld.WIDTH);
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
}
