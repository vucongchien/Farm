package io.github.Farm.player.lam_lai_file;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import io.github.Farm.UI.Inventory;

public class InputHandler {

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
        return movement.len() > 1 ? movement.nor() : movement;
    }

    public boolean isPlowing() {
        return Gdx.input.isKeyPressed(Input.Keys.F);
    }

    public boolean isHitting() {
        return Gdx.input.isKeyPressed(Input.Keys.J);
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

    public void handleInventoryInput(Inventory inventory) {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            inventory.moveSelection(-1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            inventory.moveSelection(1);
        }

    }
}
