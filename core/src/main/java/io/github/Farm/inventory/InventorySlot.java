package io.github.Farm.inventory;

import com.badlogic.gdx.graphics.Texture;
import io.github.Farm.Plants.PlantType;
import io.github.Farm.player.PLAYER_STATE.DoingState;
import io.github.Farm.player.PlayerController;

public class InventorySlot {
    private String FULL_NAME,TYPE,NAME;  //TYPE_NAME
    private Texture texture;
    private int quantity;


    public InventorySlot(String FULL_NAME, int quantity) {
        this.FULL_NAME = FULL_NAME;

        String[] tmp=FULL_NAME.split("_");
        this.TYPE=tmp[0];
        this.NAME=tmp[1];

        this.texture = new Texture("UI/Item/" + TYPE + "/" + NAME + ".png");
        this.quantity = quantity;
    }

    public void use(PlayerController playerController){
        switch (TYPE){
            case "FOOD":
                playerController.getHeath().heal(-10);
                reduceQuantity();
                return;

            case "SEED":
                playerController.getCollisionHandler().plantSeed(PlantType.valueOf(NAME));
                return;
        }
    }

    public String getFULL_NAME() {
        return FULL_NAME;
    }

    public int getQuantity() {
        return quantity;
    }

    public void raiseQuantity(){
        this.quantity++;
    }

    public void reduceQuantity() {
        this.quantity--;
    }

    public Texture getTexture() {
        return texture;
    }

    public void dispose() {
        if (texture != null) {
            texture.dispose();
        }
    }
}
