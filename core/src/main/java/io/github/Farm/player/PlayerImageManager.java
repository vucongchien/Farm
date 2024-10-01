package io.github.Farm.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

import java.util.EnumMap;

public class PlayerImageManager {
    private final EnumMap<PlayerState, Animation<TextureRegion>> animations;

    public PlayerImageManager( ) {
        animations = new EnumMap<>(PlayerState.class);


        animations.put(PlayerState.IDLE_RIGHT, createAnimation("Player_animation/spr_idle_strip9.png", 9, 1, 0.03f));
        animations.put(PlayerState.IDLE_LEFT,flipAnimation(animations.get(PlayerState.IDLE_RIGHT)));

        animations.put(PlayerState.WALK_RIGHT, createAnimation("Player_animation/spr_run_strip8.png", 8, 1, 0.04f));
        animations.put(PlayerState.WALK_LEFT, flipAnimation(animations.get(PlayerState.WALK_RIGHT)));

        animations.put(PlayerState.DIG_RIGHT, createAnimation("Player_animation/spr_dig_strip13.png", 13, 1, 0.02f));
        animations.put(PlayerState.DIG_LEFT, flipAnimation(animations.get(PlayerState.DIG_RIGHT)));
    }

    public Animation<TextureRegion> createAnimation(String sheetPath, int frameCols, int frameRows, float frameDuration) {
        Texture sheetTexture = new Texture(sheetPath);
        TextureRegion[][] tmpFrames = TextureRegion.split(sheetTexture, sheetTexture.getWidth() / frameCols, sheetTexture.getHeight() / frameRows);

        TextureRegion[] frames = new TextureRegion[frameCols * frameRows];
        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                frames[index++] = tmpFrames[i][j];
            }
        }

        return new Animation<>(frameDuration, new Array<>(frames));
    }
    public Animation<TextureRegion> flipAnimation(Animation<TextureRegion> original) {
        Array<TextureRegion> flippedFrames = new Array<>();
        for (TextureRegion frame : original.getKeyFrames()) {
            TextureRegion flippedFrame = new TextureRegion(frame);
            flippedFrame.flip(true, false);  // Flip horizontally
            flippedFrames.add(flippedFrame);
        }
        return new Animation<>(original.getFrameDuration(), flippedFrames);
    }


    public Animation<TextureRegion> getAnimation(PlayerState state) {
        return animations.get(state);
    }

    public void dispose() {
        for (Animation<TextureRegion> animation : animations.values()) {
            if (animation != null) {
                for (TextureRegion frame : animation.getKeyFrames()) {
                    if (frame.getTexture() != null) {
                        frame.getTexture().dispose();
                    }
                }
            }
        }
    }
}
