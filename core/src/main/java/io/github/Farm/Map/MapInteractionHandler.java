package io.github.Farm.Map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

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

    public ArrayList<Rectangle> getCanFishZoneLayer(){
        MapLayer canFishLayer=mapManager.getTiledMap().getLayers().get("canFish");
        ArrayList<Rectangle> canFishRectangles=new ArrayList<>();
        if(canFishLayer!=null){
            for(MapObject object: canFishLayer.getObjects()){
                if(object instanceof RectangleMapObject){
                    canFishRectangles.add(((RectangleMapObject) object).getRectangle());
                }
            }
        }
        return canFishRectangles;
    }


}

