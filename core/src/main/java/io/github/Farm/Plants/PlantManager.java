package io.github.Farm.Plants;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class PlantManager {
    private List<PlantRenderer> plants;

    public PlantManager() {
        plants = new ArrayList<>();
    }

    public void addPlant(PlantRenderer plant) {
        plants.add(plant);
    }

    public String getPlantAtPosition(Vector2 positionInMap) {
        for (PlantRenderer plant : plants) {
            if (plant.getPosition().epsilonEquals(positionInMap, 0.1f)) {
                return plant.getType().toString();
            }
        }
        System.out.println("co conca cdwadw");
        return null;
    }

    public void remove(int index) {
        plants.remove(index);
    }

    public void update(float deltaTime) {
        for (PlantRenderer plant : plants) {
            plant.update(deltaTime);
        }
    }

    public void render(SpriteBatch batch, Camera camera) {

        for (PlantRenderer plant : plants) {
            plant.render(batch,camera);
        }
    }

    public List<PlantRenderer> getPlants() {
        return plants;
    }
}

