package io.github.Farm.ui.Other;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum Expression {
    NULL(null),
    ALERTED("UI/other/expression_alerted.png"),
    ATTACK("UI/other/expression_attack.png"),
    CHAT("UI/other/expression_chat.png"),
    CONFUSED("UI/other/expression_confused.png"),
    CONFUSED_ALT("UI/other/expression_confused-1.png"),
    LOVE("UI/other/expression_love.png"),
    STRESS("UI/other/expression_stress.png"),
    WORKING("UI/other/expression_working.png"),
    NEEDWATER("UI/other/water.png");

    private TextureRegion textureRegion;

    Expression(String texturePath) {
        if (texturePath != null) {
            this.textureRegion = new TextureRegion(new Texture(texturePath));
        }
    }

    public TextureRegion getTextureRegion() {
        return textureRegion;
    }
}
