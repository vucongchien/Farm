package io.github.Farm.animal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.utils.Timer;
import io.github.Farm.animal.Buffalo.BuffaloManager;
import io.github.Farm.player.PLAYER_STATE.HurtState;
import io.github.Farm.player.PlayerController;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ThreadLocalRandom;

public class WolfManager {
    private static WolfManager wolfManager;
    public static WolfManager  getwolfmanage(){
        if(wolfManager==null){
            wolfManager=new WolfManager();
        }
        return wolfManager;
    }
    private ArrayList< WolfRender> wolfmanager;
    private ArrayList<WolfRender> storagewolfmanager;
    private Vector2 home = new Vector2(0, 0);
    private long breedingTime;
    private long starttime = 0, endtime = 0;
    private Vector2 preyleader = new Vector2(0, 0);
    //    private float deltaTime=Gdx.graphics.getDeltaTime();
    private boolean acttack;
    private float radius;
    private float angle;
    private  int fistcheck;




    public WolfManager() {
        wolfmanager = new ArrayList<>();
        storagewolfmanager = new ArrayList<>();
        home =new Vector2(1050,1050);

        for(int i=0;i<5;i++) {
            radius = ThreadLocalRandom.current().nextFloat(20, 40);
            angle = (float) Math.toRadians(ThreadLocalRandom.current().nextFloat(-90, 90));
            float xOffset = (float) Math.cos(angle) * radius;
            float yOffset = (float) Math.sin(angle) * radius;
            storagewolfmanager.add(new WolfRender(new Vector2(home.x + xOffset, home.y + yOffset), false));
            storagewolfmanager.get(storagewolfmanager.size()-1).setDistancefrombossx(xOffset);
            storagewolfmanager.get(storagewolfmanager.size()-1).setDistancefrombossy(yOffset);
        }
    }


    public void setBoss(BuffaloManager buffaloManager) {
        boolean checkboss = false;
        if (wolfmanager.size() != 0) {
            for (WolfRender x : wolfmanager) {
                if (x.getthulinh() == true) {
                    checkboss = true;
                }
            }
            if (checkboss == false) {
                wolfmanager.get(0).setthulinh(true);
                wolfmanager.get(0).setcheck(2);
            }
        }
    }


    public void checkquantity() {

        if (wolfmanager.size() < 1) {
                if (breedingTime == 0) {
                    breedingTime = TimeUtils.millis();
                }
                if (TimeUtils.timeSinceMillis(breedingTime) > 2000) {
                    WolfRender wolf = storagewolfmanager.remove(storagewolfmanager.size() - 1);
                    wolfmanager.add(wolf);
                    breedingTime = 0;
                }

        }
        Iterator<WolfRender> iterator = wolfmanager.iterator();
        while (iterator.hasNext()) {
            WolfRender wolfremove = iterator.next();
            if (wolfremove.getHp().getCurrHp() <= 0) {
                iterator.remove();
                storagewolfmanager.add(wolfremove);
                wolfremove.reset(home);
            }
        }
    }

    public void update(BuffaloManager buffaloManager,PlayerController playerController) {
        setBoss(buffaloManager);
        checkquantity();
        activate(buffaloManager);
        for(WolfRender x:wolfmanager){
            x.checkHit(playerController);

        }
        attackplayer(playerController);
    }

