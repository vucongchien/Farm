package io.github.Farm.player.PLAYER_STATE;

import io.github.Farm.player.PlayerController;

public class CastingState implements InterfacePlayerState {
    private String direction;
    private float Time=0f;

    public CastingState(String direction) {
        this.direction = direction;
    }

    @Override
    public void enter(PlayerController player) {
        player.setCurrentState("CASTING_" + direction);
    }

    @Override
    public void update(PlayerController player, float deltaTime) {
        if(Time<=0.4f){
            Time+= deltaTime;
            return;
        }

        player.changeState(new WaitingState(direction));
    }

    @Override
    public void exit(PlayerController player) {
    }

    @Override
    public String getStateName() {
        return "CASTING_" + direction;
    }
}
