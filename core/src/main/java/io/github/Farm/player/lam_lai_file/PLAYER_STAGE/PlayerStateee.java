package io.github.Farm.player.lam_lai_file.PLAYER_STAGE;

import io.github.Farm.player.lam_lai_file.PlayerCotrollerr;

public interface PlayerStateee {
    void enter(PlayerCotrollerr player);
    void update(PlayerCotrollerr player, float deltaTime);
    void exit(PlayerCotrollerr player);
    String getStateName();
}
