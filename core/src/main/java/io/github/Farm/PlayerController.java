package io.github.Farm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import java.util.HashSet;
import java.util.Set;

public class PlayerController {
    private Vector2 position;  // Vị trí của nhân vật
    private float speed;       // Tốc độ di chuyển của nhân vật
    private boolean facingRight = true;  // Hướng mà nhân vật đang đối diện (phải hoặc trái)
    private float size;        // Kích thước của nhân vật
    private PlayerActType direction;  // Hướng di chuyển hiện tại của nhân vật
    private PlayerActType lastDirection;  // Hướng di chuyển cuối cùng

    private Set<PlayerActType> activeDirections = new HashSet<>();  // Tập hợp các hướng đang được nhấn

    public PlayerController(Vector2 startPosition, float speed, float size) {
        this.position = startPosition;
        this.speed = speed;
        this.size = size;
        this.direction = PlayerActType.d_idle;  // Mặc định nhân vật ở trạng thái đứng yên
        this.lastDirection = PlayerActType.d_idle; // Mặc định hướng di chuyển cuối cùng là xuống
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getSize() {
        return size;
    }

    public void update(float deltaTime) {
        handleInput();
        moveCharacter(deltaTime);
        updateDirection();
    }

    private void handleInput() {
        activeDirections.clear();

        boolean upPressed = Gdx.input.isKeyPressed(Input.Keys.W);
        boolean downPressed = Gdx.input.isKeyPressed(Input.Keys.S);
        boolean leftPressed = Gdx.input.isKeyPressed(Input.Keys.A);
        boolean rightPressed = Gdx.input.isKeyPressed(Input.Keys.D);

        if (upPressed && !downPressed) {
            activeDirections.add(PlayerActType.up);
        }
        if (downPressed && !upPressed) {
            activeDirections.add(PlayerActType.down);
        }
        if (leftPressed && !rightPressed) {
            activeDirections.add(PlayerActType.left);
        }
        if (rightPressed && !leftPressed) {
            activeDirections.add(PlayerActType.right);
        }

        // Determine the facing direction and update the last direction
        if (activeDirections.contains(PlayerActType.left)) {
            facingRight = false;
            lastDirection = PlayerActType.left;
        } else if (activeDirections.contains(PlayerActType.right)) {
            facingRight = true;
            lastDirection = PlayerActType.right;
        } else if (activeDirections.contains(PlayerActType.up)) {
            lastDirection = PlayerActType.up;
        } else if (activeDirections.contains(PlayerActType.down)) {
            lastDirection = PlayerActType.down;
        }
    }

    private void moveCharacter(float deltaTime) {
        Vector2 movement = new Vector2();

        if (activeDirections.contains(PlayerActType.up)) {
            movement.y += 1;
        }
        if (activeDirections.contains(PlayerActType.down)) {
            movement.y -= 1;
        }
        if (activeDirections.contains(PlayerActType.left)) {
            movement.x -= 1;
        }
        if (activeDirections.contains(PlayerActType.right)) {
            movement.x += 1;
        }

        // Normalize to ensure diagonal movement isn't faster
        if (movement.len() > 0) {
            movement.nor();
        }

        // Update position with speed and delta time
        position.add(movement.scl(speed * deltaTime));
    }

    private void updateDirection() {
        if (activeDirections.isEmpty()) {
            // Set the idle direction based on the last moving direction
            switch (lastDirection) {
                case up:
                    direction = PlayerActType.u_idle;
                    break;
                case down:
                    direction = PlayerActType.d_idle;
                    break;
                case left:
                    direction = PlayerActType.l_idle;
                    break;
                case right:
                    direction = PlayerActType.r_idle;
                    break;
                default:
                    direction = PlayerActType.d_idle; // Mặc định đứng yên xuống nếu không rõ hướng trước đó
                    break;
            }
        } else {
            if (activeDirections.contains(PlayerActType.up)) {
                direction = PlayerActType.up;
            } else if (activeDirections.contains(PlayerActType.down)) {
                direction = PlayerActType.down;
            } else if (activeDirections.contains(PlayerActType.left)) {
                direction = PlayerActType.left;
            } else if (activeDirections.contains(PlayerActType.right)) {
                direction = PlayerActType.right;
            }
        }
    }

    public boolean isWalking() {
        // Kiểm tra xem nhân vật có đang di chuyển hay không
        return direction != PlayerActType.d_idle && direction != PlayerActType.u_idle
            && direction != PlayerActType.l_idle && direction != PlayerActType.r_idle;
    }

    public String getDirection() {
        // Trả về hướng di chuyển hiện tại dưới dạng chuỗi
        return direction.toString();
    }

    public boolean isFacingRight() {
        // Kiểm tra nhân vật có đang đối mặt về phía bên phải hay không
        return facingRight;
    }

    public float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }
}
