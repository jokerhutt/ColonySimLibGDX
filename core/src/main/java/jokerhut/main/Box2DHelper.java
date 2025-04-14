package jokerhut.main;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import constants.Const;
import entity.Player;

public class Box2DHelper {

    public static void createActionSensor(String sensorName, Player player) {
        if (player.actionSensorFixture != null) return;

        System.out.println("Adding sensor");

        float size = 8f / Const.OGTILESIZE;
        Vector2 sensorOffset = new Vector2();

        float pushStrength = 0.00001f;
        Vector2 nudge = new Vector2();

        switch (player.facingDirection) {
            case NORTH     -> nudge.set(0, pushStrength);
            case SOUTH     -> nudge.set(0, -pushStrength);
            case EAST      -> nudge.set(-pushStrength, 0);
            case WEST      -> nudge.set(pushStrength, 0);
            case NORTHEAST -> nudge.set( -pushStrength,  pushStrength);
            case NORTHWEST -> nudge.set(pushStrength,  pushStrength);
            case SOUTHEAST -> nudge.set( -pushStrength, -pushStrength);
            case SOUTHWEST -> nudge.set(pushStrength, -pushStrength);
        }

        player.body.setLinearVelocity(nudge);

        // Use direction to set offset
        switch (player.facingDirection) {
            case NORTH     -> sensorOffset.set(0, size);
            case SOUTH     -> sensorOffset.set(0, -size);
            case EAST      -> sensorOffset.set(-size, 0);
            case WEST      -> sensorOffset.set(size, 0);
            case NORTHEAST -> sensorOffset.set(-size * 0.7071f, size * 0.7071f);
            case NORTHWEST -> sensorOffset.set(size * 0.7071f, size * 0.7071f);
            case SOUTHEAST -> sensorOffset.set(-size * 0.7071f, -size * 0.7071f);
            case SOUTHWEST -> sensorOffset.set(size * 0.7071f, -size * 0.7071f);
        }


        // Create sensor shape at offset
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(4f / Const.OGTILESIZE, 4f / Const.OGTILESIZE, sensorOffset, 0f);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.isSensor = true;

        player.actionSensorFixture = player.body.createFixture(fd);
        player.actionSensorFixture.setUserData(sensorName);

        shape.dispose();
    }


}
