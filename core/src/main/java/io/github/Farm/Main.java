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
import io.github.Farm.Renderer.GameRenderer;
import io.github.Farm.player.lam_lai_file.PlayerCotrollerr;
import io.github.Farm.player.lam_lai_file.PlayerRender;
import io.github.Farm.player.old.PlayerController;
import io.github.Farm.player.old.PlayerImageManager;
import io.github.Farm.player.old.PlayerRenderer;


public class Main extends ApplicationAdapter {

    private SpriteBatch batch;
    private World world;


    //-------------player
    private PlayerRenderer playerRenderer;
    private PlayerController playerController;
    private PlayerImageManager playerImageManager;

    private PlayerRender playerRenderNew;
    private PlayerCotrollerr playerCotrollerrNew;
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


        playerController = new PlayerController(new Vector2(1000, 1000), 150f, world, mapInteractionHandler, plantManager); // Use tile size
        playerImageManager = new PlayerImageManager();
        playerRenderer = new PlayerRenderer(playerController, playerImageManager, 64);

        playerCotrollerrNew = new PlayerCotrollerr(new Vector2(900, 900), 150f, world, mapInteractionHandler, plantManager);
        playerImageManagerNew = new PlayerImageManager();
        playerRenderNew = new PlayerRender(playerCotrollerrNew, playerImageManagerNew, 64);

        gameRenderer = new GameRenderer(playerRenderNew, plantManager, camera,map);
    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(playerCotrollerrNew.getPosition().x, playerCotrollerrNew.getPosition().y, 0);
        camera.update();

        mapRenderer.setView(camera);
        mapRenderer.render();

        world.step(1 / 60f, 6, 2);

        float deltaTime = Gdx.graphics.getDeltaTime();

        debugRenderer.render(world, camera.combined);
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        Rectangle playerBounds = new Rectangle(playerController.getPosition().x, playerController.getPosition().y, 16, 16);
        shapeRenderer.rect(playerBounds.x, playerBounds.y, playerBounds.width, playerBounds.height);
        Rectangle collider = playerCotrollerrNew.getCollider();
        shapeRenderer.rect(collider.x, collider.y, collider.width, collider.height);
        shapeRenderer.end();


        batch.setProjectionMatrix(camera.combined);
        plantManager.update(deltaTime);
//        batch.begin();
//        plantManager.render(batch,camera);
//        batch.end();

        playerController.update(deltaTime);
        playerRenderer.render(batch);

        playerCotrollerrNew.update(deltaTime);
        gameRenderer.render();
//        playerRenderNew.render(batch);
    }


    @Override
    public void dispose() {
        batch.dispose();
        playerRenderer.dispose();
        map.dispose();
    }
}

