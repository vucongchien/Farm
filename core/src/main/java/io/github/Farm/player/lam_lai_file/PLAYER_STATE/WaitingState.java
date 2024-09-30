package io.github.Farm.player.lam_lai_file.PLAYER_STATE;

import io.github.Farm.player.lam_lai_file.PlayerCotrollerr;

public class WaitingState implements PlayerStateee {
    private String direction;
    private float waitTime = 0f;

    public WaitingState(String direction) {
        this.direction = direction;
    }

    @Override
    public void enter(PlayerCotrollerr player) {
        player.setCurrentState("WAITING_" + direction);

    }

    @Override
    public void update(PlayerCotrollerr player, float deltaTime) {
        waitTime += deltaTime;
        if (waitTime >= 2.0f) {
            player.changeState(new CaughtState(direction));
        }
    }

    @Override
    public void exit(PlayerCotrollerr player) {

    }

    @Override
    public String getStateName() {
        return "WAITING_" + direction;
    }

}
