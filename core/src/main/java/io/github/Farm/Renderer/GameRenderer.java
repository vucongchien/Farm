package io.github.Farm.Renderer;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import io.github.Farm.Interface.Animal;
import io.github.Farm.Interface.RenderableEntity;
import io.github.Farm.Plants.PlantManager;
import io.github.Farm.Plants.PlantRenderer;
import io.github.Farm.animal.Buffalo.BuffaloManager;
import io.github.Farm.animal.WolfManager;
import io.github.Farm.player.PlayerRenderer;

import java.util.ArrayList;
import java.util.List;

public class GameRenderer {
    private SpriteBatch batch;
    private PlayerRenderer player;
    private List<Animal> animal;
    private PlantManager plantManager;
    private Camera camera;
    private TiledMap map;
    private long checkquantity=0;
    private long checkquantitybuffalo=0;
    private long checkquantitywolf=0;


    public GameRenderer(PlayerRenderer player,PlantManager plantManager, Camera camera, TiledMap map){
        batch=new SpriteBatch();
        this.plantManager=plantManager;
        this.player =player;
        this.camera=camera;
        this.map=map;
        this.animal= new ArrayList<>();
    }

    public void render() {
        renderAllEntities(batch);
    }

//    public void addanimal(BuffaloManager buffaloManager,WolfManager wolfManager){
//
//            if(checkquantitybuffalo<buffaloManager.getBuffaloManager().size()) {
//                for(;checkquantitybuffalo<buffaloManager.getBuffaloManager().size();checkquantitybuffalo++){
//                    animal.add(buffaloManager.getBuffaloManager().get((int) checkquantitybuffalo));
//                }
//            }
//            if(checkquantitywolf<wolfManager.getwolfmanafer().size()) {
//                for(;checkquantitywolf<wolfManager.getwolfmanafer().size();checkquantitywolf++) {
//                    animal.add(wolfManager.getwolfmanafer().get((int) checkquantitywolf));
//                }
//            }
//            if(checkquantitybuffalo<buffaloManager.getBuffaloManager().size()){
//
//            }
//
//
//    }


    private void renderAllEntities(SpriteBatch batch) {

        List<RenderableEntity> renderableEntities = new ArrayList<>();

        renderableEntities.add(player);

        renderableEntities.addAll(plantManager.getPlants());

        renderableEntities.addAll(BuffaloManager.getbuffalomanager().getBuffaloManager());

        renderableEntities.addAll(WolfManager.getwolfmanage().getwolfmanafer());

//        renderableEntities.sort(new Comparator<RenderableEntity>() {
//            @Override
//            public int compare(RenderableEntity e1, RenderableEntity e2) {
//                return Float.compare(e2.getY(), e1.getY());
//            }
//        });

        for (RenderableEntity entity : renderableEntities) {
            entity.render(batch,camera);
            if (entity instanceof PlantRenderer) {
                PlantRenderer plantRenderer = (PlantRenderer) entity;

            }
        }
    }
}
