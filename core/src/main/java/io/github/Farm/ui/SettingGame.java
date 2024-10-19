package io.github.Farm.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator.FreeTypeFontParameter;
import com.badlogic.gdx.math.Vector2;
import io.github.Farm.Plants.PlantManager;
import io.github.Farm.Plants.PlantRenderer;
import io.github.Farm.SoundManager;
import io.github.Farm.data.*;
import io.github.Farm.inventory.Inventory;
import io.github.Farm.inventory.InventorySlot;
import io.github.Farm.player.PlayerController;

import java.util.List;


public class SettingGame {
    private boolean isActive;
    private BitmapFont font;
    private GlyphLayout layout;
    private String[] options;
    private int selectedOption;
    private Texture panelBackground;
    private Texture settingsIcon;
    private float iconSize = 64;
    private boolean isMusicPlaying;

    private GameData gameData;
    private PlayerController playerController;

    public SettingGame(GameData gameData,PlayerController playerController) {

        this.gameData=gameData;
        this.playerController=playerController;

        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font_ingame/KaushanScript-Regular.ttf"));
        FreeTypeFontParameter parameter = new FreeTypeFontParameter();
        parameter.size = 36;

        parameter.borderWidth = 2;
        parameter.borderColor = Color.BLACK;
        this.font = generator.generateFont(parameter);
        generator.dispose();


        isActive = false;
        layout = new GlyphLayout();
        options = new String[]{"Save Game", "Sound", "Exit"};
        selectedOption = 0;
        panelBackground = new Texture("Setting/table.png");
        isMusicPlaying = true;
        SoundManager.getInstance().playGameMusic();
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            SoundManager.getInstance().playMoveSound();
            isActive = !isActive;
        }

        if (isActive) {
            if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
                SoundManager.getInstance().playMoveSound();
                selectedOption = (selectedOption - 1 + options.length) % options.length;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
                SoundManager.getInstance().playMoveSound();
                selectedOption = (selectedOption + 1) % options.length;
            }
            if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
                performAction(selectedOption);
            }
        }

    }

    private void performAction(int option) {
        switch (option) {
            case 0:

                updatePlayerData(gameData.getPlayer(), playerController);
                updatePlantsData(gameData.getPlants());
                updateInventoryData(gameData.getInventory());

                GameSaveManager.getInstance().savePlayerData(gameData.getPlayer());
                GameSaveManager.getInstance().savePlantsData(gameData.getPlants());
                GameSaveManager.getInstance().saveInventoryData(gameData.getInventory());

                System.out.println("Game Saved!");
                break;
            case 1:

                isMusicPlaying = !isMusicPlaying;
                options[1] = isMusicPlaying ? "Sound: ON" : "Sound: OFF";
                if (SoundManager.getInstance().isGameMusicPlaying()) {
                    SoundManager.getInstance().pauseGameMusic();

                } else {
                    SoundManager.getInstance().playGameMusic();
                }
                break;
            case 2:
                Gdx.app.exit();
                break;
        }
    }

    public void render(SpriteBatch batch, Vector2 playerPosition) {

        if (isActive) {
            batch.begin();

            // Lấy kích thước của màn hình
            float screenWidth = Gdx.graphics.getWidth();
            float screenHeight = Gdx.graphics.getHeight();

            // Kích thước của bảng (panel)
            float panelWidth = 400;
            float panelHeight = 300;

            // Vị trí của bảng (căn giữa nhân vật)
            float panelX = playerPosition.x - panelWidth / 2;
            float panelY = playerPosition.y - panelHeight / 2;

            // Vẽ hình nền của bảng
            batch.draw(panelBackground, panelX, panelY, panelWidth, panelHeight);

            // Căn giữa các tùy chọn trong bảng
            float optionHeight = 50;
            float totalOptionsHeight = options.length * optionHeight; // Tổng chiều cao của tất cả các tùy chọn
            float startX = panelX + (panelWidth - layout.width) / 2; // Căn giữa theo chiều ngang của bảng
            float startY = panelY + (panelHeight + totalOptionsHeight) / 2; // Căn giữa theo chiều dọc của bảng

            for (int i = 0; i < options.length; i++) {
                // Tính toán chiều rộng của text để căn giữa theo chiều ngang
                layout.setText(font, options[i]);
                float textWidth = layout.width;

                // Căn giữa text theo chiều ngang của bảng
                float x = panelX + (panelWidth - textWidth) / 2;
                float y = startY - i * optionHeight;

                if (i == selectedOption) {
                    font.setColor(1, 1, 0, 1); // Màu vàng cho tùy chọn được chọn
                } else {
                    font.setColor(1, 1, 1, 1); // Màu trắng cho các tùy chọn khác
                }

                font.draw(batch, options[i], x, y);
            }

            batch.end();
        }
    }

    private void updatePlayerData(PlayerData playerData, PlayerController playerController) {

        playerData.setPosition(playerController.getPosition());

        playerData.setHealth(playerController.getHeath().getCurrHp());

    }

    private void updatePlantsData(List<PlantData> plantDataList) {
        List<PlantRenderer> plants = PlantManager.getInstance().getListPlants();

        if(!plantDataList.isEmpty()) {
            plantDataList.clear();
        }
        for (PlantRenderer plant : plants) {

            PlantData plantData = new PlantData();
            plantData.setType(plant.getType().toString());
            plantData.setStage(plant.getStage().toString());
            plantData.setPosition(plant.getPosition());

            plantDataList.add(plantData);
        }
    }

    private void updateInventoryData(InventoryData inventoryData) {
        inventoryData.getItems().clear();
        List<InventorySlot> slots=Inventory.getInstance().getSlots();

        for(InventorySlot inventorySlot:slots) {
            inventoryData.addItem(inventorySlot.getFULL_NAME(), inventorySlot.getQuantity());
        }
    }



    public void dispose() {
        // Giải phóng tài nguyên font
        if (font != null) {
            font.dispose();
        }

        // Giải phóng hình nền bảng
        if (panelBackground != null) {
            panelBackground.dispose();
        }
    }
}
