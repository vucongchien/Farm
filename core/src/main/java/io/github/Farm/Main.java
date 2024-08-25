package io.github.Farm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private PlayerController player;
    private PlayerRenderer playerRenderer;

    @Override
    public void create() {
        // Khởi tạo
        player = new PlayerController(new Vector2(100, 200), 200, 64);
        playerRenderer = new PlayerRenderer(player,
            "W_idle.png", "S_idle.png", "A_idle.png", "D_idle.png",
            "W_walk.png", "S_walk.png", "A_walk.png", "D_walk.png",
            3, 1, 0.2f, 64);

        batch = new SpriteBatch();
    }

    @Override
    public void render() {
        float deltaTime = Gdx.graphics.getDeltaTime();
        player.update(deltaTime);

        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        playerRenderer.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        playerRenderer.dispose();
    }
}
