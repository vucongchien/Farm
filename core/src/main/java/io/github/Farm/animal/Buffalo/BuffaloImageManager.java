package io.github.Farm.animal.Buffalo;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import io.github.Farm.animal.PetState;

import java.util.EnumMap;

public class BuffaloImageManager implements Disposable {
    private final EnumMap<PetState, Animation<TextureRegion>> animations;
    public BuffaloImageManager() {
        animations = new EnumMap<>(PetState.class);

        animations.put(PetState.WALK_RIGHT, BuffaloImageManager.createAnimation("Animal_animation/cow/WhiteCowWalk_Right.png",4,1,0.1f));
        animations.put(PetState.WALK_LEFT, BuffaloImageManager.flipAnimation(animations.get(PetState.WALK_RIGHT)));
        animations.put(PetState.WALK_BACK, BuffaloImageManager.createAnimation("Animal_animation/cow/WhiteCowWalk_Backward.png",4,1,0.1f));
        animations.put(PetState.WALK_FACE, BuffaloImageManager.createAnimation("Animal_animation/cow/WhiteCowWalk_Forward.png",4,1,0.1f));

        animations.put(PetState.IDLE_RIGHT, BuffaloImageManager.createAnimation("Animal_animation/cow/WhiteCowIdle_Right.png",6,1,0.1f));
        animations.put(PetState.IDLE_LEFT, BuffaloImageManager.flipAnimation(animations.get(PetState.IDLE_RIGHT)));
        animations.put(PetState.IDLE_BACK, BuffaloImageManager.createAnimation("Animal_animation/cow/WhiteCowIdle_Back.png",6,1,0.1f));
        animations.put(PetState.IDLE_FACE, BuffaloImageManager.createAnimation("Animal_animation/cow/WhiteCowIdle_Face.png",6,1,0.1f));

        animations.put(PetState.SLEEP_RIGHT, BuffaloImageManager.createAnimation("Animal_animation/cow/WhiteCowSleeping_Right.png",4,1,0.1f));
        animations.put(PetState.SLEEP_LEFT, BuffaloImageManager.flipAnimation(animations.get(PetState.SLEEP_RIGHT)));
        animations.put(PetState.SLEEP_BACK, BuffaloImageManager.createAnimation("Animal_animation/cow/WhiteCowSleeping_Back.png",4,1,0.1f));
        animations.put(PetState.SLEEP_FACE, BuffaloImageManager.createAnimation("Animal_animation/cow/WhiteCowSleeping_Face.png",4,1,0.1f));

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
