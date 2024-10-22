package io.github.Farm.Plants;

import com.badlogic.gdx.math.Vector2;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.Farm.inventory.Inventory;
import io.github.Farm.ui.MainMenu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PlantManager {

    private static PlantManager instance;
    public final String link="plantsData.json";

    public static PlantManager getInstance() {
        if (instance == null) {
            instance = new PlantManager();
        }
        return instance;
    }

    private Map<Vector2, PlantRenderer> plants;

    public PlantManager() {
        if(MainMenu.isCheckcontinue()){
            plants = new HashMap<>();
            readPlanData(plants);
        }else {
            plants = new HashMap<>();
        }
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

    public void readPlanData(Map<Vector2, PlantRenderer> plants){
        ObjectMapper objectMapper= new ObjectMapper();
        try{
            JsonNode rootNode = objectMapper.readTree(new File(link));
            for(JsonNode plantsNode :rootNode) {
                String typeAsString = plantsNode.get("type").asText();
                PlantType type = PlantType.valueOf(typeAsString);
                String stageAsString = plantsNode.get("stage").asText();
                PlantStage stage = PlantStage.valueOf(stageAsString);
                JsonNode vector = plantsNode.get("position");
                float x = (float) vector.get("x").asDouble();
                float y = (float) vector.get("y").asDouble();
                PlantRenderer plantRenderer = new PlantRenderer(new Vector2(x,y), type);
                plantRenderer.setStage(stage);
                plants.put(plantRenderer.getPosition(),plantRenderer);
            }
        }catch (IOException e) {
            throw new RuntimeException("Lỗi khi đọc file JSON", e);
        }
    }
}
