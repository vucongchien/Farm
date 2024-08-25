package io.github.Farm;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

public class PlayerRenderer {
    private final PlayerController player;
    private final PlayerImageManager imageManager;
    private Animation<TextureRegion> currentAnimation;
    private float stateTime;
    private int size;

    public PlayerRenderer(PlayerController player, String idleUpSheetPath, String idleDownSheetPath,
                          String idleLeftSheetPath, String idleRightSheetPath,
                          String walkUpSheetPath, String walkDownSheetPath,
                          String walkLeftSheetPath, String walkRightSheetPath,
                          int frameCols, int frameRows, float frameDuration, int initialSize) {
        this.player = player;
        this.size = initialSize;
        this.imageManager = new PlayerImageManager(idleUpSheetPath, idleDownSheetPath, idleLeftSheetPath, idleRightSheetPath,
            walkUpSheetPath, walkDownSheetPath, walkLeftSheetPath, walkRightSheetPath, frameCols, frameRows, frameDuration);
        this.currentAnimation = imageManager.getIdleAnimation("d_idle");
    }

    public void render(SpriteBatch batch) {
        stateTime += player.getDeltaTime();
        updateAnimation();
        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, true);
        batch.draw(frame, player.getPosition().x, player.getPosition().y, size, size);
    }

    private void updateAnimation() {
        currentAnimation = player.isWalking() ?
            imageManager.getWalkAnimation(player.getDirection()) :
            imageManager.getIdleAnimation(player.getDirection());
    }

    public void setSize(int newSize) {
        this.size = newSize;
    }

    public void dispose() {
        imageManager.dispose();
    }
}
