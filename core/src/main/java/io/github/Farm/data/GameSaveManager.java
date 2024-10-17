package io.github.Farm.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

import java.util.ArrayList;
import java.util.List;

public class GameSaveManager {
    private Json json;

    public GameSaveManager() {
        json = new Json();
    }

    public void savePlayerData(PlayerData playerData) {
        FileHandle file = Gdx.files.local("playerData.json");
        file.writeString(json.toJson(playerData), false);
    }

    public void savePlantsData(List<PlantData> plants) {
        FileHandle file = Gdx.files.local("plantsData.json");
        file.writeString(json.toJson(plants), false);
    }

    public void saveInventoryData(InventoryData inventoryData) {
        FileHandle file = Gdx.files.local("inventoryData.json");
        file.writeString(json.toJson(inventoryData), false);
    }

    public PlayerData loadPlayerData() {
        FileHandle file = Gdx.files.local("playerData.json");
        return file.exists() ? json.fromJson(PlayerData.class, file.readString()) : new PlayerData();
    }

    public List<PlantData> loadPlantsData() {
        FileHandle file = Gdx.files.local("plantsData.json");
        return file.exists() ? json.fromJson(ArrayList.class, file.readString()) : new ArrayList<>();
    }

    public InventoryData loadInventoryData() {
        FileHandle file = Gdx.files.local("inventoryData.json");
        return file.exists() ? json.fromJson(InventoryData.class, file.readString()) : new InventoryData();
    }
}


