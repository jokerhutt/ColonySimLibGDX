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
import jokerhut.main.AnimationHandler;
import jokerhut.main.Box2DWorld;
import jokerhut.main.GameScreen;
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
    KeyHandler keyHandler;
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

    public int woodCount = 0;
    public int stoneCount = 0;

    public Player (Vector2 pos, float width, float height, GameScreen screen) {
        super(pos, width, height, screen);
        this.texture = new Texture("Player.png");
        this.keyHandler = new KeyHandler(this);
        this.originalSpeed = 2f * Const.OGTILESIZE;
        this.attackHandler = new AttackHandler(this);
        this.runningSpeed = originalSpeed * 1.5f;
        this.speed = originalSpeed;
        this.animationHandler = new AnimationHandler(this);
        createBody(screen.box2DWorld);
        screen.box2DWorld.addEntityToMap(this);
        animationHandler.setupAnimations();
        setupSprite();

        attackRect = new Rectangle();
//        updateAxeCollisionZone();

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

        keyHandler.checkSwitchItem();

        inputDirection.set(0, 0);

        keyHandler.wantsToAttack(delta);

        if (!keyHandler.handleSettingDiagonalMovement()) {
            keyHandler.handleSettingCardinalMovement();
        }

        attackHandler.handleAttackLogic();

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
        animationHandler.updateSpriteAnimation();

    }





}


