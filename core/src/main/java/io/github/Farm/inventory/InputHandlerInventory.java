package io.github.Farm;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector3;
import io.github.Farm.Inventory;

public class InputHandlerInventory extends InputAdapter {
    private OrthographicCamera camera;
    private Rectangle backpackBounds;
    private Inventory inventory;
    private boolean isInGame;

    public InputHandlerInventory(OrthographicCamera camera, Rectangle backpackBounds, Inventory inventory, boolean isInGame) {
        this.camera = camera;
        this.backpackBounds = backpackBounds;
        this.inventory = inventory;
        this.isInGame = isInGame;
    }

    @Override
    public boolean keyDown(int keycode) {
        if (isInGame) {
            if (keycode == Keys.UP) {
                inventory.moveSelectionUp();  // Di chuyển lên
            } else if (keycode == Keys.DOWN) {
                inventory.moveSelectionDown();  // Di chuyển xuống
            } else if (keycode == Keys.LEFT) {
                inventory.moveSelectionLeft();  // Di chuyển sang trái
            } else if (keycode == Keys.RIGHT) {
                inventory.moveSelectionRight();  // Di chuyển sang phải
            } else if (keycode == Keys.ENTER) {
                inventory.useSelectedItem();  // Sử dụng vật phẩm
            }
        }
        return true;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 touchPos = new Vector3(screenX, screenY, 0);
        camera.unproject(touchPos);

        if (backpackBounds.contains(touchPos.x, touchPos.y)) {
            isInGame = !isInGame; // Mở/đóng inventory khi nhấp vào balo
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
