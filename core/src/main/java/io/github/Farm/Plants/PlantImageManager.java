package io.github.Farm.Plants;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Disposable;

public class PlantImageManager implements Disposable {
    private Texture seedTexture;
    private Texture sproutTexture;
    private Texture youngTexture;
    private Texture matureTexture;
    private Texture harvestedTexture;

    public PlantImageManager(PlantType type) {
        String basePath = "Crops/" + type.toString() ;

        seedTexture = new Texture(basePath + "_00.png");
        sproutTexture = new Texture(basePath + "_01.png");
        youngTexture = new Texture(basePath + "_02.png");
        matureTexture = new Texture(basePath + "_03.png");
        harvestedTexture = new Texture(basePath + "_04.png");
    }

    public Texture getTexture(PlantStage stage) {
        switch (stage) {
            case SEED: return seedTexture;
            case SPROUT: return sproutTexture;
            case YOUNG: return youngTexture;
            case MATURE: return matureTexture;
            case HARVESTED: return harvestedTexture;
            default: return seedTexture;
        }
    }

    @Override
    public void dispose() {
        seedTexture.dispose();
        sproutTexture.dispose();
        youngTexture.dispose();
        matureTexture.dispose();
        harvestedTexture.dispose();
    }
}
