package io.github.Farm.data;

import java.util.ArrayList;
import java.util.List;

public class GameData {
    private PlayerData player;
    private List<PlantData> plants;
    private InventoryData inventory;

    public GameData() {
        this.player = new PlayerData();
        this.plants = new ArrayList<>();
        this.inventory = new InventoryData();
    }

    public PlayerData getPlayer() {
        return player;
    }

    public void setPlayer(PlayerData player) {
        this.player = player;
    }

    public List<PlantData> getPlants() {
        return plants;
    }

    public void setPlants(List<PlantData> plants) {
        this.plants = plants;
    }

    public InventoryData getInventory() {
        return inventory;
    }

    public void setInventory(InventoryData inventory) {
        this.inventory = inventory;
    }


}
