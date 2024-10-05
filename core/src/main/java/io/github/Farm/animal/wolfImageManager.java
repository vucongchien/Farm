package io.github.Farm.animal;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.Farm.animal.AnimalImageManager;

import java.util.EnumMap;

public class wolfImageManager {
    private final EnumMap<PetState, Animation<TextureRegion>> animations;
    private final AnimalImageManager playerImageManager;
    public wolfImageManager() {
        animations = new EnumMap<>(PetState.class);
        playerImageManager = new AnimalImageManager();

        animations.put(PetState.WALK_LEFT,playerImageManager.createAnimation("Animal_animation/1/wolf/S_Walk.png",6,1,0.1f));
        animations.put(PetState.WALK_RIGHT, playerImageManager.flipAnimation(animations.get(PetState.WALK_LEFT)));
        animations.put(PetState.WALK_BACK,playerImageManager.createAnimation("Animal_animation/1/cow/WhiteCowWalk_Backward.png",4,1,0.1f));
        animations.put(PetState.WALK_FACE,playerImageManager.createAnimation("Animal_animation/1/wolf/D_Walk.png",6,1,0.1f));

        animations.put(PetState.IDLE_RIGHT,playerImageManager.createAnimation("Animal_animation/1/wolf/S_Attack.png",6,1,0.1f));
        animations.put(PetState.IDLE_LEFT,playerImageManager.flipAnimation(animations.get(PetState.IDLE_RIGHT)));
        animations.put(PetState.IDLE_BACK,playerImageManager.createAnimation("Animal_animation/1/cow/WhiteCowIdle_Back.png",6,1,0.1f));
        animations.put(PetState.IDLE_FACE,playerImageManager.createAnimation("Animal_animation/1/cow/WhiteCowIdle_Face.png",6,1,0.1f));

        animations.put(PetState.SLEEP_RIGHT,playerImageManager.createAnimation("Animal_animation/1/cow/WhiteCowSleeping_Right.png",4,1,0.1f));
        animations.put(PetState.SLEEP_LEFT,playerImageManager.flipAnimation(animations.get(PetState.SLEEP_RIGHT)));
        animations.put(PetState.SLEEP_BACK,playerImageManager.createAnimation("Animal_animation/1/cow/WhiteCowSleeping_Back.png",4,1,0.1f));
        animations.put(PetState.SLEEP_FACE,playerImageManager.createAnimation("Animal_animation/1/cow/WhiteCowSleeping_Face.png",4,1,0.1f));

    }

    public Animation<TextureRegion> getAnimation(PetState petState) {
        return animations.get(petState);
    }
}
