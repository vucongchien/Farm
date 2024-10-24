package io.github.Farm.ui.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;

public class InputHandlerInventory extends InputAdapter {
    private OrthographicCamera camera;
    private Rectangle backpackBounds;
    private Inventory inventory;
    private boolean isInGame;
    private Sound moveSound;
    private Sound SoundEnter;

    public InputHandlerInventory(OrthographicCamera camera, Rectangle backpackBounds, Inventory inventory, boolean isInGame) {
        this.camera = camera;
        this.backpackBounds = backpackBounds;
        this.inventory = inventory;
        this.isInGame = isInGame;

        moveSound = Gdx.audio.newSound(Gdx.files.internal("soundgame/sound_movebuttonmenu.wav"));
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.I) {
            moveSound.play(0.05f);
            isInGame = !isInGame;
        }

        if (isInGame) {
            if (keycode == Keys.UP) {
                moveSound.play(0.05f);
                inventory.moveSelectionUp();  // Di chuyển lên
            } else if (keycode == Keys.DOWN) {
                moveSound.play(0.05f);
                inventory.moveSelectionDown();  // Di chuyển xuống
            } else if (keycode == Keys.LEFT) {
                moveSound.play(0.05f);
                inventory.moveSelectionLeft();  // Di chuyển sang trái
            } else if (keycode == Keys.RIGHT) {
                moveSound.play(0.05f);
                inventory.moveSelectionRight();  // Di chuyển sang phải
            } else if (keycode == Keys.ENTER) {
               // moveSound.play(0.05f);
                //inventory.useSelectedItem();  // Sử dụng vật phẩm
            }
        }
        return true;
    }


    public boolean isInGame() {
        return isInGame;
    }

    public void setIsInGame(boolean isInGame) {
        this.isInGame = isInGame;
    }
}
