package io.github.Farm.player.PLAYER_STATE;

import io.github.Farm.player.PlayerController;

public interface InterfacePlayerState {
    void enter(PlayerController player);
    void update(PlayerController player, float deltaTime);
    void exit(PlayerController player);
    String getStateName();
}
