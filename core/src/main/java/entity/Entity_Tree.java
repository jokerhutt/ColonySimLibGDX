package entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import jokerhut.main.Box2DWorld;
import jokerhut.main.GameScreen;

public class Entity_Tree extends Entity{

    Rectangle transparentRectangle;
    private boolean queuedHit = false;
    boolean hasBeenHit = false;
    Texture deadTexture = new Texture("treeTrunk2.png");


    public Entity_Tree (Vector2 pos, float width, float height, GameScreen screen) {
        super (pos, width, height, screen);
        this.texture = new Texture("treeTexture.png");
        this.transparentRectangle = new Rectangle();
        setupSprite();
        this.transparentRectangle.width = sprite.getWidth();
        this.transparentRectangle.height = sprite.getHeight();
    }

    @Override
    public void createBody(Box2DWorld box2DWorld) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(pos.x + width / 2f, (pos.y + height / 6f)) ; // center the box
        bodyDef.type = BodyDef.BodyType.StaticBody;

        body = box2DWorld.world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 4f, height / 6f); // full size, centered

        body.createFixture(shape, 0f); // density = 0 for static
        body.setUserData(this);
        shape.dispose();
    }

    public void queueHit() {
        System.out.println("Queuing hit");
        queuedHit = true;
    }

    @Override
    public void update(float delta) {

        if (queuedHit) {
            hasBeenHit = true;
            takeDamage(); // handle sound, animation, etc.
            queuedHit = false;
        }

        if (this.sprite.getBoundingRectangle().overlaps(screen.player.sprite.getBoundingRectangle())) {
            sprite.setAlpha(0.5f);
        } else {
            sprite.setAlpha(1f);
        }
    }

    public void takeDamage () {
        if (this.health > 0) {
            this.health --;
            System.out.println("Took dmg health now + " + health);
            if (this.health == 0){
                changeSpriteBasedOnHealth();
            }
        }
    }

    public void changeSpriteBasedOnHealth () {
            screen.player.woodCount++;
            screen.hud.woodDisplay.updateValue(screen.player.woodCount, "", "");
            this.sprite.setSize(sprite.getWidth(), sprite.getHeight() / 2f);
            this.sprite.setRegion(deadTexture);
    }

}
