package io.github.Farm.player;

import com.badlogic.gdx.math.Rectangle;
import io.github.Farm.Interface.Collider;
import io.github.Farm.Map.MapInteractionHandler;
import io.github.Farm.Plants.PlantManager;
import io.github.Farm.Plants.PlantType;
import io.github.Farm.animal.Buffalo.Buffalo;
import io.github.Farm.animal.Buffalo.BuffaloManager;
import io.github.Farm.inventory.Item;
import io.github.Farm.inventory.ItemManager;

import java.util.ArrayList;
import java.util.Iterator;

/// chiu trach nhiem tuong tac giua nhan vat voi map ///
public class CollisionHandler implements Collider {

    private MapInteractionHandler mapInteractionHandler;
    private PlayerController playerController;

    public CollisionHandler(MapInteractionHandler mapInteractionHandler,PlayerController playerController) {
        this.playerController=playerController;
        this.mapInteractionHandler = mapInteractionHandler;
    }

    public void checkCollisions() {
        //check nhieu cai khac o day--

        Iterator<Item> iterator = ItemManager.getInstance().getItemList().iterator();
        while(iterator.hasNext()){
            Item item =iterator.next();
            if(playerController.getCollider().overlaps(item.getCollider())){
                item.onCollision(playerController);
                iterator.remove();
            }
        }

        Iterator<Buffalo> buffaloIterator= BuffaloManager.getbuffalomanager().getBuffaloManager().iterator();
        while (buffaloIterator.hasNext()){
            Buffalo buffalo=buffaloIterator.next();
            if(playerController.getCollider().overlaps(buffalo.getCollider())){
                buffalo.onCollision(playerController);
                playerController.onCollision(buffalo);
                playerController.setCanFeed(true);
                return;
            }
        }


        if(PlantManager.getInstance().getPlantAt(playerController.getPositionInMap())!=null){

        }

    }

    public void handlePlowing() {
        mapInteractionHandler.digSoil(playerController);
    }

    public void plantSeed( PlantType plantType) {
        mapInteractionHandler.plantSeed(plantType,playerController);
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
