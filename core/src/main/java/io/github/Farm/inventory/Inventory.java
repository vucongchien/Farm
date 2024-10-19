package io.github.Farm.inventory;


import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Color;
import io.github.Farm.player.PlayerController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Inventory {

    private static Inventory instance;

    public static Inventory getInstance() {
        if (instance == null) {
            instance = new Inventory();
        }
        return instance;
    }
    private final List<InventorySlot> slots = new ArrayList<>();
    private final BitmapFont font = new BitmapFont();
    private final ShapeRenderer shapeRenderer = new ShapeRenderer();
    private final int maxSlots=20;
    private final float slotSize=60;
    private final int columns=5;
    private final int rows = (int) Math.ceil((double) maxSlots / columns);
    private final float totalWidth = columns * slotSize;
    private final float totalHeight = rows * slotSize;

    private int selectedItemIndex = 0;

    private boolean isOpened=false;


    public Inventory() {
        font.setColor(Color.BLACK);
    }


    public void addItem(String name, int quantity) {
        for(InventorySlot slot:slots){
            if(name.equals(slot.getFULL_NAME())){
                slot.raiseQuantity();
                return;
            }
        }
        if (slots.size() < maxSlots) {
            slots.add(new InventorySlot(name, quantity));
        }
    }

    public void useSelectedItem(PlayerController playerController) {


        InventorySlot selectedInventorySlot = getSelectedItem();
        if (selectedInventorySlot != null) {
            selectedInventorySlot.use(playerController);
            if (selectedInventorySlot.getQuantity() <= 0) {
                slots.remove(selectedInventorySlot);
            }
        }
    }

    public InventorySlot getSelectedItem() {
        if (selectedItemIndex >= 0 && selectedItemIndex < slots.size()) {
            return slots.get(selectedItemIndex);
        }
        return null;
    }

    public boolean useItem(String Full_Name){
        for(InventorySlot slot:slots){
            if(Full_Name.equals(slot.getFULL_NAME())){
                slot.reduceQuantity();
                if (slot.getQuantity() <= 0) {
                    slots.remove(slot);
                }
                return true;
            }
        }
        return false;
    }


    public void moveSelectionUp() {
        if (selectedItemIndex >= columns) {
            selectedItemIndex -= columns;
        }
    }



    public void moveSelectionDown() {
        if (selectedItemIndex + columns < slots.size()) {
            selectedItemIndex += columns;
        }
    }


    public void moveSelectionLeft() {
        if (selectedItemIndex % columns > 0) {
            selectedItemIndex--;
        }
    }



    public void moveSelectionRight() {
        if ((selectedItemIndex + 1) % columns != 0 && selectedItemIndex + 1 < slots.size()) {
            selectedItemIndex++;
        }
    }


    public void draw(SpriteBatch batch, OrthographicCamera camera, Vector2 playerPosition) {
        batch.setProjectionMatrix(camera.combined);

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
        for (int i = 0; i < slots.size(); i++) {
            InventorySlot inventorySlot = slots.get(i);
            float x = inventoryX + (i % columns) * slotSize;
            float y = inventoryY + totalHeight - ((i / columns + 1) * slotSize);

            // Vẽ texture item
            batch.draw(inventorySlot.getTexture(), x + 5, y + 5, slotSize - 10, slotSize - 10);
            // Vẽ số lượng item
            font.draw(batch, String.valueOf(inventorySlot.getQuantity()), x + slotSize - 20, y + 20);
        }
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.WHITE);
        float selectedX = (selectedItemIndex % columns) * slotSize;
        float selectedY = totalHeight - ((selectedItemIndex / columns + 1) * slotSize);
        shapeRenderer.rect(inventoryX + selectedX, inventoryY + selectedY, slotSize, slotSize);  // Vẽ viền đỏ quanh ô được chọn
        shapeRenderer.end();
    }


    public boolean isOpened() {
        return isOpened;
    }

    public void setOpened(){
        isOpened=!isOpened;
    }

    public List<InventorySlot> getSlots() {
        return slots;
    }

    // Giải phóng tài nguyên
    public void dispose() {
        font.dispose();
        shapeRenderer.dispose();
        slots.forEach(InventorySlot::dispose);
    }


}
