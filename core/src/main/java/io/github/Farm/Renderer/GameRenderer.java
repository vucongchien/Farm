package io.github.Farm.Renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.utils.Disposable;
import io.github.Farm.Interface.RenderableEntity;
import io.github.Farm.Plants.PlantManager;
import io.github.Farm.Materials.MaterialManager;
import io.github.Farm.animal.Buffalo.BuffaloManager;
import io.github.Farm.animal.Chicken.ChickenManager;
import io.github.Farm.animal.Pig.PigManager;
import io.github.Farm.animal.WOLF.WolfManager;
import io.github.Farm.inventory.ItemManager;
import io.github.Farm.player.PlayerRenderer;
import io.github.Farm.ui.Weather;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameRenderer implements Disposable {
    private SpriteBatch batch;
    private PlayerRenderer player;
    private Camera camera;


    List<RenderableEntity> renderableEntities = new ArrayList<>();


    public GameRenderer(PlayerRenderer player, Camera camera, TiledMap map){
        batch=new SpriteBatch();
        this.player =player;
        this.camera=camera;
    }

    public void render() {
        if(Weather.getInstance().getNight()){
            batch.setColor(Color.WHITE);
        }else{
            batch.setColor(Color.WHITE);
        }
        renderAllEntities(batch);
    }



    private void renderAllEntities(SpriteBatch batch) {
        renderableEntities.clear();

        renderableEntities.add(player);
        gatherEntities();

        renderableEntities.sort(Comparator.comparing(RenderableEntity::getY).reversed());

        // Render all entities
        for (RenderableEntity entity : renderableEntities) {
            entity.render(batch, camera);
        }
    }

    private void gatherEntities() {
        renderableEntities.addAll(PlantManager.getInstance().getListPlants());
        renderableEntities.addAll(ItemManager.getInstance().getItemList());
        renderableEntities.addAll(BuffaloManager.getbuffalomanager().getBuffaloManager());
        renderableEntities.addAll(WolfManager.getwolfmanage().getwolfmanafer());
        renderableEntities.addAll(PigManager.getPigmanager().getPigManager());
        renderableEntities.addAll(ChickenManager.getChickenmanager().getChickenManager());
        renderableEntities.addAll(MaterialManager.getInstance().getTrees());
        // Optionally add other entities like mouse pointer if needed
    }

    @Override
    public void dispose() {
        batch.dispose();

    }
}
