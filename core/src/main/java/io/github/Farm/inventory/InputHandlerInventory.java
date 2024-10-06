package io.github.Farm.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import io.github.Farm.inventory.Inventory;

public class InputHandlerInventory extends InputAdapter {
    private OrthographicCamera camera;
    private Rectangle backpackBounds;
    private Inventory inventory;
    private boolean isInGame;
    private Sound moveSound; // Biến để lưu âm thanh di chuyển
    private Sound SoundEnter; // Biến để lưu âm thanh enter

    public InputHandlerInventory(OrthographicCamera camera, Rectangle backpackBounds, Inventory inventory, boolean isInGame) {
        this.camera = camera;
        this.backpackBounds = backpackBounds;
        this.inventory = inventory;
        this.isInGame = isInGame;
        // Khởi tạo âm thanh di chuyển
        moveSound = Gdx.audio.newSound(Gdx.files.internal("soundgame/sound_movebuttonmenu.wav"));
        SoundEnter = Gdx.audio.newSound(Gdx.files.internal("soundgame/sound_enterbutton.wav"));
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.I) {
            moveSound.play();
            isInGame = !isInGame; // Chuyển đổi trạng thái mở/tắt inventory
        }

        if (isInGame) {
            if (keycode == Keys.UP) {
                moveSound.play();
                inventory.moveSelectionUp();  // Di chuyển lên
            } else if (keycode == Keys.DOWN) {
                moveSound.play();
                inventory.moveSelectionDown();  // Di chuyển xuống
            } else if (keycode == Keys.LEFT) {
                moveSound.play();
                inventory.moveSelectionLeft();  // Di chuyển sang trái
            } else if (keycode == Keys.RIGHT) {
                moveSound.play();
                inventory.moveSelectionRight();  // Di chuyển sang phải
            } else if (keycode == Keys.ENTER) {
                moveSound.play();
                inventory.useSelectedItem();  // Sử dụng vật phẩm
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
