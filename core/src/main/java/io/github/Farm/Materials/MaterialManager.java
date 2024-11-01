package io.github.Farm.Materials;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.List;

public class MaterialManager {

    private static MaterialManager instance;
    private final MaterialImageManager materialImageManager;
    private final List<MaterialsRender> trees;

    private MaterialManager() {
        this.materialImageManager = new MaterialImageManager();
        this.trees = new ArrayList<>();
    }

    public static MaterialManager getInstance() {
        if (instance == null) {
            instance = new MaterialManager();
        }
        return instance;
    }

    public void add(World world, MaterialType materialType, Vector2 position) {
        MaterialsRender treeRender = new MaterialsRender(world, materialImageManager, materialType, position);
        trees.add( treeRender);
    }

    public List<MaterialsRender> getTrees() {
        return trees;
    }
}

