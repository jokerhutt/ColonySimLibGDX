package entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import constants.Const;
import jokerhut.main.Box2DWorld;
import jokerhut.main.GameScreen;
import manager.KeyHandler;

public class Player extends Entity {

    public TextureRegion idleDown, idleUp, idleLeft, idleRight, idleUpRight, idleUpLeft, idleDownRight, idleDownLeft;
    public Animation<TextureRegion> walkDown, walkUp, walkLeft, walkRight;
    public Animation<TextureRegion> walkUpRight, walkUpLeft, walkDownLeft, walkDownRight;
    public Animation<TextureRegion> axeDown, axeUp, axeLeft, axeRight;
    public Animation<TextureRegion> axeUpRight, axeUpLeft, axeDownLeft, axeDownRight;
    public float animationTimer = 0f;
    public float attackTimer = 0f;
    public float attackLength = 0.8f;

    KeyHandler keyHandler;
    public float speed;
    public Vector2 direction = new Vector2(0, 1);
    public Vector2 inputDirection = new Vector2(0, 0);
    public boolean moving;
    public float runningSpeed = 1.2f;
    public float originalSpeed;

    public Rectangle attackRect;
    public Fixture axeSensorFixture = null;
    public FacingDirection facingDirection = FacingDirection.SOUTH;
    public boolean hasHitThisAttack = false;

    public Player (Vector2 pos, float width, float height, GameScreen screen) {
        super(pos, width, height, screen);
        this.texture = new Texture("Player.png");
        this.keyHandler = new KeyHandler(this);
        this.originalSpeed = 2f * Const.OGTILESIZE;
        this.runningSpeed = originalSpeed * 1.5f;
        this.speed = originalSpeed;
        createBody(screen.box2DWorld);
        screen.box2DWorld.addEntityToMap(this);
        setupAnimations();
        setupSprite();

        attackRect = new Rectangle();
//        updateAxeCollisionZone();

    }

    public void setupAnimations () {

        Texture sheet = new Texture ("playerSheetNew.png");
        TextureRegion[][] split = TextureRegion.split(sheet, 32, 32);

        idleDown = split[0][0];
        idleUp   = split[4][0];

        idleRight = split[2][0];
        idleLeft = split[6][0];

        idleUpRight = split[3][0];
        idleUpLeft = split[5][0];
        idleDownRight = split[1][0];
        idleDownLeft = split[7][0];

        walkDown = new Animation<>(0.2f, split[0][2], split[0][3],  split[0][4]);
        walkUp   = new Animation<>(0.2f, split[4][2], split[4][3],  split[4][4]);
        walkRight = new Animation<>(0.2f, split[2][2], split[2][3],  split[2][4]);
        walkLeft = new Animation<>(0.2f, split[6][2], split[6][3],  split[6][4]);

        walkUpRight = new Animation<>(0.2f, split[3][2], split[3][3],  split[3][4]);
        walkUpLeft   = new Animation<>(0.2f, split[5][2], split[5][3],  split[5][4]);
        walkDownRight = new Animation<>(0.2f, split[1][2], split[1][3],  split[1][4]);
        walkDownLeft = new Animation<>(0.2f, split[7][2], split[7][3],  split[7][4]);

        axeDown = new Animation<>(0.2f, split[0][13], split[0][14],  split[0][15]);
        axeUp   = new Animation<>(0.2f, split[4][13], split[4][14],  split[4][15]);
        axeRight = new Animation<>(0.2f, split[2][13], split[2][14],  split[2][15]);
        axeLeft = new Animation<>(0.2f, split[6][13], split[6][14],  split[6][15]);

        axeUpRight = new Animation<>(0.2f, split[3][13], split[3][14],  split[3][15]);
        axeUpLeft   = new Animation<>(0.2f, split[5][13], split[5][14],  split[5][15]);
        axeDownRight = new Animation<>(0.2f, split[1][13], split[1][14],  split[1][15]);
        axeDownLeft = new Animation<>(0.2f, split[7][13], split[7][14],  split[7][15]);

    }

