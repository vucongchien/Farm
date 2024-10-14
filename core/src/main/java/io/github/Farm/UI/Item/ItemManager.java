package io.github.Farm.UI.Item;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class ItemManager {
    private List<Item> items;

    public ItemManager() {
        items = new ArrayList<>();
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public void update(float deltaTime) {
        for (Item item : items) {
            item.update(deltaTime);
        }
    }

    public void render(SpriteBatch batch) {
        for (Item item : items) {
            item.render(batch);
        }
    }

    public List<Item> getItems() {
        return items;
    }

    public Item getItemAtPosition(Vector2 position) {
        for (Item item : items) {
            if (item.getPosition().epsilonEquals(position, 0.1f)) {
                return item;
            }
        }
        return null;
    }
}
