package jokerhut.main;

import camera.GameCamera;
import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import constants.Const;
import entity.Entity_Rock;
import entity.Entity_Tree;
import entity.Player;
import hud.HUD;
import manager.MapManager;

public class GameScreen implements Screen {

    public MapManager mapManager;
    public TiledMap gameMap;
    public Box2DWorld box2DWorld;
    SpriteBatch batch;
    OrthogonalTiledMapRenderer mapRenderer;
    public Player player;
    public HUD hud;
    private ShapeRenderer shapeRenderer;

    public GameCamera gameCamera;


    @Override
    public void show() {

        //MAP
        mapManager = new MapManager(this);
        gameMap = mapManager.initialiseGameMap();
        box2DWorld = new Box2DWorld(this);
        mapManager.initialiseObjectArrays();
        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();
        gameCamera = new GameCamera();

        player = new Player(new Vector2(10 * Const.OGTILESIZE, 10 * Const.OGTILESIZE),  16, 16, this);
        // Set up renderer
        mapRenderer = new OrthogonalTiledMapRenderer(gameMap, 1f);
        hud = new HUD(this, batch);



    }

    @Override
    public void render(float delta) {

        //UPDATE
        player.update(delta);
        for (Entity_Tree tree : mapManager.treeArray) {
            tree.update(delta);
        }

        for (Entity_Rock rock : mapManager.rockArray) {
            rock.update(delta);
        }

        //set view to game camere
        mapRenderer.setView(gameCamera.getCamera());
        //draw map
        mapRenderer.render();

        //syncs batch drawing to camera
        batch.setProjectionMatrix(gameCamera.getCamera().combined);
        batch.begin();

        player.render(batch);

        //Render all trees
        for (Entity_Tree tree : mapManager.treeArray) {
            tree.render(batch);
        }

        for (Entity_Rock rock : mapManager.rockArray) {
            rock.render(batch);
        }

        batch.end();
        hud.render(delta);
        // ✅ Step Box2D
        box2DWorld.step(delta);
        box2DWorld.renderDebug(gameCamera.getCamera());
    }

    @Override
    public void resize(int width, int height) {
        hud.resize(width, height);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