    public void updateSpriteAnimation () {

        if (actionState == ActionState.MOVING) {

            if (facingDirection == FacingDirection.NORTH) {
                sprite.setRegion(walkUp.getKeyFrame(animationTimer, true));
            } else if (facingDirection == FacingDirection.EAST) {
                sprite.setRegion(walkLeft.getKeyFrame(animationTimer, true));
            }else if (facingDirection == FacingDirection.SOUTH) {
                sprite.setRegion(walkDown.getKeyFrame(animationTimer, true));
            } else if (facingDirection == FacingDirection.WEST) {
                sprite.setRegion(walkRight.getKeyFrame(animationTimer, true));
            }

            else if (facingDirection == FacingDirection.NORTHEAST) {
                sprite.setRegion(walkUpLeft.getKeyFrame(animationTimer, true));
            } else if (facingDirection == FacingDirection.NORTHWEST) {
                sprite.setRegion(walkUpRight.getKeyFrame(animationTimer, true));
            } else if (facingDirection == FacingDirection.SOUTHEAST) {
                sprite.setRegion(walkDownLeft.getKeyFrame(animationTimer, true));
            } else if (facingDirection == FacingDirection.SOUTHWEST) {
                sprite.setRegion(walkDownRight.getKeyFrame(animationTimer, true));
            }

        } else if (actionState == ActionState.IDLE) {
            if (facingDirection == FacingDirection.NORTH) {
                sprite.setRegion(idleUp);
            } else if (facingDirection == FacingDirection.EAST) {
                sprite.setRegion(idleLeft);
            } else if (facingDirection == FacingDirection.SOUTH) {
                sprite.setRegion(idleDown);
            } else if (facingDirection == FacingDirection.WEST) {
                sprite.setRegion(idleRight);
            } else if (facingDirection == FacingDirection.NORTHWEST) {
                sprite.setRegion(idleUpRight);
            } else if (facingDirection == FacingDirection.NORTHEAST) {
                sprite.setRegion(idleUpLeft);
            } else if (facingDirection == FacingDirection.SOUTHWEST) {
                sprite.setRegion(idleDownRight);
            } else if (facingDirection == FacingDirection.SOUTHEAST) {
                sprite.setRegion(idleDownLeft);
            }
        } else if (actionState == ActionState.ATTACKING) {
            if (facingDirection == FacingDirection.NORTH) {
                sprite.setRegion(axeUp.getKeyFrame(animationTimer, true));
            } else if (facingDirection == FacingDirection.EAST) {
                sprite.setRegion(axeLeft.getKeyFrame(animationTimer, true));
            }else if (facingDirection == FacingDirection.SOUTH) {
                sprite.setRegion(axeDown.getKeyFrame(animationTimer, true));
            } else if (facingDirection == FacingDirection.WEST) {
                sprite.setRegion(axeRight.getKeyFrame(animationTimer, true));
            }

            else if (facingDirection == FacingDirection.NORTHEAST) {
                sprite.setRegion(axeUpLeft.getKeyFrame(animationTimer, true));
            } else if (facingDirection == FacingDirection.NORTHWEST) {
                sprite.setRegion(axeUpRight.getKeyFrame(animationTimer, true));
            } else if (facingDirection == FacingDirection.SOUTHEAST) {
                sprite.setRegion(axeDownLeft.getKeyFrame(animationTimer, true));
            } else if (facingDirection == FacingDirection.SOUTHWEST) {
                sprite.setRegion(axeDownRight.getKeyFrame(animationTimer, true));
            }
        }

    }

