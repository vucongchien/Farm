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
import io.github.Farm.Map.MapInteractionHandler;
import io.github.Farm.Map.MapManager;
import io.github.Farm.Map.TiledObject;
import io.github.Farm.Plants.PlantManager;
import io.github.Farm.Plants.PlantType;
import io.github.Farm.Renderer.GameRenderer;
import io.github.Farm.Trees.TreeManager;
import io.github.Farm.Trees.TreeType;
import io.github.Farm.animal.Buffalo.BuffaloManager;
import io.github.Farm.animal.Chicken.ChickenManager;
import io.github.Farm.animal.PetManager;
import io.github.Farm.animal.Pig.PigManager;
import io.github.Farm.animal.WOLF.WolfManager;
import io.github.Farm.data.*;
import io.github.Farm.player.PlayerController;
import io.github.Farm.player.PlayerRenderer;
import io.github.Farm.player.PlayerImageManager;
import io.github.Farm.inventory.Inventory;
import io.github.Farm.ui.MainMenu;
import io.github.Farm.ui.Other.SelectionBox;
import io.github.Farm.ui.SettingGame;
import io.github.Farm.weather.Weather;


public class Main extends ApplicationAdapter {

    private SpriteBatch batch;
    private World world;
    private OrthographicCamera camera;

    //-------------player

    private PlayerRenderer playerRendererNew;
    private PlayerController playerControllerNew =null;
    private PlayerImageManager playerImageManagerNew;



    //-------------map
    private OrthogonalTiledMapRenderer mapRenderer;
    private TiledMap map;
    private MapManager mapManager;
    private MapInteractionHandler mapInteractionHandler;



    private MainMenu mainMenu;
    private SettingGame settingGame;
    private GameData gameData;

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


        gameData = new GameData();
        gameData.setPlayer(GameSaveManager.getInstance().loadPlayerData());
        gameData.setPlants(GameSaveManager.getInstance().loadPlantsData());
        gameData.setInventory(GameSaveManager.getInstance().loadInventoryData());
        gameData.setAnimal(GameSaveManager.getInstance().loadAnimalData());

        TreeManager.getInstance().addTree(world, TreeType.tree,new Vector2(2300,1500));


        SelectionBox.setCamera(camera);
        gameRenderer = new GameRenderer(null, camera,map);
        mainMenu = new MainMenu(map);
        settingGame = new SettingGame(gameData,null,map);
    }

    @Override
    public void render() {
        if (mainMenu.isMenuActive()) {
            mainMenu.handleInput();
            Gdx.gl.glClearColor(0.15f, 0.15f, 0.2f, 1f);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            mainMenu.render(batch);
        } else {
            if(playerControllerNew==null){
                playerControllerNew = new PlayerController(new Vector2(900, 900), world, mapInteractionHandler,camera);
                playerImageManagerNew = new PlayerImageManager();
                playerRendererNew = new PlayerRenderer(playerControllerNew, playerImageManagerNew, 64);
                gameRenderer = new GameRenderer(playerRendererNew, camera,map);
                settingGame = new SettingGame(gameData,playerControllerNew,map);
                System.out.println("1");
            }
            settingGame.handleInput();
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

                Weather.getInstance().update(Gdx.graphics.getDeltaTime());
                batch.begin();
                Weather.getInstance().render(batch);
                batch.end();

                world.step(1 / 60f, 6, 2);

                float deltaTime = Gdx.graphics.getDeltaTime();


                debugRenderer.render(world, camera.combined);
                shapeRenderer.setProjectionMatrix(camera.combined);
                shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
                shapeRenderer.setColor(Color.RED);
                Rectangle collider = playerControllerNew.getCollider();
                shapeRenderer.rect(collider.x, collider.y, collider.width, collider.height);
                if(TreeManager.getInstance().getTrees().get(0)!=null) {
                    Rectangle a = TreeManager.getInstance().getTrees().get(0).getRectangle();
                    shapeRenderer.rect(a.x, a.y, a.width, a.height);
                }
                shapeRenderer.end();

                batch.setProjectionMatrix(camera.combined);
                PlantManager.getInstance().update(deltaTime);
                BuffaloManager.getbuffalomanager().update(playerControllerNew);
                PigManager.getPigmanager().update(playerControllerNew);
                ChickenManager.getChickenmanager().update(playerControllerNew);
                PetManager.getPetmanager().update(BuffaloManager.getbuffalomanager(),ChickenManager.getChickenmanager(),PigManager.getPigmanager());
                WolfManager.getwolfmanage().update(PetManager.getPetmanager(),playerControllerNew);
                playerControllerNew.update(deltaTime);


                gameRenderer.render();

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
        shapeRenderer.dispose();
        debugRenderer.dispose();

    }
}
