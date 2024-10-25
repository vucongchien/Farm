package io.github.Farm.Trees;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TreeManager {

    private static TreeManager instance;
    private final TreeImageManager treeImageManager;
    private final List<TreeRender> trees;

    private TreeManager() {
        this.treeImageManager = new TreeImageManager();
        this.trees = new ArrayList<>();
    }

    public static TreeManager getInstance() {
        if (instance == null) {
            instance = new TreeManager();
        }
        return instance;
    }

    public void addTree(World world, TreeType treeType, Vector2 position) {
        TreeRender treeRender = new TreeRender(world, treeImageManager, treeType, position);
        trees.add( treeRender);
    }

    public List<TreeRender> getTrees() {
        return trees;
    }
}

