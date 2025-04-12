package camera;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import constants.Const;


public class GameCamera {

    private OrthographicCamera camera;
    private Viewport viewport;

    public GameCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Const.WORLD_WIDTH * Const.OGTILESIZE, Const.WORLD_HEIGHT * Const.OGTILESIZE);

        viewport = new FitViewport(Const.WORLD_WIDTH, Const.WORLD_HEIGHT, camera);
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

}
