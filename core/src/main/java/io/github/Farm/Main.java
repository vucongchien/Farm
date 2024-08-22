package io.github.Farm;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
public class Main extends ApplicationAdapter {
    private SpriteBatch batch;
    private PlayerController player;
    private PlayerRenderer playerRenderer;


    @Override
    public void create() {
        player=new PlayerController(new Vector2(100,200),100,100);
        playerRenderer = new PlayerRenderer(player, "menu.png");
        batch=new SpriteBatch();
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
