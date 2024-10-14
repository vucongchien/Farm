package io.github.Farm.player.PLAYER_STATE;

import io.github.Farm.player.PlayerController;

import java.util.Timer;

public class DoingState implements InterfacePlayerState{
    private String direction;
    private float time=0f;

    public DoingState(String direction){
        this.direction=direction;
    }

    @Override
    public void enter(PlayerController player) {

    }

    @Override
    public void update(PlayerController player, float deltaTime) {
        time += deltaTime;
        if (time >= 2f) {
            player.setPlanting(false);
        }

    }

    @Override
    public void exit(PlayerController player) {

    }

    @Override
    public String getStateName() {
        return "DOING_"+direction;
    }
}
