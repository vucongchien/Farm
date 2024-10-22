package io.github.Farm.player.PLAYER_STATE;

import com.badlogic.gdx.Gdx;
import io.github.Farm.Plants.PlantManager;
import io.github.Farm.animal.WOLF.WolfManager;
import io.github.Farm.animal.WOLF.WolfRender;
import io.github.Farm.player.PlayerController;

import java.util.Iterator;

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
        check=false;
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
        System.out.println("start hit ");

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
        check=false;
    }

    @Override
    public String getStateName() {
        return "HIT_"+direction;
    }
}