    public void activate(BuffaloManager buffaloManager) {
        if(acttack==false) {
            for (WolfRender wolf : wolfmanager) {
                if (wolf.getthulinh() == true) {
                    if (Math.abs(wolf.getlocation().x - home.x) < 1f && Math.abs(wolf.getlocation().y - home.y) < 1f) {
                        if (starttime == 0) {
                            starttime = TimeUtils.millis();
                        }
                        if (TimeUtils.timeSinceMillis(starttime) < 20000) {
                            wolf.setcheck(0);
                            fistcheck=wolf.getcheck();
                            wolf.setCrencurrentState(PetState.IDLE_RIGHT);
                            System.out.println(TimeUtils.timeSinceMillis(starttime));
                        } else {
                            wolf.setprey(buffaloManager);
                            preyleader = wolf.getPrey().getlocation().cpy().add(50, 50);
                            wolf.setcheck(1);
                            fistcheck=wolf.getcheck();
                        }
                    } else {
                        starttime = 0;
                    }
                    System.out.println(starttime);
                    if (preyleader.x != 0 || preyleader.y != 0) {
                        if (Math.abs(wolf.getlocation().x - preyleader.x) < 10f && Math.abs(wolf.getlocation().y - preyleader.y) < 10f) {
                            if (endtime == 0) {
                                endtime = TimeUtils.millis();
                                wolf.setTrangthaitancong(true);
                            }
                            if (TimeUtils.timeSinceMillis(endtime) < 20000) {
                                wolf.setCrencurrentState(PetState.IDLE_RIGHT);
                                wolf.setcheck(0);
                                fistcheck=wolf.getcheck();
                            } else {
                                wolf.setTrangthaitancong(false);
                                wolf.setcheck(2);
                                fistcheck=wolf.getcheck();


                            }
                        } else {
                            endtime = 0;
                            if (wolf.getcheck() == 1) {
                                System.out.println("ngu2");
                                movelocation(wolf, preyleader);
                            }
                        }
                        System.out.println(wolf.getcheck());
                    }
                    if (wolf.getcheck() == 2) {
                        movelocation(wolf, home, 1f, 1f,0.007f);
                        if (Math.abs(wolf.getlocation().x - home.x) < 1f && Math.abs(wolf.getlocation().y - home.y) < 1f) {
                            wolf.setprey(buffaloManager);
                            preyleader = wolf.getPrey().getlocation().cpy().add(50, 50);
                        }
                    }
                } else {
                    Vector2 bossLocation = null;
                    boolean bossAttack = false;
                    bossLocation = wolfmanager.get(0).getlocation().cpy();
                    bossAttack = wolfmanager.get(0).gettrangthaitancon();
                    wolf.setprey(buffaloManager);
                    if (bossLocation != null) {
                        if (bossAttack == false || wolf.getPrey() == null) {
                            if (Math.abs(bossLocation.x - home.x) < 1f && Math.abs(bossLocation.y - home.y) < 1f) {
                                wolf.setCrencurrentState(PetState.IDLE_RIGHT);
                                movelocation(wolf, new Vector2(bossLocation.x + wolf.getdistancefrombossx(), bossLocation.y + wolf.getDistancefrombossy()), 1f, 1f,0.007f);
                            } else {

                                if (Math.abs(wolf.getlocation().x - bossLocation.x) < Math.abs(wolf.getdistancefrombossx()) && Math.abs(wolf.getlocation().y - bossLocation.y) < Math.abs(wolf.getDistancefrombossy())) {
                                    wolf.setCrencurrentState(PetState.IDLE_RIGHT);
                                } else {
                                    movelocation(wolf, bossLocation, Math.abs(wolf.getdistancefrombossx()), Math.abs(wolf.getDistancefrombossy()),0.007f);
                                }
                            }
                        } else {
                            float deltaX = wolf.getlocation().x - wolf.getPrey().getlocation().x;
                            float deltaY = wolf.getlocation().y - wolf.getPrey().getlocation().y;
                            float overlapX = (wolf.getCollider().width / 2 + wolf.getPrey().getbox().width / 2) - Math.abs(deltaX);
                            float overlapY = (wolf.getCollider().height / 2 + wolf.getPrey().getbox().height / 2) - Math.abs(deltaY);
                            if (overlapX > 0 && overlapY > 0) {
                                wolf.getPrey().setmau();
                                float pushFactor = 5f;
                                if (Math.abs(overlapX) < Math.abs(overlapY)) {
                                    if (deltaX > 0) {
                                        wolf.getPrey().getlocation().x += overlapX * pushFactor;
                                    } else {
                                        wolf.getPrey().getlocation().x -= overlapX * pushFactor;
                                    }
                                } else {
                                    if (deltaY > 0) {
                                        wolf.getPrey().getlocation().y += overlapY * pushFactor;
                                    } else {
                                        wolf.getPrey().getlocation().y -= overlapY * pushFactor;
                                    }
                                }
                                wolf.getPrey().setLocation(wolf.getPrey().getlocation().x, wolf.getPrey().getlocation().y);
                                wolf.getPrey().setBox(wolf.getPrey().getlocation().x, wolf.getPrey().getlocation().y);

                            }
                            movelocation(wolf, wolf.getPrey().getlocation());
                        }
                    } else {
                        wolf.setCrencurrentState(PetState.IDLE_RIGHT);
                    }
                }
            }
        }
    }

