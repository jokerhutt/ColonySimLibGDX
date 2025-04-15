package manager;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import entity.*;

public class KeyHandler {
    Player player;

    public KeyHandler (Player player) {
        this.player = player;
    }

    public boolean wantsToRun () {
        if (Gdx.input.isKeyPressed(Input.Keys.SHIFT_LEFT)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean handleSettingCardinalMovement () {

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            if (ActionStateHandler.checkNewStateValidity(ActionState.MOVING, player)) {
                player.actionState = ActionState.MOVING;
                player.inputDirection.set(0, 1);

            }
            return true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            if (ActionStateHandler.checkNewStateValidity(ActionState.MOVING, player)) {
                player.actionState = ActionState.MOVING;
                player.inputDirection.set(-1, 0);

            }
            return true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {

            if (ActionStateHandler.checkNewStateValidity(ActionState.MOVING, player)) {
                player.actionState = ActionState.MOVING;
                player.inputDirection.set(0, -1);

            }
            return true;
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            if (ActionStateHandler.checkNewStateValidity(ActionState.MOVING, player)) {
                player.inputDirection.set(1, 0);

                player.actionState = ActionState.MOVING;
            }
            return true;
        }

        if (ActionStateHandler.checkNewStateValidity(ActionState.IDLE, player)) {
            player.actionState = ActionState.IDLE;
        }

        return false;

    }

    public boolean handleSettingDiagonalMovement () {

        if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.inputDirection.set(-1, 1);

            return true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.inputDirection.set(1, 1);

            return true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)) {
            player.inputDirection.set(-1, -1);

            return true;
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) {
            player.inputDirection.set(1, -1);

            return true;
        }

        if (ActionStateHandler.checkNewStateValidity(ActionState.IDLE, player)) {
            player.actionState = ActionState.IDLE;
        }

        return false;
    }

    public static boolean checkIfClicked () {
        if (Gdx.input.justTouched()) {
            return true;
        } else {
            return false;
        }
    }

    public boolean wantsToAttack (float delta) {

        if (player.actionState == ActionState.ATTACKING) {
            player.animationTimer+= delta;
            player.attackTimer+= delta;
            if (player.attackTimer >= player.attackLength) {
                player.attackTimer = 0f;
                player.hasHitThisAttack = true;
                player.actionState = ActionState.IDLE;
            }
        } else {
            if (Gdx.input.justTouched()) {
                player.attackTimer = 0f;
                player.hasHitThisAttack = false;
                player.actionState = ActionState.ATTACKING;
                return true;
            }
        }
        return false;

    }

    public void toggleCameraZoom () {
        if (Gdx.input.isKeyJustPressed(Input.Keys.Z)) {
            player.screen.zoomCamera = !player.screen.zoomCamera;
        }
    }

//    public void checkSwitchItem () {
//        if (Gdx.input.isKeyJustPressed(Input.Keys.E)) {
//            if (player.actionItem != ActionItem.AXE) {
//                player.actionItem = ActionItem.AXE;
//            } else {
//                player.actionItem = ActionItem.PICKAXE;
//            }
//        }
//    }


}
