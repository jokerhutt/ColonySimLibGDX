package entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import jokerhut.main.Box2DWorld;
import jokerhut.main.GameScreen;

public class Entity_Tree extends Entity{


    public Entity_Tree (Vector2 pos, float width, float height, GameScreen screen) {
        super (pos, width, height, screen);
        this.texture = new Texture("treeTexture.png");
        setupSprite();
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
        shape.dispose();
    }

    @Override
    public void update(float delta) {

    }

}
