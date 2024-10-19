package io.github.Farm.animal.Buffalo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import io.github.Farm.animal.PetState;
import io.github.Farm.player.PlayerController;
import io.github.Farm.ui.MainMenu;

import java.util.ArrayList;
import java.util.Iterator;

public class BuffaloManager  {
    private ArrayList<Buffalo> buffaloManager;
    private long breedingTime;
    private long hptime=0;

    private static BuffaloManager buffalomanager;

    public static BuffaloManager getbuffalomanager(){
        if(buffalomanager==null){
            buffalomanager=new BuffaloManager();
        }
        return buffalomanager;
    }

    public BuffaloManager(){
        buffaloManager=new ArrayList<>();
    }

    public  void checkquantity(){
        if (buffaloManager.size() < 2) {
            if (breedingTime == 0) {
                breedingTime = TimeUtils.millis();
            }
            if (TimeUtils.timeSinceMillis(breedingTime) > 2000) {
                buffaloManager.add(new Buffalo(new Vector2(650,1050),100));
                breedingTime = 0;
            }
        }
        Iterator<Buffalo> iterator = buffaloManager.iterator();
        while (iterator.hasNext()) {
            Buffalo buffalo = iterator.next();
            if (buffalo.getmau().getCurrHp() <= 0) {
                iterator.remove();
            }
        }
    }

    public void checkHungry(){
            if(hptime==0){
                hptime=TimeUtils.millis();
            }
            if(TimeUtils.timeSinceMillis(hptime) > 5000){
                for(Buffalo buffalo:buffaloManager) {
                    buffalo.sethungry(10);
                }
                hptime=0;
            }

    }

    public void update(PlayerController playerController){
        checkquantity();
        checkHungry();
        activate();
        for(Buffalo buffalo:buffaloManager){
            for(Buffalo buffalo1:buffaloManager){
                if(buffalo!=buffalo1){
                    buffalo.collide(buffalo1);
                }
            }

            buffalo.update(Gdx.graphics.getDeltaTime());
        }
    }

    public void activate(){
        for(Buffalo buffalo:buffaloManager){
            if(buffalo.gethungry()<=0){
                if (buffalo.getLeft()) {
                    buffalo.setcrencurrentState(PetState.SLEEP_LEFT);
                } else {
                    buffalo.setcrencurrentState(PetState.SLEEP_RIGHT);
                }
                return;
            }
            else {
                if (buffalo.getIsStopped()) {
                    if (TimeUtils.timeSinceMillis(buffalo.getCollisionStopTime()) >= 5000) {
                        buffalo.setIsStopped(false);
                        buffalo.settargetLocation(buffalo.randomlocation()) ;
                    } else {
                        if (buffalo.getLeft()) {
                            buffalo.setcrencurrentState(PetState.IDLE_LEFT);
                        } else {
                            buffalo.setcrencurrentState(PetState.IDLE_RIGHT);
                        }
                    }

                }
                else {

                    if (buffalo.getTargetLocation() == null || buffalo.getlocation().epsilonEquals(buffalo.getTargetLocation(), 1f)) {
                        if (buffalo.getStopTime() == 0) {
                            buffalo.setStopTime(TimeUtils.millis()) ;
                        }

                        if (TimeUtils.timeSinceMillis(buffalo.getStopTime()) >= 5000 && buffalo.gethungry() > 0) {
                            buffalo.settargetLocation(buffalo.randomlocation()) ;
                            buffalo.setStopTime(0);

                        } else {
                            if (buffalo.getLeft()) {
                                buffalo.setcrencurrentState(PetState.IDLE_LEFT);
                            } else {
                                buffalo.setcrencurrentState(PetState.IDLE_RIGHT);
                            }
                        }
                    }else {
                        buffalo.movelocation();
                    }

                }
            }

        }
    }

    public ArrayList<Buffalo> getBuffaloManager(){
        return (buffaloManager);
    }

    public void dispose() {
        for (Buffalo buffalo : buffaloManager) {
            buffalo.dispose(); // Nếu lớp Buffalo có hàm dispose
        }
    }

}
