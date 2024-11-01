package io.github.Farm.ui.Other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.math.Vector2;

public class SelectionBox implements Disposable {
    private TextureRegion[] selectionBoxTextures;
    private SpriteBatch batch;

    private float dropPositionY = 0f;
    private float timeElapsed = 0f;
    private float scaleFactor = 1f;
    private boolean isShrinking = true;

    public SelectionBox() {
        selectionBoxTextures = new TextureRegion[4];
        selectionBoxTextures[0] = new TextureRegion(new Texture("UI/other/selectbox_tl.png"));
        selectionBoxTextures[1] = new TextureRegion(new Texture("UI/other/selectbox_tr.png"));
        selectionBoxTextures[2] = new TextureRegion(new Texture("UI/other/selectbox_bl.png"));
        selectionBoxTextures[3] = new TextureRegion(new Texture("UI/other/selectbox_br.png"));
        batch = new SpriteBatch();
    }

    public void render(Vector2 position, float width, float height, Camera camera) {
        batch.setProjectionMatrix(camera.combined);
        float deltaTime = Gdx.graphics.getDeltaTime();
        if (isShrinking) {
            scaleFactor -= 1 * deltaTime;
            if (scaleFactor <= 0.9f) {
                scaleFactor = 0.9f;
                isShrinking = false;
            }
        } else {
            scaleFactor += 1 * deltaTime;
            if (scaleFactor >= 1f) {
                scaleFactor = 1f;
                isShrinking = true;
            }
        }

        float scaledWidth = width * scaleFactor;
        float scaledHeight = height * scaleFactor;

        batch.begin();
        batch.draw(selectionBoxTextures[0], position.x - scaledWidth / 3, position.y + scaledHeight, scaledWidth / 2, scaledHeight / 2);
        batch.draw(selectionBoxTextures[1], position.x + scaledWidth , position.y + scaledHeight, scaledWidth / 2, scaledHeight / 2);
        batch.draw(selectionBoxTextures[2], position.x - scaledWidth / 3, position.y - scaledHeight / 2, scaledWidth / 2, scaledHeight / 2);
        batch.draw(selectionBoxTextures[3], position.x + scaledWidth , position.y - scaledHeight / 2, scaledWidth / 2, scaledHeight / 2);
        batch.end();
    }

    @Override
    public void dispose() {
        for (TextureRegion region : selectionBoxTextures) {
            if (region.getTexture() != null) {
                region.getTexture().dispose();
            }
        }
        batch.dispose();
    }
}
