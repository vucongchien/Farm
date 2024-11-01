package io.github.Farm.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import io.github.Farm.inventory.Inventory;

public class InputHandler implements Disposable {
    PlayerController playerController;

    public InputHandler(PlayerController playerController){
        this.playerController=playerController;
    }

    public Vector2 handleMovementInput() {

        Vector2 movement = new Vector2();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            movement.y = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            movement.y = -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            movement.x = -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            movement.x = 1;
        }
        return movement.len() > 1 ? (movement.nor()) : movement;
    }

    public void handleInventoryInput(){
        if (Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            Inventory.getInstance().moveSelectionUp();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            Inventory.getInstance().moveSelectionDown();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            Inventory.getInstance().moveSelectionLeft();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            Inventory.getInstance().moveSelectionRight();
        }
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            Inventory.getInstance().useSelectedItem(playerController);
        }
    }

    public boolean isPlowing() {
        return Gdx.input.isKeyPressed(Input.Keys.K);
    }

    public boolean isHitting() {
        return Gdx.input.isKeyPressed(Input.Keys.J);
    }

    public boolean isHammer(){
        return Gdx.input.isKeyPressed(Input.Keys.L);
    }

    public boolean isOpeningInventory() {
        return Gdx.input.isKeyPressed(Input.Keys.SPACE);

    }

    public boolean isMoving(){
        return handleMovementInput().x!=0||handleMovementInput().y!=0;
    }

    public boolean isWatering() {
        return Gdx.input.isKeyPressed(Input.Keys.R);
    }

    public boolean isCasting(){
        return Gdx.input.isKeyPressed(Input.Keys.C);
    }


    @Override
    public void dispose() {

    }
}
