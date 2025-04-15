package jokerhut.main;

import camera.GameCamera;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import constants.Const;
import entity.Entity_Rock;
import entity.Entity_Tree;
import entity.Player;
import hud.CursorHandler;
import hud.HUD;
import manager.MapManager;
import manager.debugManager;

public class GameScreen implements Screen {

    public MapManager mapManager;
    public TiledMap gameMap;
    public Box2DWorld box2DWorld;
    public SpriteBatch batch;
    public boolean zoomCamera = true;
    OrthogonalTiledMapRenderer mapRenderer;
    public Player player;
    public HUD hud;
    private ShapeRenderer shapeRenderer;
    public CursorHandler cursorHandler;
    public GameCamera gameCamera;

    @Override
    public void show() {

        //MAP

        Gdx.graphics.setSystemCursor(Cursor.SystemCursor.None);
        mapManager = new MapManager(this);
        gameMap = mapManager.initialiseGameMap();
        box2DWorld = new Box2DWorld(this);

        mapManager.initialiseObjectArrays();
        batch = new SpriteBatch();
        cursorHandler = new CursorHandler();
        shapeRenderer = new ShapeRenderer();
        gameCamera = new GameCamera(this);
        mapRenderer = new OrthogonalTiledMapRenderer(gameMap, 1f / Const.OGTILESIZE);
        player = new Player(new Vector2(10, 10), 1f, 1f, this);
        // Set up renderer
        hud = new HUD(this, batch);



    }

    @Override
    public void render(float delta) {
        delta = Math.min(delta, 1/60f);
        player.keyHandler.toggleCameraZoom();
        if (zoomCamera) {
            gameCamera.getCamera().zoom = 0.5f;
        } else {
            gameCamera.getCamera().zoom = 1f;
        }
        box2DWorld.step(delta);
        gameCamera.updateCamera(delta);
        //UPDATE
        cursorHandler.firstUpdate(delta, gameCamera.getCamera());
        player.update(delta);
        for (Entity_Tree tree : mapManager.treeArray) {
            tree.update(delta);
        }

        for (Entity_Rock rock : mapManager.rockArray) {
            rock.update(delta);
        }

        cursorHandler.update(delta);

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

        batch.setProjectionMatrix(gameCamera.getCamera().combined); // or hud.getViewport().getCamera().combined
        batch.begin();
        cursorHandler.render(batch);
        batch.end();

        debugManager.renderARectangleFromArray(mapManager.treeArray, this, shapeRenderer);
        // ✅ Step Box2D
        box2DWorld.renderDebug(gameCamera.getCamera());

        System.out.println("Delta time: " + delta + " seconds");

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
