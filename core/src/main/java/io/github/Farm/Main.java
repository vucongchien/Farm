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
import com.badlogic.gdx.graphics.Texture; // Thêm dòng này để import lớp Texture

import java.util.ArrayList;


public class Main extends ApplicationAdapter {

    private SpriteBatch batch;
    private World world;
    private OrthographicCamera camera;

    //-------------player

    private PlayerRenderer playerRendererNew;
    private PlayerController playerControllerNew;
    private PlayerImageManager playerImageManagerNew;


    //-------------map
    private OrthogonalTiledMapRenderer mapRenderer;
    private TiledMap map;
    private MapManager mapManager;
    private MapInteractionHandler mapInteractionHandler;




    //------------------render
    GameRenderer gameRenderer;
    ShapeRenderer shapeRenderer;
    Box2DDebugRenderer debugRenderer;

    private Rectangle backpackBounds;
    private Texture backpackTexture;


//-----------------------------------inventory


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
        playerImageManagerNew = new PlayerImageManager();
        playerRendererNew = new PlayerRenderer(playerControllerNew, playerImageManagerNew, 64);

        gameRenderer = new GameRenderer(playerRendererNew, camera,map);


        backpackTexture = new Texture(Gdx.files.internal("inventory/balo.png"));
        backpackBounds = new Rectangle(10, 10, backpackTexture.getWidth(), backpackTexture.getHeight());



    }

    @Override
    public void render() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.position.set(playerControllerNew.getPosition().x, playerControllerNew.getPosition().y, 0);
        camera.update();

        mapRenderer.setView(camera);
        mapRenderer.render();

        world.step(1 / 120f, 6, 2);

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
            Inventory.getInstance().draw(batch, camera, playerControllerNew.getPosition());
        }
    }




    @Override
    public void dispose() {
        batch.dispose();
        map.dispose();
    }
}
