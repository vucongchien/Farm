package io.github.Farm;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;

public class PlayerRenderer {
    private PlayerController player;
    private Animation<TextureRegion> currentAnimation;
    private PlayerImageManager imageManager;
    private float stateTime;
    private int size;  // Kích thước nhân vật

    public PlayerRenderer(PlayerController player, String idleUpSheetPath, String idleDownSheetPath,
                          String idleLeftSheetPath, String idleRightSheetPath,
                          String walkUpSheetPath, String walkDownSheetPath,
                          String walkLeftSheetPath, String walkRightSheetPath,
                          int frameCols, int frameRows, float frameDuration, int initialSize) {
        this.player = player;
        this.size = initialSize;  // Kích thước khởi tạo của nhân vật

        // Khởi tạo quản lý hình ảnh nhân vật
        imageManager = new PlayerImageManager(idleUpSheetPath, idleDownSheetPath, idleLeftSheetPath, idleRightSheetPath,
            walkUpSheetPath, walkDownSheetPath, walkLeftSheetPath, walkRightSheetPath,
            frameCols, frameRows, frameDuration);
        currentAnimation = imageManager.getIdleAnimation("d_idle");
        stateTime = 0f;
    }

    public void render(SpriteBatch batch) {
        stateTime += player.getDeltaTime();
        updateAnimation();

        TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, true);
        Vector2 position = player.getPosition();

        batch.draw(currentFrame, position.x, position.y, size, size);
    }

    private void updateAnimation() {
        // Cập nhật animation dựa trên trạng thái của nhân vật
        String direction = player.getDirection();
        if (player.isWalking()) {
            currentAnimation = imageManager.getWalkAnimation(direction);
        } else {
            currentAnimation = imageManager.getIdleAnimation(direction);
        }
    }

    public void setSize(int newSize) {
        this.size = newSize;  // Cập nhật kích thước nhân vật
    }

    public void dispose() {
        imageManager.dispose();
    }
}
