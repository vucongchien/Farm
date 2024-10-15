package io.github.Farm.Plants;

import com.badlogic.gdx.math.Vector2;
import io.github.Farm.ui.inventory.Inventory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlantManager {

    private static PlantManager instance;

    public static PlantManager getInstance() {
        if (instance == null) {
            instance = new PlantManager();
        }
        return instance;
    }

    private Map<Vector2, PlantRenderer> plants;

    public PlantManager() {
        plants = new HashMap<>();
    }

    public void addPlantFromInventory(Vector2 position, PlantType type) {
        if (plants.containsKey(position)) {
            return;
        }
        plants.put(new Vector2(position), new PlantRenderer(new Vector2(position), type));
        Inventory.getInstance().getSelectedItem().reduceQuantity();
    }

    public void update(float deltaTime) {
        for (PlantRenderer plant : plants.values()) {
            plant.update(deltaTime);
        }
    }

    public PlantRenderer getPlantAt(Vector2 position) {
        return plants.get(position);
    }

    public List<PlantRenderer> getListPlants(){
        return new ArrayList<>(plants.values());
    }

    public Map<Vector2, PlantRenderer> getMapPlants() {
        return plants;
    }
}
