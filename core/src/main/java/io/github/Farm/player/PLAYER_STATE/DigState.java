package io.github.Farm.player.PLAYER_STATE;

import com.badlogic.gdx.math.Vector2;
import io.github.Farm.player.PlayerController;
import io.github.Farm.ui.Other.GreenBar;

public class DigState implements InterfacePlayerState {
    private final String direction;
    private float time=0f;
    private final GreenBar greenBar =new GreenBar();
    private Vector2 LastPosition;
    public DigState(String direction){
        this.direction=direction;
    }

    @Override
    public void enter(PlayerController player) {
        time=0f;
        LastPosition=new Vector2(player.getPositionInMap());
    }

    @Override
    public void update(PlayerController player, float deltaTime) {
        if(!LastPosition.equals(player.getPositionInMap())){
            LastPosition.set(player.getPositionInMap());
            time=0f;
        }

        time+= player.getDeltaTime();
        if(time>=1f){
            player.getCollisionHandler().handlePlowing();
            time=0f;
        }


        greenBar.render( player.getPosition(),player.getCamera(), time,1f, 14f, 7f);
    }

    @Override
    public void exit(PlayerController player) {

    }

    @Override
    public String getStateName() {
        return "DIG_"+direction;
    }
}
