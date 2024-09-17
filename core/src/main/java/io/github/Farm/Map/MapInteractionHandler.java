package io.github.Farm.Map;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

public class MapInteractionHandler {
    private MapManager mapManager;

    public MapInteractionHandler(MapManager mapManager) {
        this.mapManager = mapManager;
    }

    public boolean checkTile(Vector2 positonInMap,String tileType){
        if(mapManager.getTileClass(positonInMap)==null)
            return false;

        return mapManager.getTileClass(positonInMap).equals(tileType);
    }

    public void digSoil(Vector2 positionInMap) {

        String tileClass = mapManager.getTileClass(positionInMap);
        if ("grass".equals(tileClass)) {
            mapManager.changeTile(positionInMap, "dug_soil","land");
        }
        else {

        }
    }

    public void plantSeed(Vector2 positonInMap){
        String tileClass=mapManager.getTileClass(positonInMap);
        if("dug_soil".equals(tileClass)){
            mapManager.changeTile(positonInMap,"PlantedSeed_1","planted_seed");
        }
        else{

        }
    }



}

