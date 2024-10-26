package io.github.Farm.data;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
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

    public void saveMapData(TiledMap map) {
        MapData mapData = new MapData();

        for (MapLayer layer : map.getLayers()) {
            if (layer instanceof TiledMapTileLayer) {
                TiledMapTileLayer tileLayer = (TiledMapTileLayer) layer;

                for (int x = 0; x < tileLayer.getWidth(); x++) {
                    for (int y = 0; y < tileLayer.getHeight(); y++) {
                        TiledMapTileLayer.Cell cell = tileLayer.getCell(x, y);
                        if (cell != null && cell.getTile() != null) {
                            MapData.TileData tileData = new MapData.TileData();
                            tileData.x = x;
                            tileData.y = y;
                            tileData.layerName = layer.getName();
                            tileData.tileId = cell.getTile().getId();
                            mapData.tiles.add(tileData);
                        }
                    }
                }
            }
        }

        FileHandle file = Gdx.files.local("mapData.json");
        file.writeString(gson.toJson(mapData), false);
    }

    public void loadMapData(TiledMap map) {
        MapData mapData = loadMapDataFromFile();

        if (mapData == null || mapData.tiles.isEmpty()) {
            return;
        }

        for (MapData.TileData tileData : mapData.tiles) {
            TiledMapTileLayer tileLayer = (TiledMapTileLayer) map.getLayers().get(tileData.layerName);
            if (tileLayer != null) {
                TiledMapTileLayer.Cell cell = tileLayer.getCell(tileData.x, tileData.y);
                if (cell == null) {
                    cell = new TiledMapTileLayer.Cell();
                    tileLayer.setCell(tileData.x, tileData.y, cell);
                }

                for (TiledMapTileSet tileSet : map.getTileSets()) {
                    TiledMapTile tile = tileSet.getTile(tileData.tileId);
                    if (tile != null) {
                        cell.setTile(tile);
                        break;
                    }
                }
            }
        }
    }

    private MapData loadMapDataFromFile() {
        FileHandle file = Gdx.files.local("mapData.json");
        if (file.exists()) {
            return gson.fromJson(file.readString(), MapData.class);
        }
        return null;
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



