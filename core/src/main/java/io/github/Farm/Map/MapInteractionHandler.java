package io.github.Farm.Map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import io.github.Farm.Plants.PlantManager;
import io.github.Farm.Plants.PlantType;
import io.github.Farm.ui.inventory.Inventory;
import io.github.Farm.player.PlayerController;

import java.util.ArrayList;

public class MapInteractionHandler {

    private MapManager mapManager;


    private final float TIME_TO_PLANT =2f;
    private final float TIME_TO_DIG=2f;

    private Timer.Task currentTask;


    public MapInteractionHandler(MapManager mapManager) {
        this.mapManager = mapManager;
    }

    public boolean checkTile(Vector2 positonInMap,String tileType){
        if(mapManager.getTileClass(positonInMap)==null)
            return false;
        return mapManager.getTileClass(positonInMap).equals(tileType);
    }

    public void digSoil(PlayerController playerController) {

        String tileClass = mapManager.getTileClass(playerController.getPositionInMap());
        if ("grass".equals(tileClass)) {

            mapManager.changeTile(playerController.getPositionInMap(), "dug_soil", "land");

        }
        else {

            System.out.println("Cannot plant: " + tileClass);
        }

    }
    public void plantSeed( PlantType plantType, PlayerController playerController){
        String tileClass=mapManager.getTileClass(playerController.getPositionInMap());
        if("dug_soil".equals(tileClass)){
            Inventory.getInstance().setOpened();
            playerController.setPlanting(true);

            if (currentTask != null) {
                currentTask.cancel();
            }
            currentTask = new Timer.Task() {
                @Override
                public void run() {
                    if (playerController.isPlanting()) {
                        PlantManager.getInstance().addPlantFromInventory(playerController.getPositionInMap(), plantType);

                    }
                }
            };
            Timer.schedule(currentTask, TIME_TO_PLANT);

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

