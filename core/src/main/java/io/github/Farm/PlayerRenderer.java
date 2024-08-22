package io.github.Farm;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class PlayerRenderer {
    private Texture texture;
    private PlayerController player;

    public PlayerRenderer(PlayerController player, String texturePath) {
        this.player = player;
        this.texture = new Texture(texturePath); // Tải texture từ đường dẫn
    }

    public void render(SpriteBatch batch) {
        Vector2 position = player.getPosition();
        float size = player.getSize();
        batch.draw(texture, position.x, position.y, size, size);
    }

    public void dispose() {
        texture.dispose();
    }
}
