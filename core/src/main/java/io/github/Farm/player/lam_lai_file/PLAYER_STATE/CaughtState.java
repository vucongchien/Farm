package io.github.Farm.player.lam_lai_file.PLAYER_STATE;

import io.github.Farm.player.lam_lai_file.PlayerCotrollerr;

public class CaughtState implements PlayerStateee{

    private String direction;
    private float waitTime = 0f;

    public CaughtState(String direction) {
        this.direction = direction;
    }

    @Override
    public void enter(PlayerCotrollerr player) {
        player.setCurrentState("CAUGHT_" + direction);
    }

    @Override
    public void update(PlayerCotrollerr player, float deltaTime) {
        if(waitTime<0.2f){
            waitTime+=deltaTime;
            return;
        }
        player.changeState(new IdleState(direction));
    }

    @Override
    public void exit(PlayerCotrollerr player) {
        System.out.println("Exiting Caught State");
    }

    @Override
    public String getStateName() {
        return "CAUGHT_" + direction;
    }

}
