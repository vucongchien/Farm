package io.github.Farm.Renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import io.github.Farm.Interface.RenderableEntity;
import io.github.Farm.Plants.PlantManager;
import io.github.Farm.Plants.PlantRenderer;
import io.github.Farm.player.PlayerRenderer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameRenderer {
    private SpriteBatch batch;
    private PlayerRenderer player;
    private PlantManager plantManager;
    private Camera camera;
    private TiledMap map;


    public GameRenderer(PlayerRenderer player, PlantManager plantManager, Camera camera, TiledMap map){
        batch=new SpriteBatch();
        this.plantManager=plantManager;
        this.player =player;
        this.camera=camera;
        this.map=map;
    }

    public void render() {

        renderAllEntities(batch);
    }

    private void renderAllEntities(SpriteBatch batch) {
        List<RenderableEntity> renderableEntities = new ArrayList<>();

        renderableEntities.add(player);

        renderableEntities.addAll(plantManager.getPlants());


        renderableEntities.sort(new Comparator<RenderableEntity>() {
            @Override
            public int compare(RenderableEntity e1, RenderableEntity e2) {
                return Float.compare(e2.getY(), e1.getY());
            }
        });
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        for (RenderableEntity entity : renderableEntities) {
            entity.render(batch);
            if (entity instanceof PlantRenderer) {
                PlantRenderer plantRenderer = (PlantRenderer) entity;

            }
        }

        batch.end();
    }
}