    public void createAxeSensor() {
        if (axeSensorFixture != null) return;

        System.out.println("Adding sensor");

        float size = 8f;
        Vector2 sensorOffset = new Vector2();

        float pushStrength = 0.2f;
        Vector2 nudge = new Vector2();

        switch (facingDirection) {
            case NORTH     -> nudge.set(0, pushStrength);
            case SOUTH     -> nudge.set(0, -pushStrength);
            case EAST      -> nudge.set(-pushStrength, 0);
            case WEST      -> nudge.set(pushStrength, 0);
            case NORTHEAST -> nudge.set( -pushStrength,  pushStrength);
            case NORTHWEST -> nudge.set(pushStrength,  pushStrength);
            case SOUTHEAST -> nudge.set( -pushStrength, -pushStrength);
            case SOUTHWEST -> nudge.set(pushStrength, -pushStrength);
        }

        body.setLinearVelocity(nudge);

        // Use direction to set offset
        switch (facingDirection) {
            case NORTH     -> sensorOffset.set(0, size);
            case SOUTH     -> sensorOffset.set(0, -size);
            case EAST      -> sensorOffset.set(-size, 0);
            case WEST      -> sensorOffset.set(size, 0);
            case NORTHEAST -> sensorOffset.set(-size * 0.7071f, size * 0.7071f);
            case NORTHWEST -> sensorOffset.set(size * 0.7071f, size * 0.7071f);
            case SOUTHEAST -> sensorOffset.set(-size * 0.7071f, -size * 0.7071f);
            case SOUTHWEST -> sensorOffset.set(size * 0.7071f, -size * 0.7071f);
        }

        sensorOffset.add(MathUtils.random(-0.5f, 0.5f), MathUtils.random(-0.5f, 0.5f));



        // Create sensor shape at offset
        PolygonShape shape = new PolygonShape();
        shape.setAsBox(4f, 4f, sensorOffset, 0f);

        FixtureDef fd = new FixtureDef();
        fd.shape = shape;
        fd.isSensor = true;

        axeSensorFixture = body.createFixture(fd);
        axeSensorFixture.setUserData("axeSensor");

        shape.dispose();
    }

    public void setupSprite () {
        this.sprite = new Sprite(idleDown);
        this.sprite.setSize(width, height);
        this.sprite.setPosition(pos.x, pos.y);
    }


    @Override
    public void createBody(Box2DWorld box2DWorld) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(pos.x + width / 2f, (pos.y + height / 6f)) ; // center the box
        bodyDef.type = BodyDef.BodyType.DynamicBody;

        body = box2DWorld.world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(width / 4f, height / 6f); // full size, centered

        body.createFixture(shape, 0f); // density = 0 for static
        shape.dispose();
    }

    public void applyVelocityFromDirection() {
        body.setLinearVelocity(direction.scl(speed));
    }

    public void update (float delta) {

        if (keyHandler.wantsToRun()){
            speed = runningSpeed;
        } else {
            speed = originalSpeed;
        }

        inputDirection.set(0, 0);

        keyHandler.wantsToAttack(delta);

        if (!keyHandler.handleSettingDiagonalMovement()) {
            keyHandler.handleSettingCardinalMovement();
        }


//        if (actionState == ActionState.ATTACKING) {
//            body.setLinearVelocity(0, 0);
//            if (axeSensorFixture == null) {
//                createAxeSensor();
//            }
//        } else {
//            if (axeSensorFixture != null) {
//                body.destroyFixture(axeSensorFixture);
//                axeSensorFixture = null;
//            }
//        }

        if (actionState == ActionState.ATTACKING) {
//            body.setLinearVelocity(0, 0);
            if (axeSensorFixture == null) {
                createAxeSensor();
            }
        } else {
            if (axeSensorFixture != null) {
                body.destroyFixture(axeSensorFixture);
                axeSensorFixture = null;
            }
        }

        if (actionState == ActionState.MOVING) {
            direction.set(inputDirection);
            animationTimer += delta;
            applyVelocityFromDirection();
        } else if (actionState == ActionState.IDLE){
            animationTimer = 0f;
            body.setLinearVelocity(0, 0);
        }

        pos.x = body.getPosition().x - width / 2f;
        pos.y = body.getPosition().y - height / 2f;

        updateSprite();
        updateSpriteAnimation();

    }





}


