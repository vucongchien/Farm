package io.github.Farm.player.lam_lai_file.PLAYER_STATE;

import com.badlogic.gdx.Gdx;
import io.github.Farm.Plants.PlantRenderer;
import io.github.Farm.player.lam_lai_file.PlayerCotrollerr;

import java.util.Iterator;

public class HitState implements PlayerStateee{
    private String direction;
    private static float startHit=0f;
    private float timeToHit=0.3f;

    public HitState(String direction){
        this.direction=direction;
    }
    @Override
    public void enter(PlayerCotrollerr player) {
        player.setCurrentState("HIT_"+direction);
    }

    @Override
    public void update(PlayerCotrollerr player, float deltaTime) {
        //check plant
        startHit+= Gdx.graphics.getDeltaTime();
        if(startHit>=timeToHit){
            hitSomeThing(player);
        }
        if(startHit>=0.5){
            startHit=0;
        }
        System.out.println(startHit+ "  "+ timeToHit);

    }

    public void hitSomeThing(PlayerCotrollerr player){
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
    public void exit(PlayerCotrollerr player) {
        startHit=0;
    }

    @Override
    public String getStateName() {
        return "HIT_"+direction;
    }
}
