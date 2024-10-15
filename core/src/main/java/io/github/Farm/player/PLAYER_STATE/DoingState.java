package io.github.Farm.player.PLAYER_STATE;

import io.github.Farm.player.PlayerController;
import io.github.Farm.ui.Other.TimeCoolDown;

import java.util.Timer;

public class DoingState implements InterfacePlayerState{
    private String direction;
    private float time=0f;
    private  TimeCoolDown timeCoolDown;

    public DoingState(String direction){
        this.direction=direction;
        timeCoolDown=new TimeCoolDown();
    }

    @Override
    public void enter(PlayerController player) {

    }

    @Override
    public void update(PlayerController player, float deltaTime) {
        time += deltaTime;

        if (time >= 2f) {
            player.setPlanting(false);
        }
        timeCoolDown.renderGreenBar( player.getPositionInMap().scl(16), 3, 32, 16);

    }

    @Override
    public void exit(PlayerController player) {

    }

    @Override
    public String getStateName() {
        return "DOING_"+direction;
    }
}
