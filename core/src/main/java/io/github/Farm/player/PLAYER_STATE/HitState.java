package io.github.Farm.player.PLAYER_STATE;

import com.badlogic.gdx.Gdx;
import io.github.Farm.Plants.PlantRenderer;
import io.github.Farm.player.PlayerController;

import java.util.Iterator;

public class HitState implements InterfacePlayerState {
    private String direction;
    private static float startHit=0f;
    private float timeToHit=0.3f;

    public HitState(String direction){
        this.direction=direction;
    }
    @Override
    public void enter(PlayerController player) {
        player.setCurrentState("HIT_"+direction);
    }

    @Override
    public void update(PlayerController player, float deltaTime) {
        startHit+= Gdx.graphics.getDeltaTime();
        if(startHit>=timeToHit){
            hitSomeThing(player);
        }
        if(startHit>=0.5){
            startHit=0;
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
