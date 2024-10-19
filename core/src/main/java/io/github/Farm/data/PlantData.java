package io.github.Farm.data;

import com.badlogic.gdx.math.Vector2;

public class PlantData {
    private String type;
    private String stage;
    private Vector2 position;

    public PlantData() {
    }

    public PlantData(String type, String stage, Vector2 position) {
        this.type = type;
        this.stage = stage;
        this.position = position;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStage() {
        return stage;
    }

    public void setStage(String stage) {
        this.stage = stage;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }
}
