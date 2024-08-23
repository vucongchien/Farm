package io.github.Farm;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class PlayerRenderer {
    private PlayerImageManager imageManager;
    private PlayerController player;

    private float stateTime;


    public PlayerRenderer(PlayerController player, String idleTexturePath, String walkUpSheetPath, String walkDownSheetPath,
                          String walkLeftSheetPath, String walkRightSheetPath, int frameCols, int frameRows, float frameDuration) {
        this.player = player;
        this.imageManager = new PlayerImageManager(idleTexturePath, walkUpSheetPath, walkDownSheetPath, walkLeftSheetPath, walkRightSheetPath, frameCols, frameRows, frameDuration);
        this.stateTime = 0f;
    }

    public void render(SpriteBatch batch) {
        Vector2 position = player.getPosition();
        float size = player.getSize();
        stateTime += player.getDeltaTime();

        TextureRegion currentFrame;

        if (player.isWalking()) {
            String direction = player.getDirection(); // "up", "down", "left", "right"
            Animation<TextureRegion> animation = imageManager.getWalkAnimation(direction);
            currentFrame = animation.getKeyFrame(stateTime, true);
        } else {
            currentFrame = new TextureRegion(imageManager.getIdleTexture());
        }

        batch.draw(currentFrame, position.x, position.y, size, size);
    }

    public void dispose() {
        imageManager.dispose();
    }
}
