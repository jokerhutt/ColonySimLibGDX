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

    public Rectangle transparentRectangle;
    private boolean queuedHit = false;
    boolean hasBeenHit = false;
    Texture deadTexture = new Texture("treeTrunk2.png");


    public Entity_Tree (Vector2 pos, float width, float height, GameScreen screen) {
        super (pos, width, height, screen);
        this.texture = new Texture("treeTexture.png");
        this.transparentRectangle = new Rectangle();
        setupSprite();
        float shrinkAmountX = 0.8f;
        float shrinkAmountY = 0.9f;

        this.transparentRectangle.width = sprite.getWidth() - shrinkAmountX;
        this.transparentRectangle.height = sprite.getHeight() - shrinkAmountY;
        this.transparentRectangle.x = sprite.getX() + shrinkAmountX / 2f;
        this.transparentRectangle.y = sprite.getY() + shrinkAmountY / 2f;
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

        if (this.transparentRectangle.overlaps(screen.player.sprite.getBoundingRectangle())) {
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
            screen.player.inventory.addToInventory("wood", "resource");
            screen.hud.woodDisplay.updateValue(screen.player.woodCount, "", "");
            this.sprite.setSize(sprite.getWidth(), sprite.getHeight() / 2f);
            this.sprite.setRegion(deadTexture);
    }

}
