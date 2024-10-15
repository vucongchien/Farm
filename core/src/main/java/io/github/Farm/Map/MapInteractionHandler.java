package io.github.Farm.Map;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import io.github.Farm.Plants.PlantManager;
import io.github.Farm.Plants.PlantType;
import io.github.Farm.ui.Other.TimeCoolDown;
import io.github.Farm.inventory.Inventory;
import io.github.Farm.player.PlayerController;

import java.util.ArrayList;

public class MapInteractionHandler {

    private MapManager mapManager;
    private SpriteBatch batch;


    public MapInteractionHandler(MapManager mapManager) {
        this.mapManager = mapManager;
        this.batch=batch;
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

            Timer.schedule(new Timer.Task() {
                @Override
                public void run() {
                    if (playerController.isPlanting()) {
                        PlantManager.getInstance().addPlantFromInventory(positionInMap, plantType);
                    }
                }
            }, 2);


        } else {
            System.out.println("Cannot plant: " + tileClass);
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

