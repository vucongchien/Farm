package io.github.Farm.player.PLAYER_STATE;

import io.github.Farm.player.PlayerController;

public class SwimState implements InterfacePlayerState {
    private String direction;
    public SwimState (String direction){
        this.direction=direction;
    }

    @Override
    public void enter(PlayerController player) {
        player.setCurrentState("SWIM_" + direction);
    }

    @Override
    public void update(PlayerController player, float deltaTime) {

    }

    @Override
    public void exit(PlayerController player) {

    }

    @Override
    public String getStateName() {
        return "SWIM_"+direction;
    }
}
