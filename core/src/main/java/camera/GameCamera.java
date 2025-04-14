package camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import constants.Const;
import jokerhut.main.GameScreen;
import jokerhut.main.Main;


public class GameCamera {

    private OrthographicCamera camera;
    private Viewport viewport;

    private Vector2 cameraTarget = new Vector2();
    private Vector2 cameraPosition = new Vector2();
    private float cameraLerp = 5f;

    int mapWidth;
    int mapHeight;
    GameScreen screen;

    public GameCamera(GameScreen screen) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Const.WORLD_WIDTH, Const.WORLD_HEIGHT);
        viewport = new FitViewport(Const.WORLD_WIDTH, Const.WORLD_HEIGHT, camera);
        this.screen = screen;

        this.mapHeight = screen.gameMap.getProperties().get("height", Integer.class);
        this.mapWidth = screen.gameMap.getProperties().get("width", Integer.class);
        camera.zoom = 0f;
    }

    public OrthographicCamera getCamera() {
        return camera;
    }

    public Viewport getViewport() {
        return viewport;
    }

    public void update(float x, float y) {
        camera.position.set(x, y, 0);
        camera.update();
    }

    public void updateCamera (float delta) {

        //CENTER POSITIONS OF PLAYERS
        float centerX = screen.player.getCenterX();
        float centerY = screen.player.getCenterY();

        // Update camera target
        cameraTarget.set(centerX, centerY);

        //LERP for smooth render
        if (cameraPosition.dst(cameraTarget) > 0.01f) {
            cameraPosition.lerp(cameraTarget, cameraLerp * delta);
        }

        // Clamp to map bounds
        float halfViewWidth = camera.viewportWidth * 0.5f * camera.zoom;
        float halfViewHeight = camera.viewportHeight * 0.5f * camera.zoom;

        float clampedX = MathUtils.clamp(cameraPosition.x, halfViewWidth, mapWidth - halfViewWidth);
        float clampedY = MathUtils.clamp(cameraPosition.y, halfViewHeight, mapHeight - halfViewHeight);

        clampedX = Math.round(clampedX * 100f) / 100f;
        clampedY = Math.round(clampedY * 100f) / 100f;

        camera.position.set(clampedX, clampedY, 0);
        camera.update();
    }

}
