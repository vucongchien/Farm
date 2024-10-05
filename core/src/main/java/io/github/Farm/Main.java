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
import com.badlogic.gdx.math.Vector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.World;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle; // Thêm dòng này để nhập khẩu Rectangle
import io.github.Farm.InputHandlerInventory;

import com.badlogic.gdx.utils.TimeUtils;
import io.github.Farm.Map.MapInteractionHandler;
import io.github.Farm.Map.MapManager;
import io.github.Farm.Map.TiledObject;
import io.github.Farm.Plants.PlantManager;
import io.github.Farm.Renderer.GameRenderer;
import io.github.Farm.animal.Buffalo;
import io.github.Farm.animal.wolf;
import io.github.Farm.player.PlayerController;
import io.github.Farm.player.PlayerRenderer;
import io.github.Farm.player.PlayerImageManager;
import io.github.Farm.player.PlayerRenderer;
import io.github.Farm.player.hoatdong;

import java.util.ArrayList;

import static io.github.Farm.animal.PetState.IDLE_RIGHT;
import static io.github.Farm.animal.PetState.WALK_LEFT;



public class Main extends ApplicationAdapter {

    private SpriteBatch batch;
    private World world;


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


    //------------------render
    GameRenderer gameRenderer;
    ShapeRenderer shapeRenderer;
    Box2DDebugRenderer debugRenderer;


//-----------------------------------inventory


//............................................buffalo
    private ArrayList<Buffalo> arraybuffalo=new ArrayList<>();
    private ArrayList<wolf> arraywolf= new ArrayList<>();
    private long stopTimereproduction;
    private long stopTimehungry;
    @Override
    public void create() {

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


//..............................khoi tao buffalo
        arraywolf.add(new wolf(new Vector2(850,850),100,true));
        arraywolf.add(new wolf(new Vector2(850,750),100,false));
        arraybuffalo.add(new Buffalo(new Vector2(650,500),100,1,1,true));
        arraybuffalo.add(new Buffalo(new Vector2(650,600),100,1,1,true));
        arraybuffalo.add(new Buffalo(new Vector2(650,550),100,1,1,true));
        stopTimereproduction = TimeUtils.millis();
        stopTimehungry = TimeUtils.millis();

    }


    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(com.badlogic.gdx.Input.Keys.I)) { // Phím 'I' để mở inventory
            isInGame = !isInGame; // Chuyển đổi trạng thái trò chơi
        }
    }
    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(playerControllerNew.getPosition().x, playerControllerNew.getPosition().y, 0);
        camera.update();

        mapRenderer.setView(camera);
        mapRenderer.render();

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
//        batch.begin();
//        plantManager.render(batch,camera);
//        batch.end();

        playerControllerNew.update(deltaTime);
        gameRenderer.render();
//        playerRenderNew.render(batch);




        handleInput();

//............................buffalo

        if (TimeUtils.timeSinceMillis(stopTimereproduction) >= 30000) {
            if(arraybuffalo.size()<10) {
                arraybuffalo.add(new Buffalo(new Vector2(700, 550), 100, 1, 1, true));
                stopTimereproduction = TimeUtils.millis();
            }
        }
        if (TimeUtils.timeSinceMillis(stopTimehungry) >= 5000) {
            for(Buffalo x:arraybuffalo){
                if(x.gethungry()>0) {
                    x.sethungry(10);
                }
            }
            stopTimehungry = TimeUtils.millis();
        }





        camera.setToOrtho(false,500,282);
        if (mainMenu.isMenuActive()) {
            mainMenu.handleInput();
            ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
            batch.setProjectionMatrix(camera.combined);
            mainMenu.render(batch); // Vẽ menu
        } else {

            settingGame.handleInput();

            if (settingGame.isActive()) {
                ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
                map.render();
                batch.setProjectionMatrix(camera.combined);
                batch.begin();
                playerRenderer.render(batch);
//..................cho buffalo

                for (int i = 0; i < arraybuffalo.size(); i++) {
                    Buffalo x = arraybuffalo.get(i);
                    for (int j =0; j < arraybuffalo.size(); j++) {
                        if(i!=j) {
                            Buffalo y = arraybuffalo.get(j);
                            x.dam(y);
                        }
                    }
                    if(arraybuffalo.get(i).getmau()==0){
                        arraybuffalo.remove(i);
                    }
                    x.ve(batch, 32, Gdx.graphics.getDeltaTime(),camera);
                    arraywolf.get(i).hoatdong(arraywolf,arraybuffalo,batch,32,Gdx.graphics.getDeltaTime(),camera);

                }

                batch.end();


                // Vẽ inventory nếu đang ở trạng thái 'isInGame'
                if (inputHandler.isInGame()) {
                    inventory.draw(batch, camera, player.getPosition());
                }
                // Vẽ hình ảnh balo
                batch.begin();
                batch.draw(backpackTexture, backpackBounds.x, backpackBounds.y);
                batch.end();

                settingGame.render(batch);
            } else {
                float deltaTime = Gdx.graphics.getDeltaTime();
                player.update(deltaTime);
                camera.position.set(player.getPosition().x, player.getPosition().y, 0);
                camera.update();
                ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
                map.render();
                batch.setProjectionMatrix(camera.combined);
//......................cho buffalo
                for (int i = 0; i < arraybuffalo.size(); i++) {
                    Buffalo x = arraybuffalo.get(i);
                    for (int j = 0; j < arraybuffalo.size(); j++) {
                        if(i!=j) {
                            Buffalo y = arraybuffalo.get(j);
                            x.dam(y);
                        }
                    }
                    x.ve(batch, 32, Gdx.graphics.getDeltaTime(),camera);
                    if(arraybuffalo.get(i).getmau()==0){
                        arraybuffalo.remove(i);
                    }
                }
                for(int i=0;i<arraywolf.size();i++){
                    arraywolf.get(i).hoatdong(arraywolf,arraybuffalo,batch,37.67f,Gdx.graphics.getDeltaTime(),camera);
                }

//--------------------------cho player

                playerRenderer.render(batch);
                selecbox=new Selecbox(player.getPosition(),batch,map);
                if(player.isPlowing()) {
                    timeCoolDownBar = new TimeCoolDown(player.getPosition(), player.getStartPlow(), player.getTimeToPlow(), batch, map);
                }

            }
        }
    }


    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();

        mainMenu.dispose(); // Gọi phương thức dispose của MainMenu
        settingGame.dispose(); // Gọi phương thức dispose của SettingGame
        inventory.dispose();
        backpackTexture.dispose();

    }
}

