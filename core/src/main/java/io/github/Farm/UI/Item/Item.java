package io.github.Farm.UI.Item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Item {
    private Vector2 position;
    private String itemType; // Ví dụ: "SEED", "FRUIT", "TOOL"
    private int quantity;

    public Item(Vector2 position, String itemType, int quantity) {
        this.position = position;
        this.itemType = itemType;
        this.quantity = quantity;
    }

    public Vector2 getPosition() {
        return position;
    }

    public String getItemType() {
        return itemType;
    }

    public int getQuantity() {
        return quantity;
    }

    public void render(SpriteBatch batch) {
        // Vẽ item trên màn hình
        // Ví dụ: batch.draw(texture, position.x, position.y);
    }

    public void update(float deltaTime) {
        // Cập nhật trạng thái item nếu cần
    }
}
