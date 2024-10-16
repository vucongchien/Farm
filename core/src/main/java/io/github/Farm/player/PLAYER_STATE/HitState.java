package io.github.Farm.player.PLAYER_STATE;

import com.badlogic.gdx.Gdx;
import io.github.Farm.Plants.PlantManager;
import io.github.Farm.player.PlayerController;

public class HitState implements InterfacePlayerState {
    private String direction;
    private static float startHit=0f;
    private float timeToHit=0.3f;

    public HitState(String direction){
        this.direction=direction;
    }

    @Override
    public void enter(PlayerController player) {

    }

    @Override
    public void update(PlayerController player, float deltaTime) {
        startHit+= Gdx.graphics.getDeltaTime();

        if(startHit>=0.5){
            startHit=0;
        }
        if(startHit>=timeToHit){
            hitSomeThing(player);
        }


    }

    public void hitSomeThing(PlayerController player){
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
