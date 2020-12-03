package com.zeldiablo.models;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;
import com.zeldiablo.factories.TextureFactory;
import com.zeldiablo.models.enums.MazeObjects;
import com.zeldiablo.models.monsters.Monster;
import com.zeldiablo.models.monsters.Skeleton;
import com.zeldiablo.models.portals.Portal;
import com.zeldiablo.models.traps.Trap;
import com.zeldiablo.models.traps.TrapDamage;
import com.zeldiablo.models.treasure.Treasure;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;

import java.io.*;
import java.util.ArrayList;

public class Maze {

    private GameWorld gameWorld;

    private File mazeFile;

    private ArrayList<Body> wallList;
    private ArrayList<Trap> trapList;
    private ArrayList<Portal> portalList;
    private ArrayList<Monster> monsterList;
    private ArrayList<Treasure> treasureList;
    private ArrayList<Vector2> monsterToInit;

    private int currentNumMaze;
    private float tmpAnim;

    public Maze(GameWorld gameWorld) {
        this.gameWorld = gameWorld;
        this.wallList = new ArrayList<>();
        this.trapList = new ArrayList<>();
        this.portalList = new ArrayList<>();
        this.monsterList = new ArrayList<>();
        this.monsterToInit = new ArrayList<>();
        this.treasureList = new ArrayList<>();
        this.currentNumMaze = 0;
        this.tmpAnim = Gdx.graphics.getDeltaTime();
        loadMaze(0);
    }

