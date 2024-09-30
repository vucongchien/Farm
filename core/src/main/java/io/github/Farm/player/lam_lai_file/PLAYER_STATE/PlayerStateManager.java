package io.github.Farm.player.lam_lai_file.PLAYER_STATE;

import io.github.Farm.player.lam_lai_file.PlayerCotrollerr;

public class PlayerStateManager {
    private PlayerStateee currentState;
    private PlayerStateee preState;

    public PlayerStateManager(PlayerStateee initialState) {
        this.currentState = initialState;
        this.preState=initialState;
    }

    public void changeState(PlayerCotrollerr player, PlayerStateee newState) {
        if (!currentState.getStateName().equals(preState.getStateName()) ) {
            preState=currentState;
            currentState.exit(player);
            System.out.println(preState +"    "+ currentState);
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
