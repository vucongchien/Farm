package io.github.Farm.animal.Buffalo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.TimeUtils;
import io.github.Farm.Interface.Animal;
import io.github.Farm.Interface.RenderableEntity;
import io.github.Farm.animal.PetState;
import io.github.Farm.player.PlayerController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class BuffaloManager  {
    private ArrayList<Buffalo> buffaloManager;
    private long breedingTime;
    private Vector2 targetLocation = null;
//    private long collisionStopTime = 0;
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

//    @Override
    public  void checkquantity(){
        if (buffaloManager.size() < 2) {
            if (breedingTime == 0) {
                breedingTime = TimeUtils.millis();
            }
            if (TimeUtils.timeSinceMillis(breedingTime) > 2000) {
                buffaloManager.add(new Buffalo(new Vector2(650,1050),100,false));
                breedingTime = 0;
            }
        }
        Iterator<Buffalo> iterator = buffaloManager.iterator();
        while (iterator.hasNext()) {
            Buffalo buffalo = iterator.next();
            if (buffalo.getmau() <= 0) {
                System.out.println("xoa");
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
            buffalo.plow(playerController);
        }
    }
    public void activate(){
        for(Buffalo buffalo:buffaloManager){
            for(Buffalo buffalo1:buffaloManager){
                if(buffalo!=buffalo1){
                    buffalo.collide(buffalo1);
                }
            }
            if(buffalo.gethungry()<=0){
                if (buffalo.getBeside() == 3) {
                    buffalo.setcrencurrentState(PetState.SLEEP_LEFT);
                } else {
                    buffalo.setcrencurrentState(PetState.SLEEP_RIGHT);
                }
            }else {
                if (buffalo.getIsStopped()) {
                    if (TimeUtils.timeSinceMillis(buffalo.getCollisionStopTime()) >= 5000) {
                        buffalo.setIsStopped(false);
                        buffalo.settargetLocation(buffalo.randomlocation()) ;
                    } else {
                        if (buffalo.getBeside() == 3) {
                            buffalo.setcrencurrentState(PetState.IDLE_LEFT);
                        } else {
                            buffalo.setcrencurrentState(PetState.IDLE_RIGHT);
                        }
                    }
                } else {
                    if (buffalo.getTargetLocation() == null || buffalo.getlocation().epsilonEquals(buffalo.getTargetLocation(), 1f)) {
                        if (buffalo.getStopTime() == 0) {
                            buffalo.setStopTime(TimeUtils.millis()) ;
                        }

                        if (TimeUtils.timeSinceMillis(buffalo.getStopTime()) >= 5000 && buffalo.gethungry() > 0) {
                            buffalo.settargetLocation(buffalo.randomlocation()) ;
                            buffalo.setStopTime(0);

                        } else {
                            if (buffalo.getBeside() == 3) {
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
