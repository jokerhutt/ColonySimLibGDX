package entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import jokerhut.main.Box2DHelper;
import jokerhut.main.Box2DWorld;
import jokerhut.main.GameScreen;

public abstract class Entity {

    Texture texture;
    public Vector2 pos;
    public float width, height;
    public float health = 3f;
    public float maxHealth;
    public Sprite sprite;
    public Body body;
    String type;
    public GameScreen screen;

    public ActionState actionState = ActionState.IDLE;

    public Entity(Vector2 pos, float width, float height, GameScreen screen) {
        this.pos = pos;
        this.width = width;
        this.height = height;
        this.screen = screen;
    }

    public void createBody (Box2DWorld box2DWorld) {
        Box2DHelper.createBody(box2DWorld, this);
    };

    public void setupSprite () {
        this.sprite = new Sprite(texture);
        this.sprite.setSize(width, height);
        this.sprite.setPosition(pos.x, pos.y);
    }

    public void updateSprite () {
        this.sprite.setSize(width, height);
        this.sprite.setPosition(pos.x, pos.y);
    }

    public void render (SpriteBatch batch) {
        sprite.draw(batch);
    }

    public abstract void update (float delta);




}
