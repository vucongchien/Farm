package io.github.Farm.animal.WOLF;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Disposable;
import io.github.Farm.animal.PetState;

import java.util.EnumMap;

public class WolfImageManager implements Disposable {
    private final EnumMap<PetState, Animation<TextureRegion>> animations;
    public WolfImageManager() {
        animations = new EnumMap<>(PetState.class);


        animations.put(PetState.WALK_RIGHT,createAnimation("Goblin/spr_run_strip8.png",8,1,0.05f));
        animations.put(PetState. WALK_LEFT,flipAnimation(animations.get(PetState.WALK_RIGHT)));

        animations.put(PetState.IDLE_RIGHT,createAnimation("Goblin/spr_idle_strip9.png",8,1,0.04f));
        animations.put(PetState.IDLE_LEFT,flipAnimation(animations.get(PetState.IDLE_RIGHT)));


        animations.put(PetState.ATTACK_RIGHT,createAnimation("Goblin/spr_axe_strip10.png",10,1,0.04f));
        animations.put(PetState.ATTACK_LEFT,flipAnimation(animations.get(PetState.ATTACK_RIGHT)));

        animations.put(PetState.HURT_RIGHT,createAnimation("Goblin/spr_hurt_strip8.png",8,1,0.04f));
        animations.put(PetState.HURT_LEFT,flipAnimation(animations.get(PetState.HURT_RIGHT)));

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
