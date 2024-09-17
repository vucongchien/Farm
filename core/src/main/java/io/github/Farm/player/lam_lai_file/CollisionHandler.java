package io.github.Farm.player.lam_lai_file;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.Farm.Map.MapInteractionHandler;
import io.github.Farm.Plants.PlantManager;
import io.github.Farm.Plants.PlantRenderer;
import io.github.Farm.Plants.PlantType;
import io.github.Farm.player.Collider;

/// chiu trach nhiem tuong tac giua nhan vat voi map ///





public class CollisionHandler implements Collider {
    private PlantManager plantManager;
    private MapInteractionHandler mapInteractionHandler;

    public CollisionHandler(PlantManager plantManager, MapInteractionHandler mapInteractionHandler) {
        this.plantManager = plantManager;
        this.mapInteractionHandler = mapInteractionHandler;
    }

    public void checkCollisions(Rectangle playerCollider) {
        for (PlantRenderer plant : plantManager.getPlants()) {
            if (playerCollider.overlaps(plant.getCollider())) {
                plant.onCollision( this);
            }
        }
    }

    public void handlePlowing(Vector2 positionInMap) {
        mapInteractionHandler.digSoil(positionInMap);
    }

    public void plantSeed(Vector2 positionInMap, PlantType plantType) {
        mapInteractionHandler.plantSeed(positionInMap);
    }

    @Override
    public Rectangle getCollider() {
        return null;
    }

    @Override
    public void onCollision(Collider other) {

    }
}
