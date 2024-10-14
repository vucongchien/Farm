package io.github.Farm.Map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import io.github.Farm.Plants.PlantManager;
import io.github.Farm.Plants.PlantType;
import io.github.Farm.inventory.Inventory;
import io.github.Farm.player.PlayerController;

import java.util.ArrayList;
import java.util.TimerTask;

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
    public void plantSeed(Vector2 positionInMap, PlantType plantType, PlayerController playerController){
        String tileClass=mapManager.getTileClass(positionInMap);
        if("dug_soil".equals(tileClass)){
            Inventory.getInstance().setOpened();
            playerController.setPlanting(true);

            Timer.Task plantingTask = new Timer.Task() {
                @Override
                public void run() {
                    if (playerController.isPlanting()) {
                       // mapManager.changeTile(positionInMap,"PlantedSeed_1","planted_seed");
                        PlantManager.getInstance().addPlantFromInventory(positionInMap, plantType);

                    }
                }
            };
            Timer.schedule(plantingTask, 2);

        }
        else{
            System.out.println(tileClass);
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

