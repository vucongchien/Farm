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
import io.github.Farm.Map.MapInteractionHandler;
import io.github.Farm.Map.MapManager;
import io.github.Farm.Map.TiledObject;
import io.github.Farm.Plants.PlantManager;
import io.github.Farm.Renderer.GameRenderer;
import io.github.Farm.data.*;
import io.github.Farm.animal.Buffalo.BuffaloImageManager;
import io.github.Farm.animal.Buffalo.BuffaloManager;
import io.github.Farm.animal.WolfManager;
import io.github.Farm.player.PlayerController;
import io.github.Farm.player.PlayerRenderer;
import io.github.Farm.player.PlayerImageManager;
import io.github.Farm.inventory.Inventory;
import io.github.Farm.animal.Buffalo.BuffaloManager;
import io.github.Farm.animal.WolfManager;
import io.github.Farm.ui.MainMenu;
import io.github.Farm.ui.SettingGame;
import io.github.Farm.weather.Weather;

import java.util.List;


public class Main extends ApplicationAdapter {

    private SpriteBatch batch;
    private World world;
    private GameSaveManager saveManager;
    private GameData gameData;

    //    private ArrayList<Buffalo> arraybuffalo=new ArrayList<>();
//    private ArrayList<WolfManager> arraywolf= new ArrayList<>();
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
    private GameRenderer gameRenderer;
    private ShapeRenderer shapeRenderer;
    private Box2DDebugRenderer debugRenderer;


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


        GameSaveManager saveManager = new GameSaveManager();

//        PlayerData playerData = new PlayerData(800, 900, 100);
//        saveManager.savePlayerData(playerData);
//
//        List<PlantData> plants = new ArrayList<>();
//        plants.add(new PlantData("POTATO", 1, new Vector2(5, 5)));
//        saveManager.savePlantsData(plants);
//
//        InventoryData inventoryData = new InventoryData();
//        inventoryData.getItems().add(new InventoryData.Item("pumpkin", 1));
//        saveManager.saveInventoryData(inventoryData);

        PlayerData loadedPlayerData = saveManager.loadPlayerData();
        List<PlantData> loadedPlantsData = saveManager.loadPlantsData();
        InventoryData loadedInventoryData = saveManager.loadInventoryData();

        System.out.println(loadedPlayerData.getPosition());

        camera.setToOrtho(false, 800, 450);


        plantManager = new PlantManager();
        //  plantManager.addPlant(new PlantRenderer(new Vector2(200, 100), PlantType.carrot));


        playerControllerNew = new PlayerController(new Vector2(900, 900), world, mapInteractionHandler, camera);
        playerImageManagerNew = new PlayerImageManager();
        playerRendererNew = new PlayerRenderer(playerControllerNew, playerImageManagerNew, 64);

        gameRenderer = new GameRenderer(playerRendererNew, camera, map);
//        // Khởi tạo menu và setting
//        mainMenu = new MainMenu(); // Khởi tạo main menu
//        settingGame = new SettingGame(); // Khởi tạo setting menu
//        // Khởi tạo mainMenu và settingGame
//        mainMenu = new MainMenu();
//        settingGame = new SettingGame();
//        // Khởi tạo Inventory
//        inventory = new Inventory();
//        // Thêm một số item mẫu vào inventory
//        // Thêm một số item mẫu vào inventory

//        backpackTexture = new Texture(Gdx.files.internal("inventory/balo.png"));
//        backpackBounds = new Rectangle(10, 10, backpackTexture.getWidth(), backpackTexture.getHeight());

//        // Khởi tạo InputHandler
//        inputHandler = new InputHandlerInventory(camera, backpackBounds, inventory, isInGame);
//        Gdx.input.setInputProcessor(inputHandler);

    }

    @Override
    public void render() {
        BuffaloManager.getbuffalomanager().update();
        WolfManager.getwolfmanage().update(BuffaloManager.getbuffalomanager(),playerControllerNew);
        //gameRenderer.addanimal(buffaloManager,wolfManager);
        // Kiểm tra xem menu có đang hoạt động không
//        if (mainMenu.isMenuActive()) {
//            mainMenu.handleInput();
//            Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
//            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//            mainMenu.render(batch);
//        } else {
//            settingGame.handleInput();
//            // Nếu menu không hoạt động, kiểm tra xem setting có đang hoạt động không
//            if (settingGame.isActive()) {
//
//                Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
//                Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
//                mapRenderer.setView(camera);
//                mapRenderer.render();
//
//
//                settingGame.render(batch, playerControllerNew.getPosition());
//            } else {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(playerControllerNew.getPosition().x, playerControllerNew.getPosition().y, 0);
        camera.update();

        mapRenderer.setView(camera);
        mapRenderer.render();

//                weather.update(Gdx.graphics.getDeltaTime());
        batch.begin();
//                weather.render(batch);
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



                if (Inventory.getInstance().isOpened()) {
                    batch.setColor(Color.WHITE);
                    Inventory.getInstance().draw(batch, camera, playerControllerNew.getPosition());

//        batch.draw(backpackTexture, backpackBounds.x, backpackBounds.y);
//        batch.end();

//        // Vẽ inventory nếu đang ở trạng thái 'isInGame'
//        if (inputHandler.isInGame()) {
//            inventory.draw(batch, camera, playerControllerNew.getPosition());
//        }
}

}




    @Override
    public void dispose() {
            WolfManager.getwolfmanage().dispose();
//        batch.dispose();
//        map.dispose();
//        inventory.dispose(); // Giải phóng tài nguyên của inventory
    }
}
