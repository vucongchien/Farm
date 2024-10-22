package io.github.Farm.Renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import io.github.Farm.Interface.Animal;
import io.github.Farm.Interface.RenderableEntity;
import io.github.Farm.Map.MapManager;
import io.github.Farm.Plants.PlantManager;
import io.github.Farm.Plants.PlantRenderer;
import io.github.Farm.animal.Buffalo.BuffaloManager;
import io.github.Farm.animal.Chicken.ChickenManager;
import io.github.Farm.animal.Pig.PigManager;
import io.github.Farm.animal.WOLF.WolfManager;
import io.github.Farm.inventory.ItemManager;
import io.github.Farm.player.PlayerRenderer;
import io.github.Farm.weather.Weather;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GameRenderer {
    private SpriteBatch batch;
    private PlayerRenderer player;
    private List<Animal> animal;
    private PlantManager plantManager;
    private Camera camera;
    private TiledMap map;
    private Weather weather;
    private MapManager mapManager;


    List<RenderableEntity> renderableEntities = new ArrayList<>();


    public GameRenderer(PlayerRenderer player, Camera camera, TiledMap map){
        batch=new SpriteBatch();
        this.player =player;
        this.camera=camera;
        this.map=map;
        this.animal= new ArrayList<>();
    }

    public void render() {
        if(Weather.getInstance().getNight()){
            batch.setColor(Color.GRAY);
        }else{
            batch.setColor(Color.WHITE);
        }
        renderAllEntities(batch);
    }



    private void renderAllEntities(SpriteBatch batch) {
        renderableEntities.clear();

        renderableEntities.add(player);

        renderableEntities.addAll(PlantManager.getInstance().getListPlants());

        renderableEntities.addAll(ItemManager.getInstance().getItemList());

        renderableEntities.addAll(BuffaloManager.getbuffalomanager().getBuffaloManager());

        renderableEntities.addAll(WolfManager.getwolfmanage().getwolfmanafer());

        renderableEntities.addAll(PigManager.getPigmanager().getPigManager());

        renderableEntities.addAll(ChickenManager.getChickenmanager().getChickenManager());

       // renderableEntities.add(new MouseRener(new Vector2(850,1050)));


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
