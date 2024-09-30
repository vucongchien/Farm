package io.github.Farm.player.PLAYER_STATE;

import io.github.Farm.player.PlayerController;

public class CaughtState implements InterfacePlayerState {

    private String direction;
    private float waitTime = 0f;

    public CaughtState(String direction) {
        this.direction = direction;
    }

    @Override
    public void enter(PlayerController player) {
        player.setCurrentState("CAUGHT_" + direction);
    }

    @Override
    public void update(PlayerController player, float deltaTime) {
        if(waitTime<0.2f){
            waitTime+=deltaTime;
            return;
        }
        player.changeState(new IdleState(direction));
    }

    @Override
    public void exit(PlayerController player) {
        System.out.println("Exiting Caught State");
    }

    @Override
    public String getStateName() {
        return "CAUGHT_" + direction;
    }

}
