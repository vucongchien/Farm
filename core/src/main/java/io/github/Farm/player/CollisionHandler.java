package io.github.Farm.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;
import io.github.Farm.Interface.Collider;
import io.github.Farm.Map.MapInteractionHandler;
import io.github.Farm.Plants.PlantManager;
import io.github.Farm.Plants.PlantType;
import io.github.Farm.ui.SoundManager;
import io.github.Farm.animal.Pet;
import io.github.Farm.animal.PetManager;
import io.github.Farm.inventory.Item;
import io.github.Farm.inventory.ItemManager;
import io.github.Farm.ui.Other.SelectionBox;

import java.util.ArrayList;
import java.util.Iterator;

/// chiu trach nhiem tuong tac giua nhan vat voi map ///
public class CollisionHandler implements Collider {

    private MapInteractionHandler mapInteractionHandler;
    private PlayerController playerController;
    private SelectionBox selectionBox;



    public CollisionHandler(MapInteractionHandler mapInteractionHandler,PlayerController playerController) {
        this.playerController=playerController;
        this.mapInteractionHandler = mapInteractionHandler;
        this.selectionBox=new SelectionBox();
    }

    public void checkCollisions() {

        checkItemCollisions();

        checkPetCollisions();

        checkPlantCollisions();
    }

    private void checkItemCollisions() {
        Iterator<Item> iterator = ItemManager.getInstance().getItemList().iterator();
        while (iterator.hasNext()) {
            Item item = iterator.next();
            if (playerController.getCollider().overlaps(item.getCollider()) && item.isCanTake()) {
                selectionBox.ren(item.getPosition(), item.getWidth(), item.getHeight());
                if (Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                    item.onCollision(playerController);
                    iterator.remove();
                    SoundManager.getInstance().playPickUp();
                }
            }
        }
    }

    private void checkPetCollisions() {
        for (Pet pet : PetManager.getPetmanager().getPetManager()) {
            if (playerController.getCollider().overlaps(pet.getCollider())) {
                playerController.onCollision(pet);
                return;
            }
        }
    }

    private void checkPlantCollisions() {


        if (PlantManager.getInstance().getPlantAt(playerController.getPositionInMap()) != null) {
            if (PlantManager.getInstance().getPlantAt(playerController.getPositionInMap()).isHarvestable()) {

                selectionBox.ren(PlantManager.getInstance().getPlantAt(playerController.getPositionInMap()).getPosition().cpy().scl(16f).add(3, 5.5f), PlantManager.getInstance().getPlantAt(playerController.getPositionInMap()).getWidth() - 2, PlantManager.getInstance().getPlantAt(playerController.getPositionInMap()).getHeight() - 3);
            } else {
                System.out.println(PlantManager.getInstance().getPlantAt(playerController.getPositionInMap()).getWidth() - 2);
                selectionBox.ren(PlantManager.getInstance().getPlantAt(playerController.getPositionInMap()).getPosition().cpy().scl(16f).add(4.8f, 5.5f), Math.max((int) PlantManager.getInstance().getPlantAt(playerController.getPositionInMap()).getWidth() - 2,6), PlantManager.getInstance().getPlantAt(playerController.getPositionInMap()).getHeight() - 2);
            }
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
