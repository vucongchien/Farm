package io.github.Farm.player.PLAYER_STATE;

import io.github.Farm.Plants.PlantManager;
import io.github.Farm.Plants.PlantRenderer;
import io.github.Farm.player.PlayerController;

import java.util.Iterator;

public class WaterState implements InterfacePlayerState {
    String direction;
    public WaterState(String direction){
        this.direction=direction;
    }

    @Override
    public void enter(PlayerController player) {

    }

    @Override
    public void update(PlayerController player, float deltaTime) {
        if(PlantManager.getInstance().getPlantAt(player.getPositionInMap())!=null){
            PlantManager.getInstance().getMapPlants().get(player.getPositionInMap()).onCollision(player);
        }


    }

    @Override
    public void exit(PlayerController player) {

    }

    @Override
    public String getStateName() {
        return "WATER_"+direction;
    }
}
