package io.github.Farm.player.PLAYER_STATE;

import io.github.Farm.player.PlayerController;

public class WaitingState implements InterfacePlayerState {
    private String direction;
    private float waitTime = 0f;

    public WaitingState(String direction) {
        this.direction = direction;
    }

    @Override
    public void enter(PlayerController player) {

    }

    @Override
    public void update(PlayerController player, float deltaTime) {
        waitTime += deltaTime;
        if (waitTime >= 2.0f) {
            player.changeState(new CaughtState(direction));
        }
    }

    @Override
    public void exit(PlayerController player) {

    }

    @Override
    public String getStateName() {
        return "WAITING_" + direction;
    }

}
