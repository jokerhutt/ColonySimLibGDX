package jokerhut.main;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import entity.Entity;
import entity.Entity_Tree;

import java.util.HashMap;

public class Box2DWorld {

    public World world;
    private Box2DDebugRenderer debugRenderer;
    public HashMap<Integer, Entity> entityMap = new HashMap<>();

    public Box2DWorld () {
        world = new World(new Vector2(0, 0), true);
    }

    public void step (float delta) {
        world.step(delta, 6, 2);
        world.clearForces();
        debugRenderer = new Box2DDebugRenderer();
    }

    public void renderDebug(OrthographicCamera camera) {
        debugRenderer.render(world, camera.combined); // ✅ Draw it
    }

    public void dispose () {
        debugRenderer.dispose();
        world.dispose();
    }

    public void addEntityToMap(Entity entity) {
        if (entity.body != null && entity.body.getFixtureList().size > 0) {
            int fixtureId = entity.body.getFixtureList().first().hashCode();
            entityMap.put(fixtureId, entity);
        }
    }


}
