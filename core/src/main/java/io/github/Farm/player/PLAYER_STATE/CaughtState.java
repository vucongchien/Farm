package io.github.Farm.player.PLAYER_STATE;

import com.badlogic.gdx.math.Vector2;
import io.github.Farm.player.PlayerController;
import io.github.Farm.inventory.ItemManager;

public class CaughtState implements InterfacePlayerState {

    private String direction;
    private float waitTime = 0f;

    public CaughtState(String direction) {
        this.direction = direction;
    }

    @Override
    public void enter(PlayerController player) {

    }

    @Override
    public void update(PlayerController player, float deltaTime) {
        if(waitTime<0.2f){
            waitTime+=deltaTime;
            return;
        }
        player.changeState(new IdleState(direction));
    }

    @Override
    public void exit(PlayerController player) {
        ItemManager.getInstance().addItemVip("FOOD_fish",new Vector2(player.getPositionInMap()), !player.isFacingRight(), 1);
        System.out.println("Exiting Caught State");
    }

    @Override
    public String getStateName() {
        return "CAUGHT_" + direction;
    }

}
