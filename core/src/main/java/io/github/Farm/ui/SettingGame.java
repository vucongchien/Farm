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
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import io.github.Farm.Plants.PlantManager;
import io.github.Farm.Plants.PlantRenderer;
import io.github.Farm.SoundManager;
import io.github.Farm.animal.Buffalo.Buffalo;
import io.github.Farm.animal.Buffalo.BuffaloManager;
import io.github.Farm.animal.Chicken.ChickenManager;
import io.github.Farm.animal.Chicken.ChickenRender;
import io.github.Farm.animal.Pig.PigManager;
import io.github.Farm.animal.Pig.PigReander;
import io.github.Farm.animal.WOLF.WolfManager;
import io.github.Farm.animal.WOLF.WolfRender;
import io.github.Farm.data.*;
import io.github.Farm.inventory.Inventory;
import io.github.Farm.inventory.InventorySlot;
import io.github.Farm.player.PlayerController;

import java.util.ArrayList;
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
    private TiledMap map;
    private PlayerController playerController;

    public SettingGame(GameData gameData, PlayerController playerController, TiledMap map) {

        this.gameData=gameData;
        this.playerController=playerController;
        this.map=map;

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
                updateAnimalData(gameData.getAnimal());

                GameSaveManager.getInstance().savePlayerData(gameData.getPlayer());
                GameSaveManager.getInstance().savePlantsData(gameData.getPlants());
                GameSaveManager.getInstance().saveInventoryData(gameData.getInventory());
                GameSaveManager.getInstance().saveAnimalData(gameData.getAnimal());
                GameSaveManager.getInstance().saveMapData(map);

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

    private void updateAnimalData(AnimalData animalData){
        if(!animalData.getBuffalo().isEmpty()) {
            animalData.getBuffalo().clear();
        }
        if(!animalData.getWolfRenders().isEmpty()) {
            animalData.getWolfRenders().clear();
        }
        if(!animalData.getStorageWolfRenders().isEmpty()){
            animalData.getStorageWolfRenders().clear();
        }
        if(!animalData.getPig().isEmpty()){
            animalData.getPig().clear();
        }
        if(!animalData.getChicken().isEmpty()){
            animalData.getChicken().clear();
        }
        List<AnimalData.Wolf> list= new ArrayList<>();
        for(WolfRender wolfRender: WolfManager.getwolfmanage().getwolfmanafer()){
            AnimalData.Wolf wolf =new AnimalData.Wolf();
            wolf.setPosition(wolfRender.getlocation());
            wolf.setHealth(wolfRender.getHp().getCurrHp());
            wolf.setLeader(wolfRender.getthulinh());
            wolf.setDistancefrombossx(wolfRender.getdistancefrombossx());
            wolf.setDistancefrombossy(wolfRender.getDistancefrombossy());
            list.add(wolf);
        }
        animalData.setWolfRenders(list);
        if(!list.isEmpty()) {
            list.clear();
        }
        for(WolfRender wolfRender: WolfManager.getwolfmanage().getStoragewolfmanager()){
            AnimalData.Wolf wolf =new AnimalData.Wolf();
            wolf.setPosition(wolfRender.getlocation());
            wolf.setHealth(wolfRender.getHp().getCurrHp());
            wolf.setLeader(wolfRender.getthulinh());
            wolf.setDistancefrombossx(wolfRender.getdistancefrombossx());
            wolf.setDistancefrombossy(wolfRender.getDistancefrombossy());
            list.add(wolf);
        }
        animalData.setStorageWolfRenders(list);
        for(AnimalData.Wolf wolf: animalData.getWolfRenders()){
            System.out.println(wolf.getHealth());
        }

        List<AnimalData.Animal> list1=new ArrayList<>();
        for(PigReander pigReander: PigManager.getPigmanager().getPigManager()){
            AnimalData.Animal pig=new AnimalData.Animal();
            pig.setPosition(pigReander.location());
            pig.setHungry(pigReander.gethungry());
            pig.setHp(pigReander.getHeath().getCurrHp());
            list1.add(pig);
        }
        animalData.setPig(list1);
        if (!list1.isEmpty()){
            list1.clear();
        }
        for(ChickenRender chickenRender: ChickenManager.getChickenmanager().getChickenManager()){
            AnimalData.Animal chicken =new AnimalData.Animal();
            chicken.setPosition(chickenRender.location());
            chicken.setHungry(chickenRender.gethungry());
            chicken.setHp(chickenRender.getHeath().getCurrHp());
            list1.add(chicken);
        }
        animalData.setChicken(list1);
        if (!list1.isEmpty()){
            list1.clear();
        }
        for(Buffalo buffalo: BuffaloManager.getbuffalomanager().getBuffaloManager()){
            AnimalData.Animal wolf =new AnimalData.Animal();
            wolf.setPosition(buffalo.location());
            wolf.setHungry(buffalo.gethungry());
            wolf.setHp(buffalo.getmau().getCurrHp());
            list1.add(wolf);
        }
        animalData.setBuffalo(list1);
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
