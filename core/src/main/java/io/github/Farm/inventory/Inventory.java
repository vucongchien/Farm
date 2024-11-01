package io.github.Farm.inventory;


import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.Color;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.Farm.player.PlayerController;
import io.github.Farm.ui.MainMenu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
    private final String link="inventoryData.json";
    private Texture slotBorderTexture;      // Texture cho viền ô
    private Texture selectedSlotBorderTexture;

    private int selectedItemIndex = 0;

    private boolean isOpened=false;


    public Inventory() {

        this.slotBorderTexture=new Texture("UI/Inventory/Inventory_Slot.png");
        this.selectedSlotBorderTexture=new Texture("UI/Inventory/Inventory_select.png");
        if(MainMenu.isCheckcontinue()){
            font.setColor(Color.BLACK);
            readInventoty(slots);
        }else {
            font.setColor(Color.BLACK);
        }
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

    public boolean checkSoLuong(String name,int soluong){
        for(InventorySlot slot:slots){
            if(name.equals(slot.getFULL_NAME())){
                return slot.getQuantity()==soluong;
            }
        }
        return false;
    }

    public int getQuantitySlot(String name){
        for(InventorySlot slot:slots){
            if(name.equals(slot.getFULL_NAME())){
                return slot.getQuantity();
            }
        }
        return 0;
    }



    public boolean dropItem(String Full_Name){
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

        // Bắt đầu vẽ background cho inventory
        batch.begin();
        // batch.draw(slotBackgroundTexture, inventoryX, inventoryY, totalWidth, totalHeight); // Vẽ nền inventory

        // Vẽ viền cho các ô bằng cách dùng texture slotBorderTexture
        for (int i = 0; i < maxSlots ; i++) {
            float x = inventoryX + (i % columns) * slotSize;
            float y = inventoryY + totalHeight - ((i / columns + 1) * slotSize);
            batch.draw(slotBorderTexture, x, y, slotSize, slotSize); // Vẽ viền của ô
        }

        // Vẽ các vật phẩm trong các ô
        for (int i = 0; i < slots.size(); i++) {
            InventorySlot inventorySlot = slots.get(i);
            float x = inventoryX + (i % columns) * slotSize;
            float y = inventoryY + totalHeight - ((i / columns + 1) * slotSize);

            batch.draw(inventorySlot.getTexture(), x +20- inventorySlot.getTexture().getWidth()/2, y + 20-inventorySlot.getTexture().getHeight()/2, inventorySlot.getTexture().getWidth()*3, inventorySlot.getTexture().getHeight()*3);

            font.draw(batch, String.valueOf(inventorySlot.getQuantity()), x + slotSize - 20, y + 20);
        }

        // Vẽ viền của ô được chọn
        float selectedX = inventoryX + (selectedItemIndex % columns) * slotSize;
        float selectedY = inventoryY + totalHeight - ((selectedItemIndex / columns + 1) * slotSize);
        batch.draw(selectedSlotBorderTexture, selectedX, selectedY, slotSize, slotSize); // Vẽ viền ô được chọn

        batch.end();
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

    public void readInventoty(List<InventorySlot> slots){
        ObjectMapper objectMapper = new ObjectMapper();
        try{
            JsonNode rootNode = objectMapper.readTree(new File(link));
            JsonNode itemNode =rootNode.get("items");
            for(JsonNode inventoryData:itemNode){
                String name = inventoryData.get("name").asText();
                double quantity = inventoryData.get("quantity").asDouble();
                InventorySlot inventorySlot=new InventorySlot(name,(int)quantity);
                slots.add(inventorySlot);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
