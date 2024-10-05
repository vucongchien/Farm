package io.github.Farm.player.PLAYER_STATE;

import io.github.Farm.player.PlayerController;

public class DigState implements InterfacePlayerState {
    String direction;
    public DigState(String direction){
        this.direction=direction;
    }

    @Override
    public void enter(PlayerController player) {
        player.setCurrentState("DIG_"+direction);
    }

    @Override
    public void update(PlayerController player, float deltaTime) {

    }

    @Override
    public void exit(PlayerController player) {

    }

    @Override
    public String getStateName() {
        return "DIG_"+direction;
    }
}
