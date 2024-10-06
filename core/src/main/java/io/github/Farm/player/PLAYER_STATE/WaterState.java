package io.github.Farm.player.PLAYER_STATE;

import io.github.Farm.player.PlayerController;

public class WaterState implements InterfacePlayerState {
    String direction;
    public WaterState(String direction){
        this.direction=direction;
    }

    @Override
    public void enter(PlayerController player) {
        player.setCurrentState("WATER_"+direction);
    }

    @Override
    public void update(PlayerController player, float deltaTime) {

    }

    @Override
    public void exit(PlayerController player) {

    }

    @Override
    public String getStateName() {
        return "WATER_"+direction;
    }
}