package jokerhut.main;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import constants.Const;

public class Main extends Game {

    @Override
    public void create() {
        Gdx.graphics.setWindowedMode(Const.WORLD_WIDTH * (Const.OGTILESIZE * Const.SCALE), Const.WORLD_HEIGHT * (Const.OGTILESIZE * Const.SCALE));
        setScreen(new GameScreen()); // ✅ This is how you load your game screen
    }
}
