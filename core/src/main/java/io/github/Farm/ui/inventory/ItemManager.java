package io.github.Farm.ui.inventory;

import com.badlogic.gdx.math.Vector2;
import io.github.Farm.player.PlayerController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class ItemManager  {

    private static ItemManager instance;

    private List<Item> itemList;
    public ItemManager() {
        itemList = new ArrayList<>();
    }

    public void addItem(String name, Vector2 position,boolean isPlayerFacingRight,int quantity) {
        for(int i=0;i<quantity;i++) {
            Item item = new Item(name, new Vector2(position), isPlayerFacingRight);
            itemList.add(item);
        }

    }

    public static ItemManager getInstance() {
        if (instance == null) {
            instance = new ItemManager();
        }
        return instance;
    }

    public void update(float deltaTime, PlayerController playerController) {

    }

    public List<Item> getItemList() {
        return itemList;
    }

    public void dispose() {
        for (Item item : itemList) {
            item.dispose();
        }
    }

}
