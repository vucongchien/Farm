package io.github.Farm.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.math.Vector2;

public class InventorySlot {
    private final Vector2 position;
    private final Texture slotTexture;
    private Texture itemTexture;
    private int itemQuantity;
    private final BitmapFont font;
    private String itemType;

    public InventorySlot(Vector2 position, Texture slotTexture,  int initialQuantity,String itemType) {
        this.position = position;
        this.slotTexture = slotTexture;
        this.itemQuantity = initialQuantity;
        this.font = new BitmapFont();
        this.itemType=itemType;
        if(itemType!=null) {
            this.itemTexture = new Texture((String) ("Crops/" + itemType + "_05.png"));
        }
        else {
            this .itemTexture=null;
        }
    }

    public void render(SpriteBatch batch) {

        batch.draw(slotTexture, position.x, position.y);
        if (itemTexture != null) {
            batch.draw(itemTexture, position.x+2, position.y);
            font.getData().setScale(0.3f);
            font.draw(batch, String.valueOf(itemQuantity), position.x + 2, position.y + 18);
        }
    }

    public boolean hasItem() {
        return itemType != null;
    }

    public void setItem(Texture itemTexture, int quantity) {
        this.itemTexture = itemTexture;
        this.itemQuantity = quantity;
    }

    public void increaseQuantity(int amount) {
        this.itemQuantity += amount;
    }
    public void decreaseQuantity(int amout){
        this.itemQuantity-=amout;
        if(this.itemQuantity==0){
            itemType=null;
            itemTexture=null;
        }
    }

    public int getItemQuantity() {
        return itemQuantity;
    }

    public Texture getItemTexture() {
        return itemTexture;
    }

    public Vector2 getPosition() {
        return position;
    }

    public String getItemType(){
        return itemType;
    }

    public boolean isFullSlot(){
        return itemQuantity > 10;
    }
    public void setItemType(String itemType){
        this.itemType=itemType;
    }
}
