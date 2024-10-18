package io.github.Farm.animal;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.utils.Disableable;
import com.badlogic.gdx.utils.Disposable;
import io.github.Farm.animal.Buffalo.BuffaloImageManager;

import java.util.EnumMap;

public class WolfImageManager implements Disposable {
    private final EnumMap<PetState, Animation<TextureRegion>> animations;
    private final BuffaloImageManager playerImageManager;
    public WolfImageManager() {
        animations = new EnumMap<>(PetState.class);
        playerImageManager = new BuffaloImageManager();

        animations.put(PetState.WALK_LEFT,playerImageManager.createAnimation("Animal_animation/wolf/S_Walk.png",6,1,0.1f));
        animations.put(PetState.WALK_RIGHT, playerImageManager.flipAnimation(animations.get(PetState.WALK_LEFT)));
        animations.put(PetState.WALK_BACK,playerImageManager.createAnimation("Animal_animation/cow/WhiteCowWalk_Backward.png",4,1,0.1f));
        animations.put(PetState.WALK_FACE,playerImageManager.createAnimation("Animal_animation/wolf/D_Walk.png",6,1,0.1f));

        animations.put(PetState.IDLE_RIGHT,playerImageManager.createAnimation("Animal_animation/wolf/D_Attack.png",6,1,0.1f));
        animations.put(PetState.IDLE_LEFT,playerImageManager.flipAnimation(animations.get(PetState.IDLE_RIGHT)));
        animations.put(PetState.IDLE_BACK,playerImageManager.createAnimation("Animal_animation/cow/WhiteCowIdle_Back.png",6,1,0.1f));
        animations.put(PetState.IDLE_FACE,playerImageManager.createAnimation("Animal_animation/wolf/D_Attack.png",6,1,0.1f));

        animations.put(PetState.SLEEP_RIGHT,playerImageManager.createAnimation("Animal_animation/cow/WhiteCowSleeping_Right.png",4,1,0.1f));
        animations.put(PetState.SLEEP_LEFT,playerImageManager.flipAnimation(animations.get(PetState.SLEEP_RIGHT)));
        animations.put(PetState.SLEEP_BACK,playerImageManager.createAnimation("Animal_animation/cow/WhiteCowSleeping_Back.png",4,1,0.1f));
        animations.put(PetState.SLEEP_FACE,playerImageManager.createAnimation("Animal_animation/cow/WhiteCowSleeping_Face.png",4,1,0.1f));

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
