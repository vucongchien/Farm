package io.github.Farm.player.lam_lai_file.PLAYER_STAGE;

import com.badlogic.gdx.math.Vector2;
import io.github.Farm.player.lam_lai_file.PlayerCotrollerr;

public class IdleState implements PlayerStateee {
    private String direction;

    public IdleState(String direction){
        this.direction=direction;
    }

    @Override
    public void enter(PlayerCotrollerr player) {
        player.setCurrentState("IDLE_" + direction);
    }

    @Override
    public void update(PlayerCotrollerr player, float deltaTime) {
        Vector2 movement = player.getInputHandler().handleMovementInput();
        if (movement.x != 0 || movement.y != 0) {
            player.changeState(new WalkState(direction));
        }
    }

    @Override
    public void exit(PlayerCotrollerr player) {

    }

    @Override
    public String getStateName() {
        return "IDLE_" + direction;
    }
}
