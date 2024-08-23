package io.github.Farm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

public class PlayerController {
    private Vector2 position;    // Vị trí của nhân vật
    private float speed;         // Tốc độ di chuyển của nhân vật
    private boolean facingRight = true;  // Hướng mà nhân vật đang đối diện (phải hoặc trái)
    private float size;          // Kích thước của nhân vật
    private PlayerActType direction;  // Hướng di chuyển hiện tại của nhân vật

    public PlayerController(Vector2 startPosition, float speed, float size) {
        this.position = startPosition;
        this.speed = speed;
        this.size = size;
        this.direction = PlayerActType.idle;  // Mặc định nhân vật ở trạng thái đứng yên
    }

    public Vector2 getPosition() {
        return position;
    }

    public float getSize() {
        return size;
    }

    public void update(float deltaTime) {
        handleInput(deltaTime);
    }

    private void handleInput(float deltaTime) {
        float moveAmount = speed * deltaTime;  // Tính toán khoảng cách di chuyển dựa trên tốc độ và thời gian đã trôi qua
        float moveX = 0;
        float moveY = 0;

        // Kiểm tra các phím nhập để điều khiển di chuyển
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveX -= moveAmount;
            facingRight = false;  // Nếu di chuyển sang trái, hướng quay mặt sẽ là trái
            direction = PlayerActType.left;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveX += moveAmount;
            facingRight = true;  // Nếu di chuyển sang phải, hướng quay mặt sẽ là phải
            direction = PlayerActType.right;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveY += moveAmount;
            direction = PlayerActType.up;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveY -= moveAmount;
            direction = PlayerActType.down;
        }

        // Điều chỉnh di chuyển theo đường chéo
        if (moveX != 0 && moveY != 0) {
            float length = (float) Math.sqrt(moveX * moveX + moveY * moveY);
            moveX /= length;
            moveY /= length;
        }

        // Cập nhật vị trí của nhân vật
        position.x += moveX;
        position.y += moveY;

        // Cập nhật trạng thái di chuyển của nhân vật
        if (moveX == 0 && moveY == 0) {
            direction = PlayerActType.idle;
        }
    }

    public boolean isWalking() {
        // Kiểm tra xem nhân vật có đang di chuyển hay không
        return direction != PlayerActType.idle;
    }

    public String getDirection() {
        // Trả về hướng di chuyển hiện tại dưới dạng chuỗi
        return direction.toString();
    }

    // Kiểm tra nhân vật có đang đối mặt về phía bên phải hay không
    public boolean isFacingRight() {
        return facingRight;
    }

    public float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }
}
