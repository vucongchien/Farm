package io.github.Farm.Renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import io.github.Farm.Interface.RenderableEntity;
import io.github.Farm.Plants.PlantManager;
import io.github.Farm.Plants.PlantRenderer;
import io.github.Farm.ui.inventory.ItemManager;
import io.github.Farm.player.PlayerRenderer;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameRenderer {
    private SpriteBatch batch;
    private PlayerRenderer player;
    private Camera camera;
    private TiledMap map;

    List<RenderableEntity> renderableEntities = new ArrayList<>();


    public GameRenderer(PlayerRenderer player, Camera camera, TiledMap map){
        batch=new SpriteBatch();
        this.player =player;
        this.camera=camera;
        this.map=map;
    }

    public void render() {

        renderAllEntities(batch);
    }

    private void renderAllEntities(SpriteBatch batch) {
        renderableEntities.clear();

        renderableEntities.add(player);

        renderableEntities.addAll(PlantManager.getInstance().getListPlants());

        renderableEntities.addAll(ItemManager.getInstance().getItemList());


        renderableEntities.sort(new Comparator<RenderableEntity>() {
            @Override
            public int compare(RenderableEntity e1, RenderableEntity e2) {
                return Float.compare(e2.getY(), e1.getY());
            }
        });



        for (RenderableEntity entity : renderableEntities) {
            entity.render(batch,camera);
            if (entity instanceof PlantRenderer) {
                PlantRenderer plantRenderer = (PlantRenderer) entity;
            }

        }


    }
}
