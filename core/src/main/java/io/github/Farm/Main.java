package io.github.Farm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import io.github.Farm.player.PlayerController;
import io.github.Farm.player.PlayerImageManager;
import io.github.Farm.player.PlayerRenderer;
import io.github.Farm.player.hoatdong;


public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private hoatdong player;
    private PlayerRenderer playerRenderer;
    private PlayerImageManager playerImageManager;


    private OrthographicCamera camera;
    private Gamemap map;
    private MainMenu mainMenu; // Thêm biến MainMenu
    private SettingGame settingGame; // Thêm biến SettingGame
    private boolean isInGame;

    @Override
    public void create() {
        Gdx.graphics.setWindowedMode(1920, 1080);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //khoi tao map
        map = new Gamemap();
        map.create(AssentPaths.map);
        // Khởi tạo player
        player = new hoatdong(new Vector2(100, 200), 200,map);
        playerImageManager=new PlayerImageManager();
        playerRenderer = new PlayerRenderer(player, playerImageManager,  64);

        batch = new SpriteBatch();


        map.setCamera(camera);
        mainMenu = new MainMenu();
        settingGame = new SettingGame();
    }

    @Override
    public void render() {

        camera.setToOrtho(false,500,282);
        if (mainMenu.isMenuActive()) {
            mainMenu.handleInput();
            ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
            batch.setProjectionMatrix(camera.combined);
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
                settingGame.render(batch);
            } else {
                // Khi SettingGame không hoạt động, game chạy bình thường
                float deltaTime = Gdx.graphics.getDeltaTime();
                player.update(deltaTime);

                camera.position.set(player.getPosition().x, player.getPosition().y, 0);
                camera.update();

                ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
                map.render();

                batch.setProjectionMatrix(camera.combined);
                batch.begin();

                playerRenderer.render(batch);

                batch.end();
                player.laycitri(batch,map);
            }
        }
    }


    @Override
    public void dispose() {
        batch.dispose();
        playerRenderer.dispose();
        map.dispose();
        mainMenu.dispose();
        settingGame.dispose();
    }
}
