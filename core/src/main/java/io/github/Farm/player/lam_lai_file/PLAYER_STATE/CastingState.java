package io.github.Farm.player.lam_lai_file.PLAYER_STATE;

import io.github.Farm.player.lam_lai_file.PlayerCotrollerr;

public class CastingState implements PlayerStateee{
    private String direction;
    private float Time=0f;

    public CastingState(String direction) {
        this.direction = direction;
    }

    @Override
    public void enter(PlayerCotrollerr player) {
        player.setCurrentState("CASTING_" + direction);
    }

    @Override
    public void update(PlayerCotrollerr player, float deltaTime) {
        if(Time<=0.4f){
            Time+= deltaTime;
            return;
        }

        player.changeState(new WaitingState(direction));
    }

    @Override
    public void exit(PlayerCotrollerr player) {
    }

    @Override
    public String getStateName() {
        return "CASTING_" + direction;
    }
}
