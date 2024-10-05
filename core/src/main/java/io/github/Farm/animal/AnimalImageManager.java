package io.github.Farm.animal;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import java.util.EnumMap;

public class AnimalImageManager {
    private final EnumMap<PetState, Animation<TextureRegion>> animations;
    public AnimalImageManager() {
        animations = new EnumMap<>(PetState.class);

        animations.put(PetState.WALK_RIGHT,AnimalImageManager.createAnimation("Animal_animation/1/cow/WhiteCowWalk_Right.png",4,1,0.1f));
        animations.put(PetState.WALK_LEFT, AnimalImageManager.flipAnimation(animations.get(PetState.WALK_RIGHT)));
        animations.put(PetState.WALK_BACK,AnimalImageManager.createAnimation("Animal_animation/1/cow/WhiteCowWalk_Backward.png",4,1,0.1f));
        animations.put(PetState.WALK_FACE,AnimalImageManager.createAnimation("Animal_animation/1/cow/WhiteCowWalk_Forward.png",4,1,0.1f));

        animations.put(PetState.IDLE_RIGHT,AnimalImageManager.createAnimation("Animal_animation/1/cow/WhiteCowIdle_Right.png",6,1,0.1f));
        animations.put(PetState.IDLE_LEFT,AnimalImageManager.flipAnimation(animations.get(PetState.IDLE_RIGHT)));
        animations.put(PetState.IDLE_BACK,AnimalImageManager.createAnimation("Animal_animation/1/cow/WhiteCowIdle_Back.png",6,1,0.1f));
        animations.put(PetState.IDLE_FACE,AnimalImageManager.createAnimation("Animal_animation/1/cow/WhiteCowIdle_Face.png",6,1,0.1f));

        animations.put(PetState.SLEEP_RIGHT, AnimalImageManager.createAnimation("Animal_animation/1/cow/WhiteCowSleeping_Right.png",4,1,0.1f));
        animations.put(PetState.SLEEP_LEFT,AnimalImageManager.flipAnimation(animations.get(PetState.SLEEP_RIGHT)));
        animations.put(PetState.SLEEP_BACK,AnimalImageManager.createAnimation("Animal_animation/1/cow/WhiteCowSleeping_Back.png",4,1,0.1f));
        animations.put(PetState.SLEEP_FACE,AnimalImageManager.createAnimation("Animal_animation/1/cow/WhiteCowSleeping_Face.png",4,1,0.1f));

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
}
