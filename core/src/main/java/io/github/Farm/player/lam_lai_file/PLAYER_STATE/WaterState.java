package io.github.Farm.player.lam_lai_file.PLAYER_STATE;

import io.github.Farm.player.lam_lai_file.PlayerCotrollerr;

public class WaterState implements PlayerStateee {
    String direction;
    public WaterState(String direction){
        this.direction=direction;
    }

    @Override
    public void enter(PlayerCotrollerr player) {
        player.setCurrentState("WATER_"+direction);
    }

    @Override
    public void update(PlayerCotrollerr player, float deltaTime) {

    }

    @Override
    public void exit(PlayerCotrollerr player) {

    }

    @Override
    public String getStateName() {
        return "WATER_"+direction;
    }
}
