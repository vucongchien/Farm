package io.github.Farm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private PlayerController player;
    private PlayerRenderer playerRenderer;


    private OrthographicCamera camera;
    private Gamemap map;
    private MainMenu mainMenu; // Thêm biến MainMenu
    private SettingGame settingGame; // Thêm biến SettingGame
    private boolean isInGame;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Khởi tạo
        player = new PlayerController(new Vector2(100, 200), 200);
        playerRenderer = new PlayerRenderer(player,
            AssentPaths.Player_W_idle, AssentPaths.Player_S_idle, AssentPaths.Player_A_idle, AssentPaths.Player_D_idle,
            AssentPaths.Player_W_walk, AssentPaths.Player_S_walk, AssentPaths.Player_A_walk, AssentPaths.Player_D_walk,
            3, 1, 0.2f, 64);

        batch = new SpriteBatch();

        map = new Gamemap();
        map.create(AssentPaths.map);
        player.setMap(map);
        map.setCamera(camera);
        // Khởi tạo mainMenu và settingGame
        mainMenu = new MainMenu();
        settingGame = new SettingGame();
    }

    @Override
    public void render() {

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
    }
}
