package io.github.Farm.inventory;

import com.badlogic.gdx.graphics.Texture;

public class Item {
    private String name;
    private Texture texture;
    private int quantity;

    public Item(String name, Texture texture, int quantity) {
        this.name = name;
        this.texture = texture;
        this.quantity = quantity;
    }

    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public void reduceQuantity(){this.quantity--;}
    public Texture getTexture() { return texture; }


    public void dispose() {
        texture.dispose();
    }
}
