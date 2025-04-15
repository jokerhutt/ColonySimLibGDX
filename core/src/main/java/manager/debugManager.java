package manager;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import entity.Entity_Tree;
import jokerhut.main.GameScreen;

public class debugManager {

    public static void renderARectangleFromArray (Array<Entity_Tree> array, GameScreen screen, ShapeRenderer shapeRenderer) {
        shapeRenderer.setProjectionMatrix(screen.gameCamera.getCamera().combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.BLUE);

        for (Entity_Tree tree : array) {
            Rectangle rect = tree.transparentRectangle; // or getTransparentRectangle() if private
            shapeRenderer.rect(rect.x, rect.y, rect.width, rect.height);
        }

        shapeRenderer.end();
    }

}
