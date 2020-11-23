package com.zeldiablo.models.monsters;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Timer;
import com.zeldiablo.controllers.Direction;
import com.zeldiablo.controllers.ai.Node;
import com.zeldiablo.controllers.ai.PathFinding;
import com.zeldiablo.controllers.ai.PathFindingException;
import com.zeldiablo.models.Effectable;
import com.zeldiablo.models.Entity;
import com.zeldiablo.models.GameWorld;

import java.util.HashMap;
import java.util.List;

public abstract class Monster implements Entity, Effectable {

    private Entity target;
    private Body body;
    private float speed;

    protected int hp;

    private Timer.Task step;
    private Timer.Task recalculate;

    private PathFinding finding;

    protected HashMap<Direction, Animation> animations;
    private float tmpAnim;
    private int walking;
    private Direction direction;

    public Monster(GameWorld gameWorld, float x, float y, Entity tar, int hp) {
        this.target = tar;
        this.speed = 0.1f;  // Plus c'est grand moins il va vite
        this.hp = hp;
        this.animations = new HashMap<>();
        this.tmpAnim = Gdx.graphics.getDeltaTime();
        this.walking = 0;
        this.direction = Direction.Down;

        // --- Création du body --- //
        BodyDef bd = new BodyDef();
        bd.type = BodyDef.BodyType.DynamicBody;
        bd.position.set(x, y);
        body = gameWorld.getWorld().createBody(bd);

        FixtureDef fixture = new FixtureDef();
        Shape shape = new CircleShape();
        shape.setRadius(SIZE/2);
        fixture.shape = shape;
        fixture.density = 1f;
        fixture.restitution = 0.25f;
        fixture.friction = 0f;

        body.setUserData(this);
        body.createFixture(fixture);
        shape.dispose();

        // Récupération d'une grille de booléen sur laquelle faire les calcules
        final boolean[][] grid = gameWorld.generateGrid();

        try {
            this.finding = new PathFinding(grid, (int) this.getX(), (int) this.getY(), (int) this.target.getX(), (int) this.target.getY());
        } catch (PathFindingException e) {
            e.printStackTrace();
        }

        // Création de la tâche qui s'occupe de déplacer le monstre
        this.step = new Timer.Task() {
            @Override
            public void run() {
                move();
            }
        };

        // Création de la tâche qui recalcule le chemin
        this.recalculate = new Timer.Task() {
            @Override
            public void run() {
                try {
                    finding = new PathFinding(grid, (int) getX(), (int) getY(), (int) target.getX(), (int) target.getY());
                } catch (PathFindingException e) {
                    e.printStackTrace();
                }
            }
        };

        Timer.schedule(this.step, 0, this.speed);
        Timer.schedule(this.recalculate, 0, 1f);
    }

    /**
     * Effectue un déplacement du monstre en suivant le chemin calculé
     */
    public void move() {
        List<Node> path = this.finding.getPath();
        Node node;
        if (!path.isEmpty())
            node = path.remove(path.size()-1);
        else
            node = new Node((int) getX(), (int) getY());

        float dx = node.x - getX();
        float dy = node.y - getY();

        // Calcule de la distance entre les deux
        float x = this.target.getX() - this.getX();
        float y = this.target.getY() - this.getY();

        // Un peu de magie (et de la trigo) et on obtient l'angle
        float angle = (float) Math.atan(y/x);
        if (x < 0)
            angle += Math.PI;

        // Application du mouvement
        this.body.setTransform(body.getPosition(), angle);
        this.body.setLinearVelocity(dx, dy);

        if (dx > 0)
            this.direction = Direction.Left;
        if (dx < 0)
            this.direction = Direction.Right;
        if (dy > 0)
            this.direction = Direction.Up;
        if (dy < 0)
            this.direction = Direction.Down;

        if (dx == 0 && dy == 0)
            this.walking = 0;
        else
            this.walking = 1;
    }

    /**
     * Methode servant a récupérer la position de l'entité
     * @return Vector2 de la position
     */
    public Vector2 getPosition() {
        return this.body.getPosition();
    }

    /**
     * Méthode servant a récupérer la position en X de l'entité
     *
     * @return int coordonée X
     */
    @Override
    public float getX() {
        return (int) this.getPosition().x;
    }

    /**
     * Méthode servant a récupérer la position en Y de l'entité
     *
     * @return int coordonée Y
     */
    @Override
    public float getY() {
        return (int) this.getPosition().y;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    public int getHp() {
        return hp;
    }

    @Override
    public void draw(SpriteBatch batch, SpriteBatch batchText) {
        this.tmpAnim += Gdx.graphics.getDeltaTime()*this.walking;
        TextureRegion region = (TextureRegion) this.animations.get(this.direction).getKeyFrame(this.tmpAnim);
        batch.begin();
        batch.draw(region, getX()-SIZE/2, getY()-SIZE/2, SIZE, SIZE);
        batch.end();

        batchText.begin();
        Label text;
        Label.LabelStyle textStyle;
        textStyle = new Label.LabelStyle();
        textStyle.font = new BitmapFont();
        text = new Label(Integer.toString(this.hp),textStyle);
        text.setFontScale(2f,2f);
        text.setPosition((((float)(this.getX() - 1.3)*Gdx.graphics.getWidth()) / GameWorld.WIDTH), ((this.getY() + 2)*Gdx.graphics.getHeight()) / GameWorld.HEIGHT);
        text.draw(batchText, 1);
        batchText.end();
    }


    public void stopTimer(){
        this.recalculate.cancel();
    }
}
