package io.github.Farm.player.lam_lai_file;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.Farm.Map.MapInteractionHandler;
import io.github.Farm.Plants.PlantManager;
import io.github.Farm.Plants.PlantRenderer;
import io.github.Farm.Plants.PlantType;
import io.github.Farm.player.Collider;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;

/// chiu trach nhiem tuong tac giua nhan vat voi map ///
public class CollisionHandler implements Collider {
    private PlantManager plantManager;
    private MapInteractionHandler mapInteractionHandler;

    public CollisionHandler(PlantManager plantManager, MapInteractionHandler mapInteractionHandler) {
        this.plantManager = plantManager;
        this.mapInteractionHandler = mapInteractionHandler;
    }

    public void checkCollisions(PlayerCotrollerr playerCotrollerr) {
        //check collision plant va player

        Iterator<PlantRenderer> iterator = plantManager.getPlants().iterator();
        while (iterator.hasNext()){
            PlantRenderer plant=iterator.next();
            if (playerCotrollerr.getCollider().overlaps(plant.getCollider())) {
                plant.onCollision( playerCotrollerr);
//                //Hit
//                if(playerCotrollerr.getCurrentState().startsWith("HIT_")) {
//                    iterator.remove();
//                }
                //water
                if(playerCotrollerr.getCurrentState().startsWith("WATER_")){
                    plant.water();
                }



            }
        }
        //check nhieu cai khac o day--
    }

    public void handlePlowing(Vector2 positionInMap) {
        mapInteractionHandler.digSoil(positionInMap);
    }

    public void plantSeed(Vector2 positionInMap, PlantType plantType) {
        mapInteractionHandler.plantSeed(positionInMap);
    }

    public boolean isPlayerCanFish(Rectangle playerCollider){
        ArrayList<Rectangle> canFishRectangles = mapInteractionHandler.getCanFishZoneLayer();

        for (Rectangle canFishRect : canFishRectangles) {
            if (playerCollider.overlaps(canFishRect)) {
                System.out.println("dc cau ca");
                return true;
            }
        }

        return false;
    }

    @Override
    public Rectangle getCollider() {
        return null;
    }

    @Override
    public void onCollision(Collider other) {

    }

    public MapInteractionHandler getMapInteractionHandler() {
        return mapInteractionHandler;
    }

    public PlantManager getPlantManager() {
        return plantManager;
    }
}