    /**
     * La fonction loadMaze permet de charger un labyrinthe avec un fichier
     */
    public void loadMaze(int num) {
        for (Monster monster : monsterList){
            monster.stopTimer();
        }
        this.monsterList.clear();
        this.wallList.clear();
        this.trapList.clear();
        this.portalList.clear();
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

            while ((c = bufferedReader.readLine()) != null) {

                for (int column = 0; column < c.length(); column++) {
                    switch (c.charAt(column)) {
                        case MazeObjects.MONSTER:
                            this.monsterToInit.add(new Vector2(line, column));
                            break;
                        case MazeObjects.WALL:
                            addWall(line, column);
                            break;
                        case MazeObjects.TRAP:

                            break;
                        case MazeObjects.TREASURE:
                            addTreasure(line,column);
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

        try {

            File fXmlFile = new File("core/assets/maze/Config");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);

            doc.getDocumentElement().normalize();

            NodeList nLabyList = doc.getElementsByTagName("laby");
            Node nNode = nLabyList.item(this.currentNumMaze);
            NodeList nPortalList = ((Element) nNode).getElementsByTagName("portal");

            for (int temp2 = 0; temp2 < nPortalList.getLength(); temp2++) {

                Node nNode2 = nPortalList.item(temp2);

                if (nNode2.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement2 = (Element) nNode2;
                    int id = Integer.valueOf(eElement2.getAttribute("id"));
                    int numLaby = Integer.valueOf(eElement2.getElementsByTagName("numlaby").item(0).getTextContent());
                    int posx = Integer.valueOf(eElement2.getElementsByTagName("posx").item(0).getTextContent());
                    int posy = Integer.valueOf(eElement2.getElementsByTagName("posy").item(0).getTextContent());
                    int d = Integer.valueOf(eElement2.getElementsByTagName("draw").item(0).getTextContent());
                    portalList.add(new Portal(id,numLaby,new Vector2(posx,(posy)),d,this.gameWorld.getWorld()));
                }
            }

            NodeList nLinkList = ((Element) nNode).getElementsByTagName("link");


            for (int temp2 = 0; temp2 < nLinkList.getLength(); temp2++) {

                Node nNode2 = nLinkList.item(temp2);

                if (nNode2.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement2 = (Element) nNode2;

                    int num1 = Integer.valueOf(eElement2.getElementsByTagName("p1").item(0).getTextContent());
                    int num2 = Integer.valueOf(eElement2.getElementsByTagName("p2").item(0).getTextContent());
                    Portal p1 = null;
                    Portal p2 = null;
                    for(int i = 0; i < portalList.size();i++){

                        if(portalList.get(i).getNumPortal() == num1){
                            p1 = portalList.get(i);
                        }

                        if(portalList.get(i).getNumPortal() == num2){
                            p2 = portalList.get(i);
                        }
                    }
                    if ( p1 == null || p2 == null ){
                        System.out.println("Nous pouvons pas lier " + eElement2.getAttribute("id") + " car un des portail n'est pas existant");
                    }else {
                        p1.setExitPortal(p2);
                        p2.setExitPortal(p1);
                    }
                }
            }

            NodeList nTrapList = ((Element) nNode).getElementsByTagName("trap");

            for (int temp2 = 0; temp2 < nTrapList.getLength(); temp2++) {

                Node nNode2 = nTrapList.item(temp2);

                if (nNode2.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement2 = (Element) nNode2;

                    int posx = Integer.valueOf(eElement2.getElementsByTagName("posx").item(0).getTextContent());
                    int posy = Integer.valueOf(eElement2.getElementsByTagName("posy").item(0).getTextContent());
                    int angle = Integer.valueOf(eElement2.getElementsByTagName("angle").item(0).getTextContent());
                    addTrap(posx,posy,angle);
                }
            }

        } catch (Exception e) {
            System.err.println("Unable to read the file.");
        }

    }

    /**
     * Ajoute un trésor au monde
     * @param line la ligne dans le fichier
     * @param column la colonne dans le fichier
     */
    private void addTreasure(int line, int column) {
        this.treasureList.add(new Treasure(gameWorld,column+1,GameWorld.HEIGHT - line+1, 10));
    }

    /**
     * Initialise les monstres à partir de la liste des monstres à initialiser.
     * A la création du labyrinthe, les monstres ne sont pas encore initialisé. Il faut donc appeler cette méthode.
     */
    public void initMonster() {
        for (Vector2 vec : this.monsterToInit)
            addMonster((int) vec.x, (int) vec.y);
        this.monsterToInit.clear();
    }

    /**
     * Ajoute un monstre au monde
     * @param i la ligne dans le fichier
     * @param j la colonne dans le fichier
     */
    private void addMonster(int i, int j) {
        this.monsterList.add(new Skeleton(gameWorld,j+1,GameWorld.HEIGHT - (i+1), gameWorld.getPlayer()));
    }

    /**
     * Ajoute un portail au monde
     * @param i la ligne dans le fichier
     * @param j la colonne dans le fichier
     */
    private void addPortal(int i, int j) {
        World world = gameWorld.getWorld();
        //this.portalList.add(new Portal(this.currentNumMaze,this.currentNumMaze,new Vector2(j+1,GameWorld.HEIGHT - (i+1)), world));

    }

    /**
     * Ajoute un piege au monde
     * @param i la ligne dans le fichier
     * @param j la colonne dans le fichier
     */
    private void addTrap(int i, int j, int angle) {
        Trap trap = new TrapDamage(new Vector2(j+1, (i+1)), gameWorld, (float) (angle*Math.PI/180));
        this.gameWorld.addBody(trap.getBody());
        this.trapList.add(trap);
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
        body.setUserData("Wall");

        this.wallList.add(body);

    }

    public void draw(SpriteBatch batch, SpriteBatch batchText) {
        this.tmpAnim += Gdx.graphics.getDeltaTime();

        // Textures du sol
        Texture grass = TextureFactory.INSTANCE.getGrass();
        grass.setWrap(Texture.TextureWrap.Repeat, Texture.TextureWrap.Repeat);
        TextureRegion grassRegion = new TextureRegion(grass);
        grassRegion.setRegion(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        batch.begin();
        batch.draw(grassRegion, 0, 0, grass.getWidth(), grass.getHeight());
        batch.end();

        drawNumMaze(batchText);

        // Texture portail
        for (Portal p :portalList){
            if(p.getNumMaze() == this.currentNumMaze) {
                p.draw(batch);
            }
        }

        // Texture piège
        for (Trap p :trapList){
            p.draw(batch);
        }

        // Texture des monstres
        for (Monster m : monsterList)
            m.draw(batch, batchText);

        // Texture des murs
        batch.begin();
        float x, y, radius;
        for (Body wall: wallList) {
            radius = wall.getFixtureList().get(0).getShape().getRadius();
            x = wall.getPosition().x - radius;
            y = wall.getPosition().y - radius;
            batch.draw((TextureRegion) TextureFactory.INSTANCE.getAnimatedTree().getKeyFrame(this.tmpAnim),
                    x, y, radius *2, radius *2);
        }
        batch.end();
    }

    private void drawNumMaze(SpriteBatch batchText) {
        batchText.begin();
        Label text;
        Label.LabelStyle textStyle;
        textStyle = new Label.LabelStyle();
        textStyle.font = new BitmapFont();
        text = new Label(Integer.toString(this.currentNumMaze),textStyle);
        text.setFontScale(2f,2f);
        text.setPosition(Gdx.graphics.getWidth() - 50, Gdx.graphics.getHeight() - 50);
        text.draw(batchText, 1);
        batchText.end();
    }

    public void resetMaze() {
        World world = gameWorld.getWorld();
        Array<Body> bodies = new Array<>();
        world.getBodies(bodies);
        for (Body body : bodies){
            if(!(body.getUserData() instanceof Player))
            world.destroyBody(body);

        }
        for (Trap trap: trapList){
            trap.clearTimer();
        }
        this.currentNumMaze = 0;
    }

    /**
     * Retourne une liste des coordonées des murs présent dans le labyrinthe
     * @return liste des murs
     */
    public ArrayList<Vector2> getWallsCoord() {
        ArrayList<Vector2> walls = new ArrayList<>();
        for (Body body : this.wallList)
            walls.add(body.getPosition());
        return walls;
    }
}
