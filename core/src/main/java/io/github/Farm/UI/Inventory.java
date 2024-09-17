package io.github.Farm.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class Inventory {
    private InventorySlot[] slots;
    private Texture slotTexture;
    private Texture selectionCornersTexture;
    private int selectedSlotIndex;

    public Inventory() {
        this.slotTexture = new Texture("UI/Inventory/Inventory_Slot.png");;
        this.selectionCornersTexture =new Texture("UI/Inventory/Inventory_select.png");

        this.slots = new InventorySlot[20];

        int slotSize = 16;
        int slotsPerRow = 5;
        int padding = 2;

        for (int i = 0; i < 20; i++) {
            int x = (i % slotsPerRow) * (slotSize + padding);
            int y = (i / slotsPerRow) * (slotSize + padding);
            slots[i] = new InventorySlot(new Vector2(x, y), slotTexture,  0, null);
        }


        selectedSlotIndex = 0;
    }

    public void render(SpriteBatch batch) {
        batch.begin();
        for (InventorySlot slot : slots) {
            slot.render(batch);
        }

        InventorySlot selectedSlot = slots[selectedSlotIndex];
        batch.draw(selectionCornersTexture, selectedSlot.getPosition().x, selectedSlot.getPosition().y);
        batch.end();
    }

    // Hàm thêm item vào inventory
    public void add(int quantity,String itemType) {
        for (InventorySlot slot : slots) {
            if(slot.getItemType()==null){
                break;
            }
            if (slot.getItemType().equals(itemType)&&!slot.isFullSlot()) {
                slot.increaseQuantity(quantity);
                return;
            }
        }

        for (InventorySlot slot : slots) {
            if (!slot.hasItem()) {
                slot.setItemType(itemType);
                slot.setItem(new Texture((String)("Crops/" + itemType + "_05.png")), quantity);
                return;
            }
        }
    }

    public void moveSelection(int direction) {
        selectedSlotIndex = (selectedSlotIndex + direction) % 20;
        if (selectedSlotIndex < 0) {
            selectedSlotIndex += 20;
        }
    }

    public InventorySlot getSelectedSlot() {
        return slots[selectedSlotIndex];
    }
}
