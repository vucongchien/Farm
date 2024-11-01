package io.github.Farm.ui.Other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class GreenBar implements Disposable {
    private SpriteBatch batch;
    private TextureRegion[] greenBar;
    private Texture[] textures;

    public GreenBar() {
        batch = new SpriteBatch();
        textures = new Texture[7];
        greenBar = new TextureRegion[7];
        for (int i = 0; i < greenBar.length; i++) {
            textures[i] = new Texture("UI/other/greenbar_0" + i + ".png");
            greenBar[i] = new TextureRegion(textures[i]);
        }
    }

    private int getIndex(float time, float timeMax) {
        if (timeMax <= 0) return greenBar.length - 1;

        float tmp = timeMax / 6;
        int index = (int) (time / tmp);
        return Math.max(0, Math.min(index, greenBar.length - 1));
    }

    public void render(Vector2 position, Camera camera, float time, float timeMax, float width, float height) {
        int index = getIndex(time, timeMax);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(greenBar[index], position.x-height, position.y+width, width, height);
        batch.end();
    }

    @Override
    public void dispose() {
        for (Texture texture : textures) {
            texture.dispose();
        }
        batch.dispose();
    }
}
