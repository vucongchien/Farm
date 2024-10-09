package io.github.Farm.inventory;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Color;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
    private Map<String, Item> items;
    private BitmapFont font;
    private int maxSlots;
    private float slotSize;
    private float totalWidth, totalHeight;
    private ShapeRenderer shapeRenderer;
    private int columns;

    private int selectedItemIndex = 0;


    // Constructor mặc định
    public Inventory() {
        this(20, 60);
    }

    public Inventory(int maxSlots, float slotSize) {
        this.items = new HashMap<>();
        this.font = new BitmapFont();
        this.font.setColor(Color.BLACK);  // Đặt màu chữ là đen
        this.shapeRenderer = new ShapeRenderer();
        this.maxSlots = maxSlots;
        this.slotSize = slotSize;

        this.columns = 5;
        int rows = (int) Math.ceil(maxSlots / (float) columns);

        totalWidth = columns * slotSize;
        totalHeight = rows * slotSize;

    }

    public void addItem(String name, Texture texture, int quantity) {
        if (items.size() < maxSlots) {
            items.put(name, new Item(name, texture, quantity));
        }
    }

    public void useSelectedItem() {
        Item selectedItem = getSelectedItem();
        if (selectedItem != null) {
            selectedItem.reduceQuantity();
            if (selectedItem.getQuantity() <= 0) {
                items.remove(selectedItem.getName());
            }
        }
    }

    // Lấy vật phẩm hiện tại được chọn
    public Item getSelectedItem() {
        int i = 0;
        for (Item item : items.values()) {
            if (i == selectedItemIndex) {
                return item;
            }
            i++;
        }
        return null;
    }


    public void moveSelectionUp() {
        if (selectedItemIndex >= columns) {
            selectedItemIndex -= columns;
        }
    }



    public void moveSelectionDown() {
        if (selectedItemIndex + columns < items.size()) { // Kiểm tra không ra ngoài
            selectedItemIndex += columns; // Di chuyển xuống một hàng
        }
    }


    public void moveSelectionLeft() {
        if (selectedItemIndex % columns > 0) {
            selectedItemIndex--; // Di chuyển sang trái
        }
    }



    public void moveSelectionRight() {
        if ((selectedItemIndex + 1) % columns != 0 && selectedItemIndex + 1 < items.size()) {
            selectedItemIndex++; // Di chuyển sang phải
        }
    }


    // Vẽ inventory và làm nổi bật vật phẩm đang được chọn
    public void draw(SpriteBatch batch, OrthographicCamera camera, Vector2 playerPosition) {
        float inventoryX = playerPosition.x - (totalWidth / 2);
        float inventoryY = playerPosition.y - (totalHeight / 2);

        // Vẽ background và khung của inventory
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(Color.LIGHT_GRAY);  // Màu nền xám nhạt
        shapeRenderer.rect(inventoryX, inventoryY, totalWidth, totalHeight);
        shapeRenderer.end();

        // Vẽ viền cho các ô
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.DARK_GRAY);
        for (int i = 0; i <= columns; i++) {
            shapeRenderer.line(inventoryX + i * slotSize, inventoryY, inventoryX + i * slotSize, inventoryY + totalHeight);
        }
        for (int i = 0; i <= maxSlots / columns; i++) {
            shapeRenderer.line(inventoryX, inventoryY + i * slotSize, inventoryX + totalWidth, inventoryY + i * slotSize);
        }
        shapeRenderer.end();

        // Vẽ các vật phẩm trước
        batch.begin();
        int i = 0;
        for (Item item : items.values()) {
            float x = inventoryX + (i % columns) * slotSize;
            float y = inventoryY + totalHeight - ((i / columns + 1) * slotSize);

            // Vẽ texture vật phẩm
            batch.draw(item.getTexture(), x + 5, y + 5, slotSize - 10, slotSize - 10);  // Để lại khoảng trống nhỏ
            // Vẽ số lượng vật phẩm
            font.draw(batch, String.valueOf(item.getQuantity()), x + slotSize - 20, y + 20);
            i++;
        }
        batch.end();

        // Vẽ viền đỏ quanh vật phẩm được chọn sau khi vẽ texture và số lượng
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        float selectedX = (selectedItemIndex % columns) * slotSize;
        float selectedY = totalHeight - ((selectedItemIndex / columns + 1) * slotSize);
        shapeRenderer.rect(inventoryX + selectedX, inventoryY + selectedY, slotSize, slotSize);  // Vẽ viền đỏ quanh ô được chọn
        shapeRenderer.end();
    }




    // Giải phóng tài nguyên
    public void dispose() {
        font.dispose();
        shapeRenderer.dispose();
        for (Item item : items.values()) {
            item.dispose();
        }
    }


}
