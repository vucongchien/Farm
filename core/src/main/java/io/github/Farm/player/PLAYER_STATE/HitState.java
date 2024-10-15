package io.github.Farm.player.PLAYER_STATE;

import com.badlogic.gdx.Gdx;
import io.github.Farm.Plants.PlantManager;
import com.badlogic.gdx.utils.TimeUtils;
import io.github.Farm.Plants.PlantRenderer;
import io.github.Farm.animal.WolfManager;
import io.github.Farm.animal.WolfRender;
import io.github.Farm.player.PlayerController;

public class HitState implements InterfacePlayerState {
    private String direction;
    private static float startHit=0f;
    private float timeToHit=0.3f;
    static boolean check;

    public HitState(String direction){
        this.direction=direction;
    }
    @Override
    public void enter(PlayerController player) {

    }

    @Override
    public void update(PlayerController player, float deltaTime) {
        startHit+= Gdx.graphics.getDeltaTime();

        if(startHit>=timeToHit&&!check){
                hitSomeThing(player);
                check=true;
        }
        if(startHit>=0.5){
            startHit=0;
            check=false;
        }

    }

    public void hitSomeThing(PlayerController player){
        Iterator<PlantRenderer> iterator = player.getCollisionHandler().getPlantManager().getPlants().iterator();
        while (iterator.hasNext()) {
            PlantRenderer plant = iterator.next();
            if (player.getCollider().overlaps(plant.getCollider())) {
                plant.onCollision(player);
                iterator.remove();
            }
        }
        Iterator<WolfRender> iteratorwolf = WolfManager.getwolfmanage().getwolfmanafer().iterator();
        while (iteratorwolf.hasNext()){
            WolfRender wolf=iteratorwolf.next();
            if (player.getCollider().overlaps(wolf.getCollider())) {
                wolf.onCollision(player);

            }
        }

        if(PlantManager.getInstance().getPlantAt(player.getPositionInMap())!=null){
            PlantManager.getInstance().getMapPlants().get(player.getPositionInMap()).onCollision(player);
            PlantManager.getInstance().getMapPlants().remove(player.getPositionInMap());
        }
        startHit=-0.2f;
    }

    @Override
    public void exit(PlayerController player) {
        startHit=0;
    }

    @Override
    public String getStateName() {
        return "HIT_"+direction;
    }
}
