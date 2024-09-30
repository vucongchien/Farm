package io.github.Farm.Renderer;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface RenderableEntity {
    float getY();
    void render(SpriteBatch batch);
}
