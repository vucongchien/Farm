package io.github.Farm.Map;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Timer;
import io.github.Farm.Plants.PlantManager;
import io.github.Farm.Plants.PlantType;
import io.github.Farm.inventory.Inventory;
import io.github.Farm.player.PLAYER_STATE.IdleState;
import io.github.Farm.player.PlayerController;

import java.util.ArrayList;

public class MapInteractionHandler {

    private MapManager mapManager;
    private static final String GRASS_TILE = "grass";
    private static final String DUG_SOIL_TILE = "dug_soil";
    private static final String CAN_FISH_LAYER = "canFish";
    private static final String LAND_LAYER="land";


    private ArrayList<Rectangle> canFishRectangles;


    private final float TIME_TO_PLANT =2f;
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
        if (GRASS_TILE.equals(tileClass)) {

            mapManager.changeTile(playerController.getPositionInMap(), DUG_SOIL_TILE, LAND_LAYER);
            System.out.println(playerController.getPositionInMap());

        }
        else {


        }

    }
    public void plantSeed(PlantType plantType, PlayerController playerController){
        Inventory.getInstance().setOpened();
        if(PlantManager.getInstance().getPlantAt(playerController.getPositionInMap())!=null) return;
        String tileClass=mapManager.getTileClass(playerController.getPositionInMap());
        if(DUG_SOIL_TILE.equals(tileClass)){
            playerController.setPlanting(true);

            if (currentTask != null) {
                currentTask.cancel();
            }
            currentTask = new Timer.Task() {
                @Override
                public void run() {
                    if (playerController.isPlanting()) {
                        PlantManager.getInstance().addPlantFromInventory(playerController.getPositionInMap(), plantType);
                        playerController.changeState(new IdleState(playerController.isFacingRight()?"RIGHT":"LEFT"));
                        playerController.setPlanting(false);
                        if (Inventory.getInstance().getSelectedItem().getQuantity() <= 0) {
                            Inventory.getInstance().getSlots().remove(Inventory.getInstance().getSelectedItem());
                        }

                    }
                }
            };
            Timer.schedule(currentTask, TIME_TO_PLANT);

        } else {
//            System.out.println("Cannot plant: " + tileClass+ "  "+playerController.getPositionInMap());
        }


    }


    public ArrayList<Rectangle> getCanFishZoneLayer() {
        if (canFishRectangles == null) {
            canFishRectangles = new ArrayList<>();
            MapLayer canFishLayer = mapManager.getTiledMap().getLayers().get(CAN_FISH_LAYER);
            if (canFishLayer != null) {
                for (MapObject object : canFishLayer.getObjects()) {
                    if (object instanceof RectangleMapObject) {
                        canFishRectangles.add(((RectangleMapObject) object).getRectangle());
                    }
                }
            }
        }
        return canFishRectangles;
    }



}

