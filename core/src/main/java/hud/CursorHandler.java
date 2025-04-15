package hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import jokerhut.main.GameScreen;
import manager.KeyHandler;

import java.security.Key;


public class CursorHandler {

    private Texture cursorTexture;
    private Texture cursorClickedTexture;
    private Texture specialActionClicked;

    public Vector2 currentPos;
    private Vector3 targetPos;
    private float lerpSpeed = 25f;

    public float clickLength = 0.2f;
    public float clickTimer = 0f;
    public boolean hasClicked = false;
    public boolean specialClicked = false;

    public CursorHandler() {
        cursorTexture = new Texture("cursorNew.png");
        cursorClickedTexture = new Texture("cursorSelectednNew.png");
        specialActionClicked = new Texture("specialClicked.png");
        currentPos = new Vector2();
        targetPos = new Vector3();
    }

    public void firstUpdate (float delta, OrthographicCamera camera) {
        targetPos.set(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(targetPos); // needs Vector3
        currentPos.lerp(new Vector2(targetPos.x, targetPos.y), lerpSpeed * delta);

    }

    public void update(float delta) {

        if (!hasClicked) {
            if (KeyHandler.checkIfClicked()) {
                hasClicked = true;
            }
        } else {
            System.out.println(hasClicked);
            clickTimer+= delta;
            if (clickTimer >= clickLength) {
                System.out.println(clickTimer);
                specialClicked = false;
                hasClicked = false;
                clickTimer = 0f;
            }
        }

    }

    public void handleSpecialClick () {
        specialClicked = true;
    }

    public void render(SpriteBatch batch) {
        Texture tex;

        if (hasClicked) {
            if (specialClicked) {
                tex = specialActionClicked;
            } else {
                tex = cursorClickedTexture;
            }
        } else {
            tex = cursorTexture;
        }

        float drawWidth = 0.5f;
        float drawHeight = 0.5f;
        batch.draw(tex, currentPos.x - drawWidth / 2f, currentPos.y - drawHeight / 2f, drawWidth, drawHeight);
    }

    public Vector2 getPosition() {
        return currentPos;
    }

    public void dispose() {
        cursorTexture.dispose();
        cursorClickedTexture.dispose();
    }
}
