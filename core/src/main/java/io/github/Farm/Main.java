package io.github.Farm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
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
import io.github.Farm.Renderer.GameRenderer;
import io.github.Farm.player.PlayerController;
import io.github.Farm.player.PlayerRenderer;
import io.github.Farm.player.PlayerImageManager;
import io.github.Farm.animal.Buffalo;
import io.github.Farm.animal.wolf;
import io.github.Farm.ui.MainMenu;
import io.github.Farm.ui.SettingGame;
import io.github.Farm.inventory.Inventory;
import io.github.Farm.inventory.InputHandlerInventory;
import io.github.Farm.weather.Weather;
import com.badlogic.gdx.graphics.Texture; // Thêm dòng này để import lớp Texture
import java.util.ArrayList;


public class Main extends ApplicationAdapter {

    private SpriteBatch batch;
    private World world;

    private ArrayList<Buffalo> arraybuffalo=new ArrayList<>();
    private ArrayList<wolf> arraywolf= new ArrayList<>();
    private long stopTimereproduction;
    private long stopTimehungry;
    //-------------player

    private PlayerRenderer playerRendererNew;
    private PlayerController playerControllerNew;
    private PlayerImageManager playerImageManagerNew;

    //-------------plant
    private PlantManager plantManager;

    //-------------map
    private OrthogonalTiledMapRenderer mapRenderer;
    private TiledMap map;
    private MapManager mapManager;
    private MapInteractionHandler mapInteractionHandler;


    private OrthographicCamera camera;
    // Biến cho menu chính, cài đặt và inventory
    private MainMenu mainMenu; // Khai báo mainMenu
    private SettingGame settingGame; // Khai báo settingGame
    private boolean isInGame; // Khai báo biến isInGame
    //------------------render
    GameRenderer gameRenderer;
    ShapeRenderer shapeRenderer;
    Box2DDebugRenderer debugRenderer;

    private Rectangle backpackBounds; // Khai báo backpackBounds
    private Texture backpackTexture; // Khai báo biến lưu trữ hình ảnh ba lô
//-----------------------------------inventory
    private InputHandlerInventory inputHandler;
    private Inventory inventory; // Khai báo biến inventory
//____weather
    private Weather weather; // Khai báo Weather

    @Override
    public void create() {
        //..............................khoi tao buffalo
        arraywolf.add(new wolf(new Vector2(850,1050),100,true));
        arraywolf.add(new wolf(new Vector2(850,1040),100,false));
        arraybuffalo.add(new Buffalo(new Vector2(650,1000),100,1,1,true));
        arraybuffalo.add(new Buffalo(new Vector2(700,1000),100,1,1,true));
        arraybuffalo.add(new Buffalo(new Vector2(750,1000),100,1,1,true));
        stopTimereproduction = TimeUtils.millis();
        stopTimehungry = TimeUtils.millis();

        batch = new SpriteBatch();
        camera = new OrthographicCamera();
        world = new World(new Vector2(0, 0), false);
        Gdx.graphics.setWindowedMode(1920, 1080);

        map = new TmxMapLoader().load("Map_chien_lam/Map.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        mapManager = new MapManager(map);
        mapInteractionHandler = new MapInteractionHandler(mapManager);

        shapeRenderer = new ShapeRenderer();
        debugRenderer = new Box2DDebugRenderer();
        TiledObject.parseTiledObject(world, map.getLayers().get("aduvip").getObjects());

        camera.setToOrtho(false, 800, 450);


        plantManager = new PlantManager();
        //  plantManager.addPlant(new PlantRenderer(new Vector2(200, 100), PlantType.carrot));



        playerControllerNew = new PlayerController(new Vector2(900, 900), 150f, world, mapInteractionHandler, plantManager);
        playerImageManagerNew = new PlayerImageManager();
        playerRendererNew = new PlayerRenderer(playerControllerNew, playerImageManagerNew, 64);

        gameRenderer = new GameRenderer(playerRendererNew, plantManager, camera,map);
        // Khởi tạo mainMenu và settingGame
        mainMenu = new MainMenu();
        settingGame = new SettingGame();
        // Khởi tạo Inventory
        inventory = new Inventory();
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
        // weather
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
                plantManager.update(deltaTime);

                playerControllerNew.update(deltaTime);
                gameRenderer.render();

                // Kiểm tra và cập nhật buffalo
                if (TimeUtils.timeSinceMillis(stopTimereproduction) >= 30000) {
                    if (arraybuffalo.size() < 10) {
                        arraybuffalo.add(new Buffalo(new Vector2(700, 1000), 100, 1, 1, true));
                        stopTimereproduction = TimeUtils.millis();
                    }
                }
                if (TimeUtils.timeSinceMillis(stopTimehungry) >= 10000) {
                    for (Buffalo x : arraybuffalo) {
                        if (x.gethungry() > 0) {
                            x.sethungry(10);
                        }
                    }
                    stopTimehungry = TimeUtils.millis();
                }
                for (int i = arraybuffalo.size() - 1; i >= 0; i--) {
                    Buffalo x = arraybuffalo.get(i);
                    for (int j = 0; j < arraybuffalo.size(); j++) {
                        if (i != j) {
                            Buffalo y = arraybuffalo.get(j);
                            x.dam(y);
                        }
                    }
                    if (x.getmau() == 0) {
                        arraybuffalo.remove(i);
                        continue;
                    }
                    x.ve(batch, 32, Gdx.graphics.getDeltaTime(), camera);
                }
                for (int i = 0; i < arraywolf.size(); i++) {
                    arraywolf.get(i).hoatdong(arraywolf, arraybuffalo, batch, 32, Gdx.graphics.getDeltaTime(), camera);
                }

                batch.begin();
                playerRendererNew.render(batch);
                batch.setColor(Color.WHITE);
                // Tính toán vị trí balo dựa trên vị trí nhân vật
                backpackBounds.setPosition(playerControllerNew.getPosition().x - camera.viewportWidth / 2 + 10,
                    playerControllerNew.getPosition().y - camera.viewportHeight / 2 + 10);
                batch.draw(backpackTexture, backpackBounds.x, backpackBounds.y);
                batch.end();

                // Vẽ inventory nếu đang ở trạng thái 'isInGame'
                if (inputHandler.isInGame()) {
                    batch.setColor(Color.WHITE);
                    inventory.draw(batch, camera, playerControllerNew.getPosition());
                }
            }
        }
    }

    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
        inventory.dispose(); // Giải phóng tài nguyên của inventory
        // Giải phóng các texture
        backpackTexture.dispose(); // Giải phóng texture của ba lô
        // Giải phóng các đối tượng khác
        shapeRenderer.dispose();
        debugRenderer.dispose();

    }
}
