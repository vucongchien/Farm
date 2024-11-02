package io.github.Farm.player.PLAYER_STATE;

import io.github.Farm.player.PlayerController;

public class PlayerStateManager {
    private InterfacePlayerState currentState;
    private InterfacePlayerState preState;

    public PlayerStateManager(InterfacePlayerState initialState) {
        this.currentState = initialState;
        this.preState=initialState;
    }

    public void changeState(PlayerController player, InterfacePlayerState newState) {
        if(currentState.getStateName().equals(newState.getStateName())){
            return;
        }
        if (!currentState.getStateName().equals(preState.getStateName()) ) {
            preState=currentState;
            currentState.exit(player);
        }
        currentState = newState;
        currentState.enter(player);
    }

    public void updateState(PlayerController player, float deltaTime) {
        if (currentState != null) {
            currentState.update(player, deltaTime);
        }
        else {
//            System.out.println("null roi anh zai");
        }
    }

    public String getCurrentStateName() {
        return currentState.getStateName();
    }
}
