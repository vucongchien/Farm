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
        player.setCurrentState("IDLE_" + direction);
    }

    @Override
    public void update(PlayerController player, float deltaTime) {
        Vector2 movement = player.getInputHandler().handleMovementInput();
        if (movement.x != 0 || movement.y != 0) {
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
