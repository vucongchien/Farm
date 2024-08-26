package io.github.Farm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import java.util.LinkedList;
import java.util.Queue;

public class PlayerController {
    private Vector2 position;
    private float speed;
    private boolean facingRight = true;
    private PlayerActType direction = PlayerActType.d_idle;
    private PlayerActType lastDirection = PlayerActType.d_idle;
    private Queue<PlayerActType> inputQueue = new LinkedList<>();

    public PlayerController(Vector2 startPosition, float speed) {
        this.position = startPosition;
        this.speed = speed;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void update(float deltaTime) {
        handleInput();
        moveCharacter(deltaTime);
    }

    private void handleInput() {
        inputQueue.clear();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) inputQueue.add(PlayerActType.up);
        if (Gdx.input.isKeyPressed(Input.Keys.S)) inputQueue.add(PlayerActType.down);
        if (Gdx.input.isKeyPressed(Input.Keys.A)) inputQueue.add(PlayerActType.left);
        if (Gdx.input.isKeyPressed(Input.Keys.D)) inputQueue.add(PlayerActType.right);

        if (!inputQueue.isEmpty()) {
            direction = inputQueue.peek();
            lastDirection = direction;
            facingRight = direction == PlayerActType.right;
        } else {
            direction = getIdleDirection();
        }
    }

    private PlayerActType getIdleDirection() {
        switch (lastDirection) {
            case up: return PlayerActType.u_idle;
            case down: return PlayerActType.d_idle;
            case left: return PlayerActType.l_idle;
            case right: return PlayerActType.r_idle;
            default: return PlayerActType.d_idle;
        }
    }

    private void moveCharacter(float deltaTime) {
        Vector2 movement = new Vector2();

        switch (direction) {
            case up: movement.y = 1; break;
            case down: movement.y = -1; break;
            case left: movement.x = -1; break;
            case right: movement.x = 1; break;
            default: return; // Không di chuyển nếu ở trạng thái idle
        }

        position.add(movement.scl(speed * deltaTime));
    }

    public boolean isWalking() {
        return direction != PlayerActType.d_idle && direction != PlayerActType.u_idle
            && direction != PlayerActType.l_idle && direction != PlayerActType.r_idle;
    }

    public String getDirection() {
        return direction.toString();
    }

    public boolean isFacingRight() {
        return facingRight;
    }
    public float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }
}
