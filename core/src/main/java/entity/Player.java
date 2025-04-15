package entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import constants.Const;
import jokerhut.main.*;
import manager.AttackHandler;
import manager.KeyHandler;

public class Player extends Entity {

    public TextureRegion idleDown, idleUp, idleLeft, idleRight, idleUpRight, idleUpLeft, idleDownRight, idleDownLeft;
    public Animation<TextureRegion> walkDown, walkUp, walkLeft, walkRight;
    public Animation<TextureRegion> walkUpRight, walkUpLeft, walkDownLeft, walkDownRight;
    public Animation<TextureRegion> axeDown, axeUp, axeLeft, axeRight;
    public Animation<TextureRegion> axeUpRight, axeUpLeft, axeDownLeft, axeDownRight;
    public Animation<TextureRegion> pickaxeDown, pickaxeUp, pickaxeLeft, pickaxeRight;
    public Animation<TextureRegion> pickaxeUpRight, pickaxeUpLeft, pickaxeDownLeft, pickaxeDownRight;
    public float animationTimer = 0f;
    public float attackTimer = 0f;
    public float attackLength = 0.8f;
    public ActionItem actionItem = ActionItem.AXE;
    public KeyHandler keyHandler;
    AnimationHandler animationHandler;
    public float speed;
    public Vector2 direction = new Vector2(0, 1);
    public Vector2 inputDirection = new Vector2(0, 0);
    public boolean moving;
    public float runningSpeed = 1.2f;
    public float originalSpeed;
    public AttackHandler attackHandler;
    public Rectangle attackRect;
    public Fixture actionSensorFixture = null;
    public FacingDirection facingDirection = FacingDirection.SOUTH;
    public boolean hasHitThisAttack = false;
    public Inventory inventory;

    public int woodCount = 0;
    public int stoneCount = 0;

    public Player (Vector2 pos, float width, float height, GameScreen screen) {
        super(pos, width, height, screen);
        this.texture = new Texture("Player.png");
        this.keyHandler = new KeyHandler(this);
        this.originalSpeed = 2f;
        this.attackHandler = new AttackHandler(this);
        this.runningSpeed = originalSpeed * 1.5f;
        this.speed = originalSpeed;
        this.animationHandler = new AnimationHandler(this);
        createBody(screen.box2DWorld);
        screen.box2DWorld.addEntityToMap(this);
        animationHandler.setupAnimations();
        setupSprite();
        this.inventory = new Inventory(this);

        attackRect = new Rectangle();
//        updateAxeCollisionZone();

    }

    public float getCenterX() {
        return this.body.getPosition().x + this.sprite.getWidth() / 2f;
    }
    public float getCenterY () {
        return this.body.getPosition().y + this.sprite.getHeight() / 2f;
    }

    public void setupSprite () {
        this.sprite = new Sprite(idleDown);
        this.sprite.setSize(width, height);
        this.sprite.setPosition(pos.x, pos.y);
    }

    public void applyVelocityFromDirection() {
        if (keyHandler.wantsToRun()){
            speed = runningSpeed;
        } else {
            speed = originalSpeed;
        }
        body.setLinearVelocity(direction.scl(speed));
    }

    public void update (float delta) {

        if (inventory.currentItem != null) {
            if (inventory.currentItem.type == "weapon") {
                switch (inventory.currentItem.name) {
                    case "axe" -> actionItem = ActionItem.AXE;
                    case "pickaxe" -> actionItem = ActionItem.PICKAXE;
                }
            }
        }

        //UPDATE ACTION STATES

//        keyHandler.checkSwitchItem();

        inputDirection.set(0, 0);

        keyHandler.wantsToAttack(delta);

        if (!keyHandler.handleSettingDiagonalMovement()) {
            keyHandler.handleSettingCardinalMovement();
        }
        updateFacingDirection(screen.cursorHandler.currentPos);

        attackHandler.handleAttackLogic();

        if (actionState == ActionState.MOVING) {
            direction.set(inputDirection);
            System.out.println(direction.x + " " + direction.y);
            animationTimer += delta;
            applyVelocityFromDirection();
        } else if (actionState == ActionState.IDLE){
            animationTimer = 0f;
            body.setLinearVelocity(0, 0);
        }

        pos.x = body.getPosition().x - width / 2f;
        pos.y = body.getPosition().y - height / 2f;
        updateSprite();
        animationHandler.updateSpriteAnimation();

    }

    public void updateFacingDirection(Vector2 cursorPos) {
        float dx = cursorPos.x - body.getPosition().x;
        float dy = cursorPos.y - body.getPosition().y   ;

        float angle = MathUtils.atan2(dy, -dx) * MathUtils.radiansToDegrees;

        // Normalize angle to [0, 360)
        if (angle < 0) angle += 360;

        if (angle >= 345 || angle < 15)
            facingDirection = FacingDirection.EAST;
        else if (angle >= 15 && angle < 60)
            facingDirection = FacingDirection.NORTHEAST;
        else if (angle >= 60 && angle < 120)
            facingDirection = FacingDirection.NORTH;
        else if (angle >= 120 && angle < 165)
            facingDirection = FacingDirection.NORTHWEST;
        else if (angle >= 165 && angle < 195)
            facingDirection = FacingDirection.WEST;
        else if (angle >= 195 && angle < 240)
            facingDirection = FacingDirection.SOUTHWEST;
        else if (angle >= 240 && angle < 300)
            facingDirection = FacingDirection.SOUTH;
        else if (angle >= 300 && angle < 345)
            facingDirection = FacingDirection.SOUTHEAST;
    }





}


