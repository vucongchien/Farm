package io.github.Farm.player.lam_lai_file.PLAYER_STATE;

import com.badlogic.gdx.math.Vector2;
import io.github.Farm.player.lam_lai_file.PlayerCotrollerr;

public class WalkState implements PlayerStateee{
    private String direction;

    public WalkState(String direction) {
        this.direction = direction;
    }
    @Override
    public void enter(PlayerCotrollerr player) {
        player.setCurrentState("WALK_" + direction);
    }

    @Override
    public void update(PlayerCotrollerr player, float deltaTime) {
        Vector2 movement = player.getInputHandler().handleMovementInput();
        if (movement.x == 0 && movement.y == 0) {
            player.changeState(new IdleState(direction));
        }
    }

    @Override
    public void exit(PlayerCotrollerr player) {
        //-----------------------
    }

    @Override
    public String getStateName() {
        return "WALK_" + direction;
    }
}
