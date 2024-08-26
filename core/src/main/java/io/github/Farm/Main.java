package io.github.Farm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private PlayerController player;
    private PlayerRenderer playerRenderer;

    private OrthographicCamera camera;
    private Map map;

    @Override
    public void create() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // Khởi tạo
        player = new PlayerController(new Vector2(100, 200), 200);
        playerRenderer = new PlayerRenderer(player,
            AssentPaths.Player_W_idle, AssentPaths.Player_S_idle, AssentPaths.Player_A_idle, AssentPaths.Player_D_idle,
            AssentPaths.Player_W_walk, AssentPaths.Player_S_walk, AssentPaths.Player_A_walk, AssentPaths.Player_D_walk,
            3, 1, 0.2f, 64);

        batch = new SpriteBatch();

        map = new Map();
        map.create(AssentPaths.map);
        map.setCamera(camera);
    }

    @Override
    public void render() {
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
    }

    @Override
    public void dispose() {
        batch.dispose();
        playerRenderer.dispose();
        map.dispose();
    }
}
