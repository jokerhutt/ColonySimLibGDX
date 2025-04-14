package entity;

public class ActionStateHandler {

    public static boolean checkNewStateValidity (ActionState intendedState, Player player) {

        if (intendedState == ActionState.MOVING) {

            if (player.actionState != ActionState.ATTACKING) {
                return true;
            }
            else {
                return false;
            }

        }

        if (intendedState == ActionState.IDLE) {

            if (player.actionState != ActionState.ATTACKING) {
                return true;
            } else {
                return false;
            }

        }
        return false;

    }


}
