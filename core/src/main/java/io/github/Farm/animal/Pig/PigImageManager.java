package io.github.Farm.animal.Pig;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import io.github.Farm.animal.PetState;

import java.util.EnumMap;

public class PigImageManager implements Disposable {
    private final EnumMap<PetState, Animation<TextureRegion>> animations;
    public PigImageManager() {
        animations = new EnumMap<>(PetState.class);

        animations.put(PetState.WALK_RIGHT, createAnimation("Animal_animation/Pig/Pig_1/BabyPigWalk_1.png",4,1,0.2f));
        animations.put(PetState.WALK_LEFT, flipAnimation(animations.get(PetState.WALK_RIGHT)));


        animations.put(PetState.IDLE_RIGHT, createAnimation("Animal_animation/Pig/Pig_1/BabyPigSleep_1.png",2,1,0.1f));
        animations.put(PetState.IDLE_LEFT, flipAnimation(animations.get(PetState.IDLE_RIGHT)));


        animations.put(PetState.SLEEP_LEFT, createAnimation("Animal_animation/Pig/Pig_1/BabyPigSleep_1.png",2,1,0.5f));
        animations.put(PetState.SLEEP_RIGHT, flipAnimation(animations.get(PetState.SLEEP_LEFT)));

        animations.put(PetState.EAT_LEFT,createAnimation("Animal_animation/Pig/Pig_1/BabyPigEating_1.png",2,1,0.1f));
        animations.put(PetState.EAT_RIGHT,flipAnimation(animations.get(PetState.EAT_LEFT)));


    }

    public static Animation<TextureRegion> createAnimation(String sheetPath, int frameCols, int frameRows, float frameDuration) {
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
    public static Animation<TextureRegion> flipAnimation(Animation<TextureRegion> original) {
        Array<TextureRegion> flippedFrames = new Array<>();
        for (TextureRegion frame : original.getKeyFrames()) {
            TextureRegion flippedFrame = new TextureRegion(frame);
            flippedFrame.flip(true, false);  // Flip horizontally
            flippedFrames.add(flippedFrame);
        }
        return new Animation<>(original.getFrameDuration(), flippedFrames);
    }


    public Animation<TextureRegion> getAnimation(PetState petState) {
        return animations.get(petState);
    }


    @Override
    public void dispose() {
        // Giải phóng tất cả các Texture trong các Animation
        for (Animation<TextureRegion> animation : animations.values()) {
            if (animation != null) {
                // Lấy mảng các key frames từ Animation
                Object[] keyFrames = animation.getKeyFrames();
                for (Object keyFrame : keyFrames) {
                    if (keyFrame instanceof TextureRegion) {
                        TextureRegion frame = (TextureRegion) keyFrame;
                        Texture texture = frame.getTexture();
                        if (texture != null) {
                            texture.dispose(); // Giải phóng Texture
                        }
                    }
                }
            }
        }
    }
}
