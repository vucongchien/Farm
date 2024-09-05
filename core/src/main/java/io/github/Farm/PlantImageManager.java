package io.github.Farm;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class PlantImageManager {
    private Animation<TextureRegion> seedAnimation;
    private Animation<TextureRegion> sproutAnimation;
    private Animation<TextureRegion> youngAnimation;
    private Animation<TextureRegion> matureAnimation;
    private Animation<TextureRegion> harvestedAnimation;
    private Animation<TextureRegion> witherAnimation;

    public PlantImageManager(PlantType type) {
        String basePath = "Plant/" + type.toString() + "/";

        seedAnimation = createAnimation(basePath, "seed", 1);
        sproutAnimation = createAnimation(basePath, "sprout", 3);
        youngAnimation = createAnimation(basePath, "young", 3);
        matureAnimation = createAnimation(basePath, "mature", 3);
        harvestedAnimation = createAnimation(basePath, "harvested", 3);
        witherAnimation = createAnimation(basePath, "wither", 3);
    }

    private Animation<TextureRegion> createAnimation(String basePath, String stageName, int frameCount) {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 1; i <= frameCount; i++) {
            Texture texture = new Texture(basePath + stageName + "_" + i + ".png");

            frames.add(new TextureRegion(texture));
        }
        return new Animation<TextureRegion>(0.2f, frames, Animation.PlayMode.LOOP);
    }

    public Animation<TextureRegion> getAnimation(PlantStage stage) {
        switch (stage) {
            case SEED: return seedAnimation;
            case SPROUT: return sproutAnimation;
            case YOUNG: return youngAnimation;
            case MATURE: return matureAnimation;
            case HARVESTED: return harvestedAnimation;
            case WITHER: return witherAnimation;
            default: return seedAnimation;
        }
    }

    public void dispose() {
        for (TextureRegion region : seedAnimation.getKeyFrames()) {
            region.getTexture().dispose();
        }
        for (TextureRegion region : sproutAnimation.getKeyFrames()) {
            region.getTexture().dispose();
        }
        for (TextureRegion region : youngAnimation.getKeyFrames()) {
            region.getTexture().dispose();
        }
        for (TextureRegion region : matureAnimation.getKeyFrames()) {
            region.getTexture().dispose();
        }
        for (TextureRegion region : harvestedAnimation.getKeyFrames()) {
            region.getTexture().dispose();
        }
        for(TextureRegion region : witherAnimation.getKeyFrames()){
            region.getTexture().dispose();
        }
    }
}
