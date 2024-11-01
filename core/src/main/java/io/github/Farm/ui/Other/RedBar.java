package io.github.Farm.ui.Other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class RedBar implements Disposable {
    private SpriteBatch batch;
    private TextureRegion[] redBar;
    private Texture[] textures;

    public RedBar() {
        batch = new SpriteBatch();
        textures = new Texture[7];
        redBar = new TextureRegion[7];
        for (int i = 0; i < redBar.length; i++) {
            textures[i] = new Texture(Gdx.files.internal("UI/other/redbar_0" + i + ".png"));
            redBar[i] = new TextureRegion(textures[i]);
        }
    }

    private int getIndex(float currHp, float maxHp) {
        if (maxHp <= 0) return redBar.length - 1;
        float tmp = maxHp / 6;
        int index = (int) (currHp / tmp);
        return Math.max(0, Math.min(index, redBar.length - 1));
    }

    public  void renderGoblin(Vector2 position,SpriteBatch batch,float currHp,float maxHp, float width, float height){
        int index=getIndex(currHp,maxHp);
        batch.begin();
        batch.draw(redBar[index],position.x-height,position.y+width,width, height);
        batch.end();
    }

    public void render(Vector2 position, Camera camera, float currHp, float maxHp, float width, float height) {
        int index = getIndex(currHp, maxHp);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(redBar[index], position.x, position.y, width, height);
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
