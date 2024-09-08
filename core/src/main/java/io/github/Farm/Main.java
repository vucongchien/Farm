package io.github.Farm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private hoatdong player;
    private PlayerRenderer playerRenderer;
//    private hoatdong con;

    private OrthographicCamera camera;
    private Gamemap map;

    private PlantRenderer potato;

    @Override
    public void create() {
        Gdx.graphics.setWindowedMode(1920, 1080);
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        player = new hoatdong(new Vector2(100, 200), 200);
        playerRenderer = new PlayerRenderer(player,
            AssentPaths.Player_W_idle, AssentPaths.Player_S_idle, AssentPaths.Player_A_idle, AssentPaths.Player_D_idle,
            AssentPaths.Player_W_walk, AssentPaths.Player_S_walk, AssentPaths.Player_A_walk, AssentPaths.Player_D_walk,
            3, 1, 0.2f, 16);

        batch = new SpriteBatch();

        map = new Gamemap();
        map.create(AssentPaths.map);
        player.setMap(map);
        map.setCamera(camera);

        // Khởi tạo cây Tomato với hình ảnh cho các giai đoạn phát triển
        potato = new PlantRenderer(new Vector2(100, 200), PlantType.POTATO);
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        player.update(deltaTime);
        camera.setToOrtho(false, 500, 282);
        potato.update(deltaTime);

        camera.position.set(player.getPosition().x, player.getPosition().y, 0);
        camera.update();
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);

//      map.velayerdau();
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.setColor(1f, 1f, 1f, 1f);
        potato.render(batch);
        playerRenderer.render(batch);
        map.render();
        batch.end();
        player.laycitri(batch,map);
        player.plow(map);
    }

    @Override
    public void dispose() {
        batch.dispose();
        playerRenderer.dispose();
        map.dispose();
    }
}
