package io.github.Farm.player.PLAYER_STATE;

import com.badlogic.gdx.math.Vector2;
import io.github.Farm.player.PlayerController;

public class HurtState implements InterfacePlayerState{
    private String direction;
    private float time=0f;

    public HurtState(String direction){
        this.direction = direction;
    }

    @Override
    public void enter(PlayerController player) {
        player.getBody().applyLinearImpulse(new Vector2(direction.equals("RIGHT") ? -50f : 50f, 2f).scl(150f), player.getBody().getWorldCenter(), true);
        player.getHeath().damaged(10);
    }

    @Override
    public void update(PlayerController player, float deltaTime) {
        time+= player.getDeltaTime();
        if(time>=0.31f){
            player.changeState(new IdleState(direction));
        }
    }

    @Override
    public void exit(PlayerController player) {
    }

    @Override
    public String getStateName() {
        return "HURT_" + direction;
    }
}
