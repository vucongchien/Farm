package io.github.Farm.player.lam_lai_file.PLAYER_STAGE;

import io.github.Farm.player.lam_lai_file.PlayerCotrollerr;

public class PlayerStateManager {
    private PlayerStateee currentState;

    public PlayerStateManager(PlayerStateee initialState) {
        this.currentState = initialState;
    }

    public void changeState(PlayerCotrollerr player, PlayerStateee newState) {
        if (currentState != null) {
            currentState.exit(player);
        }
        currentState = newState;
        currentState.enter(player);
    }

    public void updateState(PlayerCotrollerr player, float deltaTime) {
        if (currentState != null) {
            currentState.update(player, deltaTime);
        }
    }

    public String getCurrentStateName() {
        return currentState.getStateName();
    }
}
