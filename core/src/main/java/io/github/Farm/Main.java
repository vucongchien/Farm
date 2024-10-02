package io.github.Farm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle; // Thêm dòng này để nhập khẩu Rectangle
import io.github.Farm.InputHandlerInventory;


public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private PlayerController player;
    private PlayerRenderer playerRenderer;


    private OrthographicCamera camera;
    private Gamemap map;
    private MainMenu mainMenu; // Thêm biến MainMenu
    private SettingGame settingGame; // Thêm biến SettingGame
    private boolean isInGame;
    private Inventory inventory; // Thêm biến Inventory
    private Texture backpackTexture;  // Texture cho balo
    private Rectangle backpackBounds;
    private InputHandlerInventory inputHandler;


    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Khởi tạo
        player = new PlayerController(new Vector2(100, 200), 200);
        playerRenderer = new PlayerRenderer(player,
            AssentPaths.Player_W_idle, AssentPaths.Player_S_idle, AssentPaths.Player_A_idle, AssentPaths.Player_D_idle,
            AssentPaths.Player_W_walk, AssentPaths.Player_S_walk, AssentPaths.Player_A_walk, AssentPaths.Player_D_walk,
            3, 1, 0.2f, 16);

        batch = new SpriteBatch();
        //lkajflkajf
        map = new Gamemap();
        map.create(AssentPaths.map);
        player.setMap(map);
        map.setCamera(camera);
        // Khởi tạo mainMenu và settingGame
        mainMenu = new MainMenu();
        settingGame = new SettingGame();
        // Khởi tạo Inventory
        inventory = new Inventory();
        // Thêm một số item mẫu vào inventory
        // Thêm một số item mẫu vào inventory
        inventory.addItem("Apple", new Texture(Gdx.files.internal("inventory/almonds.png")), 5);
        inventory.addItem("Carrot", new Texture(Gdx.files.internal("inventory/apple_green.png")), 3);
        inventory.addItem("asparagus", new Texture(Gdx.files.internal("inventory/asparagus.png")), 10);
        inventory.addItem("egg", new Texture(Gdx.files.internal("inventory/egg_whole_white.png")), 100);
        inventory.addItem("banana", new Texture(Gdx.files.internal("inventory/banana.png")), 100);
        inventory.addItem("corn", new Texture(Gdx.files.internal("inventory/corn.png")), 100);
        inventory.addItem("watermelon", new Texture(Gdx.files.internal("inventory/watermelon_whole.png")), 100);
        inventory.addItem("chili", new Texture(Gdx.files.internal("inventory/chili_pepper_green.png")), 100);


        backpackTexture = new Texture(Gdx.files.internal("inventory/balo.png"));
        backpackBounds = new Rectangle(10, 10, backpackTexture.getWidth(), backpackTexture.getHeight());

        // Khởi tạo InputHandler
        inputHandler = new InputHandlerInventory(camera, backpackBounds, inventory, isInGame);
        Gdx.input.setInputProcessor(inputHandler);
    }


    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.I)) { // Phím 'I' để mở inventory
            isInGame = !isInGame; // Chuyển đổi trạng thái trò chơi
        }
    }
    @Override
    public void render() {
        handleInput();

        if (mainMenu.isMenuActive()) {
            mainMenu.handleInput(); // Xử lý sự kiện đầu vào cho menu
            ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f); // Xóa màn hình với màu nền
            batch.setProjectionMatrix(camera.combined); // Thiết lập ma trận chiếu cho SpriteBatch
            mainMenu.render(batch); // Vẽ menu
        } else {
            // Xử lý đầu vào và render cho SettingGame
            settingGame.handleInput();

            if (settingGame.isActive()) {
                // Khi SettingGame đang hoạt động, tạm dừng game
                ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
                map.render();

                batch.setProjectionMatrix(camera.combined);
                batch.begin();
                playerRenderer.render(batch); // Vẫn vẽ nhân vật nhưng không cho phép điều khiển
                batch.end();

                settingGame.render(batch); // Vẽ bảng cài đặt lên trên game
            } else {
                // Khi SettingGame không hoạt động, game chạy bình thường
                float deltaTime = Gdx.graphics.getDeltaTime();
                player.update(deltaTime);
                player.plow(map);

                camera.position.set(player.getPosition().x, player.getPosition().y, 0);
                camera.update();

                ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
                map.render();

                batch.setProjectionMatrix(camera.combined);
                batch.begin();
                playerRenderer.render(batch);
                batch.end();

                // Vẽ inventory nếu đang ở trạng thái 'isInGame'
                if (inputHandler.isInGame()) {
                    inventory.draw(batch, camera, player.getPosition());
                }
                // Vẽ hình ảnh balo
                batch.begin();
                batch.draw(backpackTexture, backpackBounds.x, backpackBounds.y);
                batch.end();
            }
        }
    }


    @Override
    public void dispose() {
        batch.dispose();
        playerRenderer.dispose();
        map.dispose();
        mainMenu.dispose(); // Gọi phương thức dispose của MainMenu
        settingGame.dispose(); // Gọi phương thức dispose của SettingGame
        inventory.dispose();
        backpackTexture.dispose();
    }
}
