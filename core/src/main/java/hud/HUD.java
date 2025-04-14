package hud;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import jokerhut.main.GameScreen;



public class HUD {

    protected Stage stage;
    GameScreen screen;
    private BitmapFont font = new BitmapFont();
    public PairActorGroup woodDisplay;
    Table woodTable;

    public HUD (GameScreen screen, SpriteBatch batch) {

        stage = new Stage(new ScreenViewport(), batch);
        this.screen = screen;
        Gdx.input.setInputProcessor(stage);

        woodDisplay = new PairActorGroup(screen, "lumberResourceIcon.png", "Wood:", screen.player.woodCount, "left", 'M', 2f);
        woodTable = new Table();
        woodTable.top().right().pad(10).padRight(50);
        woodTable.setFillParent(true);
        woodTable.add(woodDisplay).padTop(10); // no need to right-align here
        stage.addActor(woodTable);

    }

    public void render (float delta) {
        stage.getViewport().apply();
        stage.act(delta);
        stage.draw();
    }

    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

}
