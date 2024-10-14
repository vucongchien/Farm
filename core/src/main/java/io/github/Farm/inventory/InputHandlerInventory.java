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
    //private Sound moveSound;
    //private Sound SoundEnter;

    public InputHandlerInventory(OrthographicCamera camera, Rectangle backpackBounds, Inventory inventory, boolean isInGame) {
        this.camera = camera;
        this.backpackBounds = backpackBounds;
        this.inventory = inventory;
        this.isInGame = isInGame;

       // moveSound = Gdx.audio.newSound(Gdx.files.internal("soundgame/sound_movebuttonmenu.wav"));
      //  SoundEnter = Gdx.audio.newSound(Gdx.files.internal("soundgame/sound_enterbutton.wav"));
    }

    @Override
    public boolean keyDown(int keycode) {
        if (keycode == Keys.I) {
          //  moveSound.play();
            isInGame = !isInGame;
        }

        if (isInGame) {
            if (keycode == Keys.UP) {
              //  moveSound.play();
                inventory.moveSelectionUp();
            } else if (keycode == Keys.DOWN) {
              //  moveSound.play();
                inventory.moveSelectionDown();
            } else if (keycode == Keys.LEFT) {
              //  moveSound.play();
                inventory.moveSelectionLeft();
            } else if (keycode == Keys.RIGHT) {
              //  moveSound.play();
                inventory.moveSelectionRight();
            } else if (keycode == Keys.ENTER) {
             //   moveSound.play();
//                inventory.useSelectedItem();
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
