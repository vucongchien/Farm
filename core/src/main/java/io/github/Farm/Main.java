package io.github.Farm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.TimeUtils;
import io.github.Farm.Map.MapInteractionHandler;
import io.github.Farm.Map.MapManager;
import io.github.Farm.Map.TiledObject;
import io.github.Farm.Plants.PlantManager;
import io.github.Farm.Plants.PlantType;
import io.github.Farm.Renderer.GameRenderer;
import io.github.Farm.player.PlayerController;
import io.github.Farm.player.PlayerRenderer;
import io.github.Farm.player.PlayerImageManager;
import io.github.Farm.animal.Buffalo;
import io.github.Farm.animal.wolf;
import io.github.Farm.inventory.Inventory;
import io.github.Farm.inventory.InputHandlerInventory;
import io.github.Farm.ui.MainMenu;
import io.github.Farm.ui.SettingGame;
import io.github.Farm.weather.Weather;
import com.badlogic.gdx.graphics.Texture; // Thêm dòng này để import lớp Texture
import java.util.ArrayList;


public class Main extends ApplicationAdapter {

    private SpriteBatch batch;
    private World world;
    private OrthographicCamera camera;

    //-------------player

    private PlayerRenderer playerRendererNew;
    private PlayerController playerControllerNew;


    //-------------map
    private OrthogonalTiledMapRenderer mapRenderer;
    private TiledMap map;
    private MapManager mapManager;
    private MapInteractionHandler mapInteractionHandler;



    private MainMenu mainMenu;
    private SettingGame settingGame;

    //------------------render
    GameRenderer gameRenderer;
    ShapeRenderer shapeRenderer;
    Box2DDebugRenderer debugRenderer;

    private Rectangle backpackBounds;
    private Texture backpackTexture;



//____weather
    private Weather weather; // Khai báo Weather

    @Override
    public void create() {

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 450);
        world = new World(new Vector2(0, 0), false);
        Gdx.graphics.setWindowedMode(1920, 1080);

        map = new TmxMapLoader().load("Map_chien_lam/Map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        mapManager = new MapManager(map);
        mapInteractionHandler = new MapInteractionHandler(mapManager);

        shapeRenderer = new ShapeRenderer();
        debugRenderer = new Box2DDebugRenderer();
        TiledObject.parseTiledObject(world, map.getLayers().get("aduvip").getObjects());


        playerControllerNew = new PlayerController(new Vector2(900, 900), world, mapInteractionHandler);
        PlayerImageManager playerImageManagerNew = new PlayerImageManager();
        playerRendererNew = new PlayerRenderer(playerControllerNew, playerImageManagerNew, 64);

        gameRenderer = new GameRenderer(playerRendererNew, camera,map);
        // Khởi tạo mainMenu và settingGame
        mainMenu = new MainMenu();
        settingGame = new SettingGame();


        backpackTexture = new Texture(Gdx.files.internal("inventory/balo.png"));
        backpackBounds = new Rectangle(10, 10, backpackTexture.getWidth(), backpackTexture.getHeight());


        weather = new Weather(); // Khởi tạo Weather

    }

    @Override
    public void render() {
        // Kiểm tra xem menu có đang hoạt động không
        if (mainMenu.isMenuActive()) {
            mainMenu.handleInput();
            Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            mainMenu.render(batch);
        } else {
            settingGame.handleInput();
            // Nếu menu không hoạt động, kiểm tra xem setting có đang hoạt động không
            if (settingGame.isActive()) {
                batch.setColor(Color.WHITE);
                Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
                mapRenderer.setView(camera);
                mapRenderer.render();
                settingGame.render(batch, playerControllerNew.getPosition());
            } else {

                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

                camera.position.set(playerControllerNew.getPosition().x, playerControllerNew.getPosition().y, 0);
                camera.update();

                mapRenderer.setView(camera);
                mapRenderer.render();

                // Cập nhật và vẽ thời tiết
                weather.update(Gdx.graphics.getDeltaTime());

                batch.begin();
                weather.render(batch); // Vẽ thời tiết lên bản đồ
                batch.end();

                world.step(1 / 60f, 6, 2);

                float deltaTime = Gdx.graphics.getDeltaTime();

                debugRenderer.render(world, camera.combined);
                shapeRenderer.setProjectionMatrix(camera.combined);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(Color.RED);
                Rectangle collider = playerControllerNew.getCollider();
                shapeRenderer.rect(collider.x, collider.y, collider.width, collider.height);
                shapeRenderer.end();

                batch.setProjectionMatrix(camera.combined);
                PlantManager.getInstance().update(deltaTime);

                playerControllerNew.update(deltaTime);
                gameRenderer.render();


                batch.begin();
                batch.setColor(Color.WHITE);
                backpackBounds.setPosition(playerControllerNew.getPosition().x - camera.viewportWidth / 2 + 10,
                    playerControllerNew.getPosition().y - camera.viewportHeight / 2 + 10);
                batch.draw(backpackTexture, backpackBounds.x, backpackBounds.y);
                batch.end();

                if (Inventory.getInstance().isOpened()) {
                    batch.setColor(Color.WHITE);
                    Inventory.getInstance().draw(batch, camera, playerControllerNew.getPosition());
                }
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
        Inventory.getInstance().dispose();
        backpackTexture.dispose();
        shapeRenderer.dispose();
        debugRenderer.dispose();

    }
}
