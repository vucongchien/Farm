package io.github.Farm.player.PLAYER_STATE;

import io.github.Farm.animal.Buffalo.Buffalo;
import io.github.Farm.animal.Buffalo.BuffaloManager;
import io.github.Farm.player.PlayerController;
import io.github.Farm.ui.Other.GreenBar;

import java.util.Iterator;

public class DoingState implements InterfacePlayerState{
    private final String direction;
    private float time;
    private final GreenBar greenBar;

    public DoingState(String direction){
        time=0f;
        this.direction=direction;
        greenBar =new GreenBar();
    }

    @Override
    public void enter(PlayerController player) {
    }

    @Override
    public void update(PlayerController player, float deltaTime) {
        time += player.getDeltaTime();
//        if (time >= 3f) {
//            player.setPlanting(false);
//        }
        greenBar.render( player.getPosition(), player.getCamera(), time,2, 14, 7);



    }

    @Override
    public void exit(PlayerController player) {

    }

    @Override
    public String getStateName() {
        return "DOING_"+direction;
    }
}
