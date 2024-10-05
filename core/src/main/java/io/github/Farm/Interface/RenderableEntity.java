package io.github.Farm.Interface;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface RenderableEntity {
    float getY();
    void render(SpriteBatch batch);
}
