package jokerhut.main;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import entity.ActionItem;
import entity.ActionState;
import entity.FacingDirection;
import entity.Player;

public class AnimationHandler {

    Player player;

    public AnimationHandler (Player player) {

        this.player = player;

    }

    public void setupAnimations () {

        Texture sheet = new Texture ("playerSheetNew.png");
        TextureRegion[][] split = TextureRegion.split(sheet, 32, 32);

        player.idleDown = split[0][0];
        player.idleUp   = split[4][0];

        player.idleRight = split[2][0];
        player.idleLeft = split[6][0];

        player.idleUpRight = split[3][0];
        player.idleUpLeft = split[5][0];
        player.idleDownRight = split[1][0];
        player.idleDownLeft = split[7][0];

        player.walkDown = new Animation<>(0.2f, split[0][2], split[0][3],  split[0][4]);
        player.walkUp   = new Animation<>(0.2f, split[4][2], split[4][3],  split[4][4]);
        player.walkRight = new Animation<>(0.2f, split[2][2], split[2][3],  split[2][4]);
        player.walkLeft = new Animation<>(0.2f, split[6][2], split[6][3],  split[6][4]);

        player.walkUpRight = new Animation<>(0.2f, split[3][2], split[3][3],  split[3][4]);
        player.walkUpLeft   = new Animation<>(0.2f, split[5][2], split[5][3],  split[5][4]);
        player.walkDownRight = new Animation<>(0.2f, split[1][2], split[1][3],  split[1][4]);
        player.walkDownLeft = new Animation<>(0.2f, split[7][2], split[7][3],  split[7][4]);

        player.axeDown = new Animation<>(0.2f, split[0][13], split[0][14],  split[0][15]);
        player.axeUp   = new Animation<>(0.2f, split[4][13], split[4][14],  split[4][15]);
        player.axeRight = new Animation<>(0.2f, split[2][13], split[2][14],  split[2][15]);
        player.axeLeft = new Animation<>(0.2f, split[6][13], split[6][14],  split[6][15]);

        player.axeUpRight = new Animation<>(0.2f, split[3][13], split[3][14],  split[3][15]);
        player.axeUpLeft   = new Animation<>(0.2f, split[5][13], split[5][14],  split[5][15]);
        player.axeDownRight = new Animation<>(0.2f, split[1][13], split[1][14],  split[1][15]);
        player.axeDownLeft = new Animation<>(0.2f, split[7][13], split[7][14],  split[7][15]);

        player.pickaxeDown = new Animation<>(0.2f, split[0][6], split[0][7],  split[0][8]);
        player.pickaxeUp   = new Animation<>(0.2f, split[4][6], split[4][7],  split[4][8]);
        player.pickaxeRight = new Animation<>(0.2f, split[2][6], split[2][7],  split[2][8]);
        player.pickaxeLeft = new Animation<>(0.2f, split[6][6], split[6][7],  split[6][8]);

        player.pickaxeUpRight = new Animation<>(0.2f, split[3][6], split[3][7],  split[3][8]);
        player.pickaxeUpLeft   = new Animation<>(0.2f, split[5][6], split[5][7],  split[5][8]);
        player.pickaxeDownRight = new Animation<>(0.2f, split[1][6], split[1][7],  split[1][8]);
        player.pickaxeDownLeft = new Animation<>(0.2f, split[7][6], split[7][7],  split[7][8]);


    }

