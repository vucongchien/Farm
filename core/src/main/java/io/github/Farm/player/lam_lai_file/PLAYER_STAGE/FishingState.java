package io.github.Farm.player.lam_lai_file.PLAYER_STAGE;

import com.badlogic.gdx.math.Vector2;
import io.github.Farm.player.lam_lai_file.PlayerCotrollerr;

public class FishingState implements PlayerStateee{
    private float timeFishing = 0f;
    private final float timeToFish = 5f;
    private String direction;

    public FishingState(String direction) {
        this.direction = direction;
    }

    @Override
    public void enter(PlayerCotrollerr player) {
        player.setCurrentState("CASTING_" + direction);
        timeFishing = 0f;
    }

    @Override
    public void update(PlayerCotrollerr player, float deltaTime) {
        Vector2 movement = player.getInputHandler().handleMovementInput();

        if (movement.x != 0 || movement.y != 0) {
            player.changeState(new IdleState(direction));
            return;
        }

        timeFishing += deltaTime;

        if (timeFishing >= timeToFish) {
            player.setCurrentState("CAUGHT_" + direction);
            player.changeState(new IdleState(direction));  // Sau đó chuyển về trạng thái idle
        } else if (timeFishing >= 0.6f) {
            player.setCurrentState("WAITING_" + direction); // Đợi cá cắn câu
        }
    }

    @Override
    public void exit(PlayerCotrollerr player) {

    }

    @Override
    public String getStateName() {
        return "FISHING_" + direction;
    }
}
