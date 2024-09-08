package io.github.Farm.player;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;

public class PlayerRenderer {
    private final PlayerController player;
    private final PlayerImageManager imageManager;
    private Animation<TextureRegion> currentAnimation;
    private float stateTime;
    private int size;

    public PlayerRenderer(PlayerController player, PlayerImageManager imageManager, int initialSize) {
        this.player = player;
        this.imageManager = imageManager;
        this.size = initialSize;
        this.currentAnimation = imageManager.getAnimation(PlayerState.IDLE_RIGHT);
    }

    public void render(SpriteBatch batch) {
        stateTime += player.getDeltaTime();
        updateAnimation();
        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, true);
        batch.draw(frame, player.getPosition().x, player.getPosition().y, size, size);
    }

    private void updateAnimation() {
        PlayerState state = player.getCurrentState();
        currentAnimation = imageManager.getAnimation(state);
    }

    public void setSize(int newSize) {
        this.size = newSize;
    }

    public void dispose() {
        imageManager.dispose();
    }
}
