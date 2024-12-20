package io.github.Farm.player;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import io.github.Farm.Interface.RenderableEntity;
import io.github.Farm.player.PLAYER_STATE.PlayerState;
import io.github.Farm.ui.Other.SelectionBox;

public class PlayerRenderer implements RenderableEntity, Disposable {
    private final PlayerController player;
    private final PlayerImageManager imageManager;
    private Animation<TextureRegion> currentAnimation;
    private float stateTime;
    private int size;
    private PlayerState lastState;


    public PlayerRenderer(PlayerController player, PlayerImageManager imageManager, int initialSize){
        this.player=player;
        this.imageManager = imageManager;
        this.size = initialSize;
        this.currentAnimation = imageManager.getAnimation(PlayerState.IDLE_RIGHT);
        this.lastState=PlayerState.IDLE_RIGHT;

    }

    @Override
    public float getY() {
        return player.getPosition().y;
    }

    @Override
    public void render(SpriteBatch batch, Camera camera) {
        batch.setProjectionMatrix(camera.combined);
        stateTime += player.getDeltaTime();
        updateAnimation();
        batch.begin();

        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, true);
        batch.setProjectionMatrix(camera.combined);
        batch.draw(frame, player.getPosition().x-32, player.getPosition().y-28, size, size);
        batch.end();
    }

    private void updateAnimation() {
        PlayerState currentState = PlayerState.valueOf(player.getCurrentState());
        if (currentState !=lastState) {
            stateTime = 0f;
            currentAnimation = imageManager.getAnimation(currentState);
            lastState=currentState;
        }
    }

    public void setSize(int newSize) {
        this.size = newSize;
    }

    @Override
    public void dispose() {
        imageManager.dispose();
        player.dispose();
    }
}
