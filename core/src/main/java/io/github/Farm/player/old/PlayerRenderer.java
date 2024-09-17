package io.github.Farm.player.old;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import io.github.Farm.player.PlayerState;

public class PlayerRenderer {
    private final PlayerController player;
    private final PlayerImageManager imageManager;
    private Animation<TextureRegion> currentAnimation;
    private float stateTime;
    private int size;
    private PlayerState lastState;

    public PlayerRenderer(PlayerController player, PlayerImageManager imageManager, int initialSize) {
        this.player = player;
        this.imageManager = imageManager;
        this.size = initialSize;
        this.currentAnimation = imageManager.getAnimation(PlayerState.IDLE_RIGHT);
        this.lastState=PlayerState.IDLE_RIGHT;
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        stateTime += player.getDeltaTime();
        updateAnimation();
        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, true);
        // Draw the player with the correct size
        batch.draw(frame, player.getPosition().x-32, player.getPosition().y-30, size, size);

        batch.end();
    }

    private void updateAnimation() {
        PlayerState currentState = player.getCurrentState();
        currentAnimation = imageManager.getAnimation(currentState);
        if (currentState !=lastState) {
            // If state changes, reset stateTime to start animation from frame 0
            stateTime = 0f;
            currentAnimation = imageManager.getAnimation(currentState);
            lastState=currentState;
        }
    }

    public void setSize(int newSize) {
        this.size = newSize;
    }

    public void dispose() {
        imageManager.dispose();
    }
}

