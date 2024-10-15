package io.github.Farm.player;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import io.github.Farm.player.PLAYER_STATE.PlayerState;

import java.util.EnumMap;

public class PlayerImageManager implements Disposable {
    private final EnumMap<PlayerState, Animation<TextureRegion>> animations;

    public PlayerImageManager() {
        animations = new EnumMap<>(PlayerState.class);

        animations.put(PlayerState.IDLE_RIGHT, createAnimation("Player_animation/spr_idle_strip9.png", 9, 1, 0.03f));
        animations.put(PlayerState.IDLE_LEFT, flipAnimation(animations.get(PlayerState.IDLE_RIGHT)));

        animations.put(PlayerState.WALK_RIGHT, createAnimation("Player_animation/spr_run_strip8.png", 8, 1, 0.04f));
        animations.put(PlayerState.WALK_LEFT, flipAnimation(animations.get(PlayerState.WALK_RIGHT)));

        animations.put(PlayerState.DIG_RIGHT, createAnimation("Player_animation/spr_dig_strip13.png", 13, 1, 0.02f));
        animations.put(PlayerState.DIG_LEFT, flipAnimation(animations.get(PlayerState.DIG_RIGHT)));

        animations.put(PlayerState.HIT_RIGHT, createAnimation("Player_animation/spr_sword_strip10.png", 10, 1, 0.05f));
        animations.put(PlayerState.HIT_LEFT, flipAnimation(animations.get(PlayerState.HIT_RIGHT)));

        animations.put(PlayerState.AXE_RIGHT, createAnimation("Player_animation/spr_axe_strip10.png", 10, 1, 0.03f));
        animations.put(PlayerState.AXE_LEFT, flipAnimation(animations.get(PlayerState.AXE_RIGHT)));

        animations.put(PlayerState.DOING_RIGHT, createAnimation("Player_animation/spr_doing_strip8.png", 8, 1, 0.04f));
        animations.put(PlayerState.DOING_LEFT, flipAnimation(animations.get(PlayerState.DOING_RIGHT)));

        animations.put(PlayerState.WATER_RIGHT, createAnimation("Player_animation/spr_watering_strip5.png", 5, 1, 0.02f));
        animations.put(PlayerState.WATER_LEFT, flipAnimation(animations.get(PlayerState.WATER_RIGHT)));

        animations.put(PlayerState.HAMMER_RIGHT, createAnimation("Player_animation/spr_hammering_strip23.png", 23, 1, 0.02f));
        animations.put(PlayerState.HAMMER_LEFT, flipAnimation(animations.get(PlayerState.HAMMER_RIGHT)));

        animations.put(PlayerState.SWIM_RIGHT, createAnimation("Player_animation/spr_swimming_strip12.png", 12, 1, 0.02f));
        animations.put(PlayerState.SWIM_LEFT, flipAnimation(animations.get(PlayerState.SWIM_RIGHT)));

        animations.put(PlayerState.CASTING_RIGHT, createAnimation("Player_animation/spr_casting_strip15.png", 15, 1, 0.04f));
        animations.put(PlayerState.CASTING_LEFT, flipAnimation(animations.get(PlayerState.CASTING_RIGHT)));

        animations.put(PlayerState.CAUGHT_RIGHT, createAnimation("Player_animation/spr_caught_strip10.png", 10, 1, 0.04f));
        animations.put(PlayerState.CAUGHT_LEFT, flipAnimation(animations.get(PlayerState.CAUGHT_RIGHT)));

        animations.put(PlayerState.WAITING_RIGHT, createAnimation("Player_animation/spr_waiting_strip9.png", 9, 1, 0.04f));
        animations.put(PlayerState.WAITING_LEFT, flipAnimation(animations.get(PlayerState.WAITING_RIGHT)));

        animations.put(PlayerState.HURT_RIGHT,createAnimation("Player_animation/spr_hurt_strip8.png",8,1,0.04f));
        animations.put(PlayerState.HURT_LEFT,flipAnimation(animations.get(PlayerState.HURT_RIGHT)));
    }

    private Animation<TextureRegion> createAnimation(String sheetPath, int frameCols, int frameRows, float frameDuration) {
        Texture sheetTexture = new Texture(sheetPath);
        int originalFrameWidth = 96;
        int originalFrameHeight = 64;
        int frameWidth = 64;
        int frameHeight = 64;
        int cropWidth = (originalFrameWidth - frameWidth) / 2;
        int cropHeight = (originalFrameHeight - frameHeight) / 2;

        TextureRegion[][] tmpFrames = TextureRegion.split(sheetTexture, originalFrameWidth, originalFrameHeight);
        TextureRegion[] frames = new TextureRegion[frameCols * frameRows];

        int index = 0;
        for (int i = 0; i < frameRows; i++) {
            for (int j = 0; j < frameCols; j++) {
                TextureRegion originalFrame = tmpFrames[i][j];
                TextureRegion croppedFrame = new TextureRegion(originalFrame, cropWidth, cropHeight, frameWidth, frameHeight);
                frames[index++] = croppedFrame;
            }
        }

        return new Animation<>(frameDuration, new Array<>(frames));
    }

    private Animation<TextureRegion> flipAnimation(Animation<TextureRegion> original) {
        Array<TextureRegion> flippedFrames = new Array<>();
        for (TextureRegion frame : original.getKeyFrames()) {
            TextureRegion flippedFrame = new TextureRegion(frame);
            flippedFrame.flip(true, false);
            flippedFrames.add(flippedFrame);
        }
        return new Animation<>(original.getFrameDuration(), flippedFrames);
    }

    public Animation<TextureRegion> getAnimation(PlayerState state) {
        return animations.get(state);
    }

    @Override
    public void dispose() {
        for (Animation<TextureRegion> animation : animations.values()) {
            if (animation != null) {
                for (TextureRegion frame : animation.getKeyFrames()) {
                    if (frame != null && frame.getTexture() != null) {
                        frame.getTexture().dispose();
                    }
                }
            }
        }

    }
}