    public void attackplayer(PlayerController playerController){
        for(WolfRender x:wolfmanager){
            if(x.gethit()){
                acttack=true;
                break;
            }
        }
        if(acttack){
            for(WolfRender wolfRender:wolfmanager){
                if(wolfRender.getlocation().x>playerController.getPosition().x) {
                    if (Math.abs(wolfRender.getlocation().x - playerController.getPosition().x) <= 10f && Math.abs(wolfRender.getlocation().y - playerController.getPosition().y) <= 10f) {
                        wolfRender.setCrencurrentState(PetState.IDLE_LEFT);
                        wolfRender.setKill(true);
                        playerController.changeState(new HurtState("LEFT"));
                        wolfRender.setanimationattack(true);
                        while (wolfRender.getcooldown()>0) {
                            wolfRender.setcooldown();
                        }
                    } else {
                        movelocation(wolfRender, playerController.getPosition(), 10f, 10f, 0.007f);
                        wolfRender.setKill(false);
                        wolfRender.recoverycooldown();
                    }
                }
                else{
                    if (Math.abs(wolfRender.getlocation().x - playerController.getPosition().x) <= 10f && Math.abs(wolfRender.getlocation().y - playerController.getPosition().y) <= 10f) {
                        wolfRender.setCrencurrentState(PetState.IDLE_RIGHT);
                        wolfRender.setKill(true);
                        playerController.changeState(new HurtState("RIGHT"));
                        wolfRender.setanimationattack(true);
                        while (wolfRender.getcooldown()>0) {
                            wolfRender.setcooldown();
                        }
                    } else {
                        wolfRender.setKill(false);
                        movelocation(wolfRender, playerController.getPosition(), 10f, 10f, 0.007f);
                        wolfRender.recoverycooldown();
                    }
                }
            }
            acttack = false;
            if(wolfmanager.size()>0&&wolfmanager.get(0).getcheck()==0) {
                wolfmanager.get(0).setcheck(fistcheck);
            }
            for (WolfRender x : wolfmanager) {
                if (x.gethit()) {
                    acttack = true;
                    wolfmanager.get(0).setcheck(0);
                    break;
                }
            }
        }


    }

    public void movelocation(WolfRender a, Vector2 b) {
        movelocation(a, b, 10f,10f,0.007f);
    }


    public void movelocation(WolfRender a, Vector2 b, float sox,float soy,float deltaTime) {
        if (Math.abs(a.getlocation().x - b.x) > sox) {
            if (a.getlocation().x > b.x) {
                a.getlocation().x -= 10f * deltaTime;
                a.setLocation(a.getlocation().x, a.getlocation().y);
                a.getCollider().setPosition(a.getlocation().cpy().x-10, a.getlocation().cpy().y-15);
                a.setCrencurrentState(PetState.WALK_LEFT);
            } else if (a.getlocation().x < b.x) {
                a.getlocation().x += 10f * deltaTime;
                a.setLocation(a.getlocation().x, a.getlocation().y);
                a.getCollider().setPosition(a.getlocation().cpy().x-10, a.getlocation().cpy().y-15);
                a.setCrencurrentState(PetState.WALK_RIGHT);
            }
        }
        if (Math.abs(a.getlocation().y - b.y) > soy) {
            if (a.getlocation().y > b.y) {
                a.getlocation().y -= 10f * deltaTime;
                a.setLocation(a.getlocation().x, a.getlocation().y);
                a.getCollider().setPosition(a.getlocation().cpy().x-10, a.getlocation().cpy().y-15);
//                a.setCrencurrentState(PetState.WALK_FACE);
            } else if (a.getlocation().y < b.y) {
                a.getlocation().y += 10f * deltaTime;
                a.setLocation(a.getlocation().x, a.getlocation().y);
                a.getCollider().setPosition(a.getlocation().cpy().x-10, a.getlocation().cpy().y-15);
//                a.setCrencurrentState(PetState.WALK_BACK);
            }
        }
    }

    public ArrayList<WolfRender> getwolfmanafer() {
        return (wolfmanager);
    }

    public void dispose(){
        for(WolfRender wolfRender:storagewolfmanager){
            wolfRender.dispose();
        }
        for(WolfRender wolfRender:wolfmanager){
            wolfRender.dispose();
        }
    }
}
