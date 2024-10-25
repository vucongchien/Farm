package io.github.Farm.Trees;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public enum TreeType {
    mushroom("mushroom"),
    tree("wood");
    private final String typeMaterial;
    TreeType(String typeMaterial){
        this.typeMaterial=typeMaterial;
    }

    public String getTypeMaterial() {
        return typeMaterial;
    }
}
