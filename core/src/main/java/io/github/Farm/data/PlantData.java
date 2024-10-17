package io.github.Farm.data;

import com.badlogic.gdx.math.Vector2;

public class PlantData {
    private String type;
    private int growthStage;
    private Vector2 position;

    public PlantData() {
    }

    public PlantData(String type, int growthStage, Vector2 position) {
        this.type = type;
        this.growthStage = growthStage;
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getGrowthStage() {
        return growthStage;
    }

    public void setGrowthStage(int growthStage) {
        this.growthStage = growthStage;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
