package io.github.Farm.player.PLAYER_STATE;

import com.badlogic.gdx.math.Vector2;
import io.github.Farm.SoundManager;
import io.github.Farm.player.PlayerController;

public class WalkState implements InterfacePlayerState {
    private String direction;

    public WalkState(String direction) {
        this.direction = direction;
    }
    @Override
    public void enter(PlayerController player) {

    }

    @Override
    public void update(PlayerController player, float deltaTime) {

    }

    @Override
    public void exit(PlayerController player) {

    }

    @Override
    public String getStateName() {
        return "WALK_" + direction;
    }
}
