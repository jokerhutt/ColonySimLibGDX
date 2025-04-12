package entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import constants.Const;
import jokerhut.main.Box2DWorld;
import jokerhut.main.GameScreen;
import manager.KeyHandler;

public class Player extends Entity {

    public TextureRegion idleDown, idleUp, idleLeft, idleRight;
    public Animation<TextureRegion> walkDown, walkUp, walkLeft, walkRight;
    public float animationTimer = 0f;

    KeyHandler keyHandler;
    public float speed;
    public Vector2 direction = new Vector2(0, 1);
    public boolean moving;
    public float runningSpeed = 1.2f;
    public float originalSpeed;

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
    }

    public void setupAnimations () {

        Texture sheet = new Texture ("playerSheet.png");
        TextureRegion[][] split = TextureRegion.split(sheet, 19, 21);
        TextureRegion[] walkLeftFrames = new TextureRegion[] {
            new TextureRegion(split[0][0]),
            new TextureRegion(split[0][1]),
        };


        idleDown = split[7][0];
        idleUp   = split[8][0];

        idleRight = split[7][0];
        idleLeft = new TextureRegion(split[7][0]);
        idleLeft.flip(true, false);

        walkDown = new Animation<>(0.2f, split[0][0],  split[0][1]);
        walkUp   = new Animation<>(0.2f, split[1][0],  split[1][1]);
        walkRight = new Animation<>(0.2f, split[0][0],  split[0][1]);


        for (TextureRegion frame : walkLeftFrames) {
            frame.flip(true, false); // flip horizontally
        }

        walkLeft = new Animation<>(0.2f, walkLeftFrames);

    }

    public void updateSpriteAnimation () {

        if (moving) {

            if (direction.y > 0) {
                sprite.setRegion(walkUp.getKeyFrame(animationTimer, true));
            } else if (direction.y < 0 && direction.x < 0) {
                sprite.setRegion(walkLeft.getKeyFrame(animationTimer, true));
            }else if (direction.y < 0) {
                sprite.setRegion(walkDown.getKeyFrame(animationTimer, true));
            } else if (direction.x > 0 && direction.y == 0) {
                sprite.setRegion(walkRight.getKeyFrame(animationTimer, true));
            } else if (direction.x < 0 && direction.y == 0) {
                sprite.setRegion(walkLeft.getKeyFrame(animationTimer, true));
            }

        } else{
            if (direction.y > 0) {
                sprite.setRegion(idleUp);
            } else if (direction.y < 0 && direction.x < 0) {
                sprite.setRegion(idleLeft);
            } else if (direction.y < 0) {
                sprite.setRegion(idleDown);
            }


            else if (direction.x > 0 && direction.y == 0) {
                System.out.println("Idleright " + direction.x + " " + direction.y);
                sprite.setRegion(idleRight);
            } else if (direction.x < 0 && direction.y == 0) {
                sprite.setRegion(idleLeft);
            }
        }

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

        if (!keyHandler.handleSettingDiagonalMovement()) {
            keyHandler.handleSettingCardinalMovement();
        }

        if (moving) {
            animationTimer += delta;
            applyVelocityFromDirection();
        } else {
            animationTimer = 0f;
            body.setLinearVelocity(0, 0);
        }

        pos.x = body.getPosition().x - width / 2f;
        pos.y = body.getPosition().y - height / 2f;

        updateSprite();
        updateSpriteAnimation();







    }



}


