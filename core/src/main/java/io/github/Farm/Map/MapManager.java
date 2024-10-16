package io.github.Farm.Map;

import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

public class MapManager {
    private TiledMap map;



    public MapManager(TiledMap map) {
        this.map = map;
        setNightLayerVisible(false);
    }

    public String getTileClass(Vector2 positonInMap) {
        TiledMapTileLayer landLayer = (TiledMapTileLayer) map.getLayers().get("land");

        TiledMapTileLayer.Cell cell = landLayer.getCell((int) positonInMap.x,(int) positonInMap.y);
        if (cell != null) {
            TiledMapTile tile = cell.getTile();
            return (String) tile.getProperties().get("type");
        }
        return null;
    }

    public void changeTile(Vector2 postionInMap, String newTileClass,String layer) {

        TiledMapTileLayer tileLayer = (TiledMapTileLayer) map.getLayers().get(layer);

        TiledMapTileLayer.Cell cell = tileLayer.getCell((int) postionInMap.x,(int) postionInMap.y);

        if (cell == null) {
            cell = new TiledMapTileLayer.Cell();
            tileLayer.setCell((int) postionInMap.x, (int) postionInMap.y, cell);
        }


        for (TiledMapTileSet tileSet : map.getTileSets()) {
            for (TiledMapTile tile : tileSet) {

                String tileClass = (String) tile.getProperties().get("type");
                if (newTileClass.equals(tileClass)) {

                    cell.setTile(tile);
                    return;
                }
            }
        }
    }

    public void setNightLayerVisible(boolean visible) {
        TiledMapTileLayer nightLayer =(TiledMapTileLayer) map.getLayers().get("NIGHT");
        if (nightLayer != null) {
            nightLayer.setVisible(visible);
        }
    }

    public TiledMap getTiledMap() {
        return map;
    }
}
