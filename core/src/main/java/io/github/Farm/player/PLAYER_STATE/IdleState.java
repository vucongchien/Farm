package io.github.Farm.player.PLAYER_STATE;

import com.badlogic.gdx.math.Vector2;
import io.github.Farm.player.PlayerController;

public class IdleState implements InterfacePlayerState {
    private String direction;

    public IdleState(String direction){
        this.direction=direction;
    }

    @Override
    public void enter(PlayerController player) {

    }

    @Override
    public void update(PlayerController player, float deltaTime) {
        if (player.getInputHandler().isMoving()) {
            player.changeState(new WalkState(direction));
        }
    }

    @Override
    public void exit(PlayerController player) {

    }

    @Override
    public String getStateName() {
        return "IDLE_" + direction;
    }
}
