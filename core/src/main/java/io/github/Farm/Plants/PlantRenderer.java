package io.github.Farm.Plants;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

public class PlantRenderer {
    private Vector2 position;
    private PlantType type;
    private PlantStage stage;
    private long plantTime;
    private long lastStageChangeTime;
    private boolean isWatered;
    private boolean isHarvestable;

    private static final long WATERING_DURATION = 30000;
    private static final long GROWTH_TIME = 60000;
    private static final long WITHER_TIME = 45000;


    private Animation<TextureRegion> currentAnimation;
    private PlantImageManager imageManager;
    private float stateTime;


    private float width = 16f;
    private float height = 16f;

    public PlantRenderer(Vector2 position, PlantType type) {
        this.position = position;
        this.type = type;
        this.stage = PlantStage.SEED;
        this.plantTime = TimeUtils.millis();
        this.lastStageChangeTime = plantTime;
        this.isWatered = false;
        this.isHarvestable = false;
        this.stateTime = 0f;

        imageManager = new PlantImageManager(type);
        currentAnimation = imageManager.getAnimation(PlantStage.SEED);
    }

    public void update(float deltaTime) {
        stateTime += deltaTime;

        if (isWatered) {
            if (TimeUtils.timeSinceMillis(lastStageChangeTime) > GROWTH_TIME) {
                advanceGrowthStage();
            }
        } else if (TimeUtils.timeSinceMillis(lastStageChangeTime) > WATERING_DURATION) {
            isWatered = false;
        }

        if (stage == PlantStage.HARVESTED && TimeUtils.timeSinceMillis(lastStageChangeTime) > WITHER_TIME) {
            stage = PlantStage.WITHER;
        }

        currentAnimation = imageManager.getAnimation(stage);
    }

    private void advanceGrowthStage() {
        switch (stage) {
            case SEED:
                stage = PlantStage.SPROUT;
                break;
            case SPROUT:
                stage = PlantStage.YOUNG;
                break;
            case YOUNG:
                stage = PlantStage.MATURE;
                break;
            case MATURE:
                stage = PlantStage.HARVESTED;
                isHarvestable = true;
                break;
            default:
                break;
        }

        lastStageChangeTime = TimeUtils.millis();
    }

    public void water() {
        isWatered = true;
        lastStageChangeTime = TimeUtils.millis();
    }

    public boolean isHarvestable() {
        return isHarvestable;
    }

    public void harvest() {
        if (isHarvestable) {
            stage = PlantStage.HARVESTED;
            isHarvestable = false;
        }
    }

    public void render(SpriteBatch batch) {
        TextureRegion currentFrame = currentAnimation.getKeyFrame(stateTime, true);
        batch.draw(currentFrame, position.x, position.y, width, height);
    }

    public Vector2 getPosition() {
        return position;
    }

    public PlantStage getStage() {
        return stage;
    }
}
