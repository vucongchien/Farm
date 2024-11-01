package io.github.Farm.Ship;


import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.Farm.Interface.Collider;
import io.github.Farm.inventory.Inventory;
import io.github.Farm.player.PlayerController;
import io.github.Farm.ui.SettingGame;
import io.github.Farm.ui.WinGame;


public class Ship implements Collider {

    private Rectangle collider;
    private boolean isNearPlayer;
    private Vector2 position;



    public Ship(){
        position=new Vector2(3900,900);
        collider=new Rectangle(position.x, position.y, 50,50);

    }


    public void update(PlayerController playerController){
        checkCollision(playerController);

    }

    private void checkCollision(PlayerController player) {
        isNearPlayer= collider.overlaps(player.getCollider());
        if(isNearPlayer) {

            boolean hasCarrot = Inventory.getInstance().getQuantitySlot("FOOD_carrot") >= 50;
            boolean hasPumpkin = Inventory.getInstance().getQuantitySlot("FOOD_pumpkin") >= 50;
            boolean hasMilk = Inventory.getInstance().getQuantitySlot("FOOD_milk") >= 50;
            boolean hasPotato = Inventory.getInstance().getQuantitySlot("FOOD_potato") >= 50;
            boolean hasKale = Inventory.getInstance().getQuantitySlot("FOOD_kale") >= 50;
            boolean hasCabbage = Inventory.getInstance().getQuantitySlot("FOOD_cabbage") >= 50;
            boolean hasEgg = Inventory.getInstance().getQuantitySlot("FOOD_egg") >= 50;
            boolean hasFish = Inventory.getInstance().getQuantitySlot("FOOD_fish") >= 50;
            boolean hasWood = Inventory.getInstance().getQuantitySlot("MATERIAL_wood") >= 50;
            boolean hasRock = Inventory.getInstance().getQuantitySlot("MATERIAL_rock") >= 50;

            if (hasCarrot && hasPumpkin && hasMilk && hasPotato && hasKale &&
                hasCabbage && hasEgg && hasFish && hasWood && hasRock) {

                WinGame.getInstance().setIsWin(true);
                SettingGame.getInstance().clearFile();
            }
        }
    }


    @Override
    public Rectangle getCollider() {
        return collider;
    }

    @Override
    public void onCollision(Collider other) {

    }

    public boolean isNearPlayer() {
        return isNearPlayer;
    }

    public void setNearPlayer(boolean nearPlayer) {
        isNearPlayer = nearPlayer;
    }

    public Vector2 getPosition() {
        return position;
    }

}
