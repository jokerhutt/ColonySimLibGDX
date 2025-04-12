package manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import entity.Player;

public class KeyHandler {
    Player player;

    public KeyHandler (Player player) {
        this.player = player;
    }

    public boolean handleSettingCardinalMovement () {

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            player.direction.set(0, 1);
            player.moving = true;
            return true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.direction.set(-1, 0);
            player.moving = true;
            return true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            player.direction.set(0, -1);
            player.moving = true;
            return true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.direction.set(1, 0);
            player.moving = true;
            return true;
        }

        player.moving = false;
        return false;

    }

    public boolean handleSettingDiagonalMovement () {

        if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.direction.set(-1, 1);
            player.moving = true;
            return true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.direction.set(1, 1);
            player.moving = true;
            return true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.direction.set(-1, -1);
            player.moving = true;
            return true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.direction.set(1, -1);
            player.moving = true;
            return true;
        }
        player.moving = false;
        return false;

    }


}
