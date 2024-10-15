package io.github.Farm.player;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.Farm.Interface.Collider;
import io.github.Farm.Map.MapInteractionHandler;
import io.github.Farm.Plants.PlantType;
import io.github.Farm.ui.inventory.Item;
import io.github.Farm.ui.inventory.ItemManager;

import java.util.ArrayList;
import java.util.Iterator;

/// chiu trach nhiem tuong tac giua nhan vat voi map ///
public class CollisionHandler implements Collider {

    private MapInteractionHandler mapInteractionHandler;

    public CollisionHandler(MapInteractionHandler mapInteractionHandler) {

        this.mapInteractionHandler = mapInteractionHandler;
    }

    public void checkCollisions(PlayerController playerController) {
        //check nhieu cai khac o day--
        Iterator<Item> iterator = ItemManager.getInstance().getItemList().iterator();
        while(iterator.hasNext()){
            Item item =iterator.next();
            if(playerController.getCollider().overlaps(item.getCollider())){
                item.onCollision(playerController);
                iterator.remove();
            }
        }

    }

    public void handlePlowing(Vector2 positionInMap) {
        mapInteractionHandler.digSoil(positionInMap);
    }

    public void plantSeed(Vector2 positionInMap, PlantType plantType,PlayerController playerController) {
        mapInteractionHandler.plantSeed(positionInMap,plantType,playerController);
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

}
