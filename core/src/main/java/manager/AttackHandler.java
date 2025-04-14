package manager;

import entity.ActionState;
import entity.Entity;
import entity.Player;
import jokerhut.main.Box2DHelper;

public class AttackHandler {

    Player player;

    public AttackHandler (Player player) {

        this.player = player;

    }

    public void handleAttackLogic () {
        if (player.actionState == ActionState.ATTACKING) {
            if (player.actionSensorFixture == null) {
                switch (player.actionItem) {
                 case AXE -> Box2DHelper.createActionSensor("axeSensor", player);
                 case PICKAXE -> Box2DHelper.createActionSensor("pickaxeSensor", player);
                }
            }
        } else {
            if (player.actionSensorFixture != null) {
                player.body.destroyFixture(player.actionSensorFixture);
                player.actionSensorFixture = null;
            }
        }
    }

}
