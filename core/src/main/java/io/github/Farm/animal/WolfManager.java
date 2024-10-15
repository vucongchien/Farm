package io.github.Farm.animal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import io.github.Farm.animal.Buffalo.Buffalo;
import io.github.Farm.animal.Buffalo.BuffaloManager;
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
    private int acttack;
    private float radius;
    private float angle;

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


    public void setBoss() {
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

        if (wolfmanager.size() < 5) {
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
//            System.out.println(wolfremove.gethp());
            if (wolfremove.getHp().getCurrHp() <= 0) {
//                System.out.println(wolfremove.gethp()+"ngu-1");
                iterator.remove();
                storagewolfmanager.add(wolfremove);
                wolfremove.reset(home);
            }
        }
    }

    public void update(BuffaloManager buffaloManager,PlayerController playerController) {
        setBoss();
        checkquantity();
        attackplayer(playerController);
        activate(buffaloManager);

    }

    public void activate(BuffaloManager buffaloManager) {
        if(acttack==0) {
            for (WolfRender wolf : wolfmanager) {
                if (wolf.getthulinh() == true) {
//                System.out.println("tau");
                    if (Math.abs(wolf.getlocation().x - home.x) < 1f && Math.abs(wolf.getlocation().y - home.y) < 1f) {
                        System.out.println("ưhy");
                        if (starttime == 0) {
                            starttime = TimeUtils.millis();
                        }
                        if (TimeUtils.timeSinceMillis(starttime) < 10000) {
                            wolf.setcheck(0);
                            wolf.setCrencurrentState(PetState.IDLE_RIGHT);
                        } else {
                            wolf.setprey(buffaloManager);
                            preyleader = wolf.getPrey().getlocation().cpy().add(50, 50);
                            starttime = 0;
                            wolf.setcheck(1);
                        }
                    } else {
                        starttime = 0;

                    }
                    if (preyleader.x != 50 || preyleader.y != 50) {
                        if (Math.abs(wolf.getlocation().x - preyleader.x) < 10f && Math.abs(wolf.getlocation().y - preyleader.y) < 10f) {
                            if (endtime == 0) {
                                endtime = TimeUtils.millis();
                                wolf.setTrangthaitancong(true);
                            }
                            if (TimeUtils.timeSinceMillis(endtime) < 10000) {
                                wolf.setCrencurrentState(PetState.IDLE_RIGHT);
                                wolf.setcheck(0);
                            } else {
                                wolf.setTrangthaitancong(false);
                                wolf.setcheck(2);
                                endtime = 0;
                            }
                        } else {
                            endtime = 0;
                        }

                        if (wolf.getcheck() == 1) {
                            movelocation(wolf, preyleader);
                        }
                    }
                    if (wolf.getcheck() == 2) {
                        movelocation(wolf, home, 1f, 1f);
                        if (Math.abs(wolf.getlocation().x - home.x) < 1f && Math.abs(wolf.getlocation().y - home.y) < 1f) {
                            preyleader = new Vector2(0, 0);
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
                                movelocation(wolf, new Vector2(bossLocation.x + wolf.getdistancefrombossx(), bossLocation.y + wolf.getDistancefrombossy()), 1f, 1f);
                            } else {

                                if (Math.abs(wolf.getlocation().x - bossLocation.x) < Math.abs(wolf.getdistancefrombossx()) && Math.abs(wolf.getlocation().y - bossLocation.y) < Math.abs(wolf.getDistancefrombossy())) {
                                    wolf.setCrencurrentState(PetState.IDLE_RIGHT);
//                            System.out.println(wolf.getCrencurrentState() + "1");
                                } else {
                                    movelocation(wolf, bossLocation, Math.abs(wolf.getdistancefrombossx()), Math.abs(wolf.getDistancefrombossy()));
//                            System.out.println(wolf.getCrencurrentState() + "2");
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
        acttack=0;
        for(WolfRender wolfRender:wolfmanager){
            if(wolfRender.getattacked()==true){
                acttack++;
                break;
            }
        }
        if(acttack>0){
            for(WolfRender wolfRender:wolfmanager){
                movelocation(wolfRender,new Vector2(playerController.getPosition().x-7,playerController.getPosition().y-16),1f,1f);
                if(wolfRender.getCollider().overlaps(playerController.getCollider())) {

                }
            }
        }

    }


    public void movelocation(WolfRender a, Vector2 b) {
        movelocation(a, b, 10f,10f);
    }


    public void movelocation(WolfRender a, Vector2 b, float sox,float soy) {
        float deltaTime = Gdx.graphics.getDeltaTime();
        if (Math.abs(a.getlocation().x - b.x) > sox) {
            if (a.getlocation().x > b.x) {
                a.getlocation().x -= 10f * deltaTime;
                a.setLocation(a.getlocation().x, a.getlocation().y);
                a.getCollider().setPosition(a.getlocation().x, a.getlocation().y);
                a.setCrencurrentState(PetState.WALK_LEFT);
            } else if (a.getlocation().x < b.x) {
                a.getlocation().x += 10f * deltaTime;
                a.setLocation(a.getlocation().x, a.getlocation().y);
                a.getCollider().setPosition(a.getlocation().x, a.getlocation().y);
                a.setCrencurrentState(PetState.WALK_RIGHT);
            }
        } else if (Math.abs(a.getlocation().y - b.y) > soy) {
            if (a.getlocation().y > b.y) {
                a.getlocation().y -= 10f * deltaTime;
                a.setLocation(a.getlocation().x, a.getlocation().y);
                a.getCollider().setPosition(a.getlocation().x, a.getlocation().y);
                a.setCrencurrentState(PetState.WALK_FACE);
            } else if (a.getlocation().y < b.y) {
                a.getlocation().y += 10f * deltaTime;
                a.setLocation(a.getlocation().x, a.getlocation().y);
                a.getCollider().setPosition(a.getlocation().x, a.getlocation().y);
                a.setCrencurrentState(PetState.WALK_BACK);
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
