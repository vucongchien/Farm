package io.github.Farm.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class GameSaveManager {
    private static GameSaveManager instance;
    private Gson gson;

    public static GameSaveManager getInstance() {
        if (instance == null) {
            instance = new GameSaveManager();
        }
        return instance;
    }

    public GameSaveManager() {
        gson = new GsonBuilder().setPrettyPrinting().create();
    }

    public void savePlayerData(PlayerData playerData) {
        FileHandle file = Gdx.files.local("playerData.json");
        file.writeString(gson.toJson(playerData), false);
    }

    public void savePlantsData(List<PlantData> plants) {
        FileHandle file = Gdx.files.local("plantsData.json");
        file.writeString(gson.toJson(plants), false);
    }

    public void saveInventoryData(InventoryData inventoryData) {
        FileHandle file = Gdx.files.local("inventoryData.json");
        file.writeString(gson.toJson(inventoryData), false);
    }

    public void saveAnimalData(AnimalData animalData) {
        FileHandle file = Gdx.files.local("animalData.json");
        file.writeString(gson.toJson(animalData), false);
    }

    public PlayerData loadPlayerData() {
        FileHandle file = Gdx.files.local("playerData.json");
        return file.exists() ? gson.fromJson(file.readString(), PlayerData.class) : new PlayerData();
    }

    public List<PlantData> loadPlantsData() {
        FileHandle file = Gdx.files.local("plantsData.json");
        return file.exists() ? gson.fromJson(file.readString(), List.class) : new ArrayList<>();
    }

    public InventoryData loadInventoryData() {
        FileHandle file = Gdx.files.local("inventoryData.json");
        return file.exists() ? gson.fromJson(file.readString(), InventoryData.class) : new InventoryData();
    }
    public AnimalData loadAnimalData() {
        FileHandle file = Gdx.files.local("animalData.json");
        return file.exists() ? gson.fromJson(file.readString(), AnimalData.class) : new AnimalData();
    }
}


