package jokerhut.main;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import entity.Entity;
import entity.Entity_Tree;
import entity.Player;

import java.util.HashMap;

public class Box2DWorld {

    public World world;
    private Box2DDebugRenderer debugRenderer;
    public HashMap<Integer, Entity> entityMap = new HashMap<>();
    GameScreen screen;

    public Box2DWorld (GameScreen screen) {
        this.screen = screen;
        world = new World(new Vector2(0, 0), true);
        handleContactListenerSetup();
    }

    public void handleContactListenerSetup () {
        world.setContactListener(new ContactListener() {
            @Override
            public void beginContact(Contact contact) {
                Fixture a = contact.getFixtureA();
                Fixture b = contact.getFixtureB();

                handleHit(a, b);
                handleHit(b, a);
            }

            @Override public void endContact(Contact contact) {}
            @Override public void preSolve(Contact contact, Manifold oldManifold) {}
            @Override public void postSolve(Contact contact, ContactImpulse impulse) {}

            public void handleHit(Fixture sensor, Fixture other) {

                Object sensorTag = sensor.getUserData();
                Object target = other.getBody().getUserData();

                if ("axeSensor".equals(sensorTag) && target instanceof Entity_Tree tree) {
                    System.out.println("Hit a tree");
                    tree.queueHit();
                }

                //TODO rock logic
                if ("pickaxeSensor".equals(sensorTag) && target instanceof Entity_Tree rock) {
                    rock.queueHit();
                }

            }
        });
    }

    public void step (float delta) {
        world.step(delta, 6, 2);
        world.clearForces();
        debugRenderer = new Box2DDebugRenderer();
    }

    public void renderDebug(OrthographicCamera camera) {
        debugRenderer.render(world, camera.combined);
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
