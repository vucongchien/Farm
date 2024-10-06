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


    //------------------render
    GameRenderer gameRenderer;
    ShapeRenderer shapeRenderer;
    Box2DDebugRenderer debugRenderer;


//-----------------------------------inventory


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
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(playerControllerNew.getPosition().x, playerControllerNew.getPosition().y, 0);
        camera.update();
        if (mainMenu.isMenuActive()) {
            mainMenu.handleInput();
            ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
            batch.setProjectionMatrix(camera.combined);
            mainMenu.render(batch);
        } else {
            settingGame.handleInput();

        mapRenderer.setView(camera);
        mapRenderer.render();
            if (settingGame.isActive()) {
                ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
                map.render();

        world.step(1 / 60f, 6, 2);
                batch.setProjectionMatrix(camera.combined);
                batch.begin();
                playerRenderer.render(batch);
                // Tính toán vị trí balo dựa trên vị trí nhân vật
                backpackBounds.setPosition(player.getPosition().x - camera.viewportWidth / 2 + 10,
                    player.getPosition().y - camera.viewportHeight / 2 + 10);
                batch.draw(backpackTexture, backpackBounds.x, backpackBounds.y);
                batch.end();

        float deltaTime = Gdx.graphics.getDeltaTime();
                settingGame.render(batch, player.getPosition());
            } else {
                float deltaTime = Gdx.graphics.getDeltaTime();
                player.update(deltaTime);
                player.plow(map);

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
        //............................buffalo
                batch.setProjectionMatrix(camera.combined);
                batch.begin();
                playerRenderer.render(batch);
                // Tính toán vị trí balo dựa trên vị trí nhân vật
                backpackBounds.setPosition(player.getPosition().x - camera.viewportWidth / 2 + 10,
                    player.getPosition().y - camera.viewportHeight / 2 + 10);
                batch.draw(backpackTexture, backpackBounds.x, backpackBounds.y);
                batch.end();

        if (TimeUtils.timeSinceMillis(stopTimereproduction) >= 30000) {
            if(arraybuffalo.size()<10) {
                arraybuffalo.add(new Buffalo(new Vector2(700, 1000), 100, 1, 1, true));
                stopTimereproduction = TimeUtils.millis();
            }
        }
        if (TimeUtils.timeSinceMillis(stopTimehungry) >= 10000) {
            for(Buffalo x:arraybuffalo){
                if(x.gethungry()>0) {
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
        for(int i=0;i<arraywolf.size();i++){
            arraywolf.get(i).hoatdong(arraywolf, arraybuffalo, batch, 32, Gdx.graphics.getDeltaTime(), camera);

                // Vẽ inventory nếu đang ở trạng thái 'isInGame'
                if (inputHandler.isInGame()) {
                    inventory.draw(batch, camera, player.getPosition());
                }
            }
        }
    }



    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
    }
}