    public void updateSpriteAnimation () {

        if (player.actionState == ActionState.MOVING) {

            if (player.facingDirection == FacingDirection.NORTH) {
                player.sprite.setRegion(player.walkUp.getKeyFrame(player.animationTimer, true));
            } else if (player.facingDirection == FacingDirection.EAST) {
                player.sprite.setRegion(player.walkLeft.getKeyFrame(player.animationTimer, true));
            } else if (player.facingDirection == FacingDirection.SOUTH) {
                player.sprite.setRegion(player.walkDown.getKeyFrame(player.animationTimer, true));
            } else if (player.facingDirection == FacingDirection.WEST) {
                player.sprite.setRegion(player.walkRight.getKeyFrame(player.animationTimer, true));
            }

            else if (player.facingDirection == FacingDirection.NORTHEAST) {
                player.sprite.setRegion(player.walkUpLeft.getKeyFrame(player.animationTimer, true));
            } else if (player.facingDirection == FacingDirection.NORTHWEST) {
                player.sprite.setRegion(player.walkUpRight.getKeyFrame(player.animationTimer, true));
            } else if (player.facingDirection == FacingDirection.SOUTHEAST) {
                player.sprite.setRegion(player.walkDownLeft.getKeyFrame(player.animationTimer, true));
            } else if (player.facingDirection == FacingDirection.SOUTHWEST) {
                player.sprite.setRegion(player.walkDownRight.getKeyFrame(player.animationTimer, true));
            }

        } else if (player.actionState == ActionState.IDLE) {

            if (player.facingDirection == FacingDirection.NORTH) {
                player.sprite.setRegion(player.idleUp);
            } else if (player.facingDirection == FacingDirection.EAST) {
                player.sprite.setRegion(player.idleLeft);
            } else if (player.facingDirection == FacingDirection.SOUTH) {
                player.sprite.setRegion(player.idleDown);
            } else if (player.facingDirection == FacingDirection.WEST) {
                player.sprite.setRegion(player.idleRight);
            } else if (player.facingDirection == FacingDirection.NORTHWEST) {
                player.sprite.setRegion(player.idleUpRight);
            } else if (player.facingDirection == FacingDirection.NORTHEAST) {
                player.sprite.setRegion(player.idleUpLeft);
            } else if (player.facingDirection == FacingDirection.SOUTHWEST) {
                player.sprite.setRegion(player.idleDownRight);
            } else if (player.facingDirection == FacingDirection.SOUTHEAST) {
                player.sprite.setRegion(player.idleDownLeft);
            }

        } else if (player.actionState == ActionState.ATTACKING) {

            if (player.actionItem == ActionItem.AXE) {
                if (player.facingDirection == FacingDirection.NORTH) {
                    player.sprite.setRegion(player.axeUp.getKeyFrame(player.animationTimer, true));
                } else if (player.facingDirection == FacingDirection.EAST) {
                    player.sprite.setRegion(player.axeLeft.getKeyFrame(player.animationTimer, true));
                } else if (player.facingDirection == FacingDirection.SOUTH) {
                    player.sprite.setRegion(player.axeDown.getKeyFrame(player.animationTimer, true));
                } else if (player.facingDirection == FacingDirection.WEST) {
                    player.sprite.setRegion(player.axeRight.getKeyFrame(player.animationTimer, true));
                } else if (player.facingDirection == FacingDirection.NORTHEAST) {
                    player.sprite.setRegion(player.axeUpLeft.getKeyFrame(player.animationTimer, true));
                } else if (player.facingDirection == FacingDirection.NORTHWEST) {
                    player.sprite.setRegion(player.axeUpRight.getKeyFrame(player.animationTimer, true));
                } else if (player.facingDirection == FacingDirection.SOUTHEAST) {
                    player.sprite.setRegion(player.axeDownLeft.getKeyFrame(player.animationTimer, true));
                } else if (player.facingDirection == FacingDirection.SOUTHWEST) {
                    player.sprite.setRegion(player.axeDownRight.getKeyFrame(player.animationTimer, true));
                }

            } else if (player.actionItem == ActionItem.PICKAXE) {
                if (player.facingDirection == FacingDirection.NORTH) {
                    player.sprite.setRegion(player.pickaxeUp.getKeyFrame(player.animationTimer, true));
                } else if (player.facingDirection == FacingDirection.EAST) {
                    player.sprite.setRegion(player.pickaxeLeft.getKeyFrame(player.animationTimer, true));
                } else if (player.facingDirection == FacingDirection.SOUTH) {
                    player.sprite.setRegion(player.pickaxeDown.getKeyFrame(player.animationTimer, true));
                } else if (player.facingDirection == FacingDirection.WEST) {
                    player.sprite.setRegion(player.pickaxeRight.getKeyFrame(player.animationTimer, true));
                } else if (player.facingDirection == FacingDirection.NORTHEAST) {
                    player.sprite.setRegion(player.pickaxeUpLeft.getKeyFrame(player.animationTimer, true));
                } else if (player.facingDirection == FacingDirection.NORTHWEST) {
                    player.sprite.setRegion(player.pickaxeUpRight.getKeyFrame(player.animationTimer, true));
                } else if (player.facingDirection == FacingDirection.SOUTHEAST) {
                    player.sprite.setRegion(player.pickaxeDownLeft.getKeyFrame(player.animationTimer, true));
                } else if (player.facingDirection == FacingDirection.SOUTHWEST) {
                    player.sprite.setRegion(player.pickaxeDownRight.getKeyFrame(player.animationTimer, true));
                }
            }
        }
    }

}
