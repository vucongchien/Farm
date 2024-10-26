package io.github.Farm.player.PLAYER_STATE;

import io.github.Farm.player.PlayerController;

public class HammerState implements InterfacePlayerState{
    private String direction;
    private float time=0f;

    public HammerState(String direction){
        this.direction=direction;
    }




    @Override
    public void enter(PlayerController player) {
        time=0f;
    }

    @Override
    public void update(PlayerController player, float deltaTime) {
        time+= player.getDeltaTime();
        if(time>=0.46){
            time=0f;
        }
        else if(time>=0.35){
            hammer(player);
        }
    }

    public void hammer(PlayerController player){
        player.setStamina(player.getStamina()-1);
    }

    @Override
    public void exit(PlayerController player) {

    }


    @Override
    public String getStateName() {
        return "HAMMER_"+direction;
    }
}
