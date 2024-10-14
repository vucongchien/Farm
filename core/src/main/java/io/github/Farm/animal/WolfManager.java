package io.github.Farm.animal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import io.github.Farm.animal.Buffalo.Buffalo;
import io.github.Farm.animal.Buffalo.BuffaloManager;

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
    private ArrayList<WolfRender> wolfmanager;

    private Vector2 home = new Vector2(0, 0);
    private long breedingTime;
    private int check = 0;
    private long starttime = 0, endtime = 0;
    private Vector2 preyleader = new Vector2(0, 0);
    //    private float deltaTime=Gdx.graphics.getDeltaTime();
    private Vector2 tamthoidecheck=null;
    private float radius;
    private float angle;

    public WolfManager() {
        wolfmanager = new ArrayList<>();

    }

    public void sethome() {
        for (WolfRender x : wolfmanager) {
            if (x.getthulinh() == true) {
                if (home.equals(new Vector2(0, 0))) {
                    home = x.getlocation().cpy();
                }
            }

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
            }
        }
    }

//    @Override
    public void checkquantity() {
        radius = ThreadLocalRandom.current().nextFloat(20, 40);
        angle = (float) Math.toRadians(ThreadLocalRandom.current().nextFloat(-90, 90));
        float xOffset = (float) Math.cos(angle) * radius;
        float yOffset = (float) Math.sin(angle) * radius;
        for(WolfRender wolf:wolfmanager) {
            if(wolf.getthulinh()==true){
                tamthoidecheck=wolf.getlocation().cpy();
                break;
            }
        }
        if(tamthoidecheck!=null) {
            if (wolfmanager.size() < 5) {
                if (breedingTime == 0) {
                    breedingTime = TimeUtils.millis();
                }
                if (TimeUtils.timeSinceMillis(breedingTime) > 2000) {
                    wolfmanager.add(new WolfRender(new Vector2(home.x + xOffset, home.y + yOffset), false));
                    wolfmanager.get(wolfmanager.size()-1).setDistancefrombossx(xOffset);
                    wolfmanager.get(wolfmanager.size()-1).setDistancefrombossy(yOffset);
                    breedingTime = 0;
                }
            }
        }else{
            if (breedingTime == 0) {
                breedingTime = TimeUtils.millis();
            }
            if (TimeUtils.timeSinceMillis(breedingTime) > 2000) {
                wolfmanager.add(new WolfRender(new Vector2(900,950), false));
                breedingTime = 0;
            }
        }
        Iterator<WolfRender> iterator = wolfmanager.iterator();
        while (iterator.hasNext()) {
            WolfRender wolf = iterator.next();
            if (wolf.gethp() <= 0) {
                System.out.println("xoa");
                iterator.remove();
            }
        }
    }

    public void update(BuffaloManager buffaloManager) {
        checkquantity();
        activate(buffaloManager);
        sethome();
        setBoss();
    }

    public void activate(BuffaloManager buffaloManager) {
        for (WolfRender wolf : wolfmanager) {
            if (wolf.getthulinh() == true) {
                System.out.println("con");
//                System.out.println(preyleader);
//                if (preyleader.x == 0 || preyleader.y == 0 || preyleader.x == 50 || preyleader.y == 50) {
//                    wolf.setprey(buffaloManager);
//                    preyleader = wolf.getPrey().cpy().add(50, 50);
//
//                }

                if (Math.abs(wolf.getlocation().x - home.x) < 1f&&Math.abs(wolf.getlocation().y - home.y) < 1f) {
                    if (starttime == 0) {
                        starttime = TimeUtils.millis();
                    }
                    if (TimeUtils.timeSinceMillis(starttime) < 10000) {
                        check = 0;
                        wolf.setCrencurrentState(PetState.IDLE_RIGHT);
                    } else {
                        wolf.setprey(buffaloManager);
                        preyleader = wolf.getPrey().cpy().add(50, 50);
                        starttime = 0;
                        check = 1;
                    }
                } else {
                    starttime = 0;
                }
                if (preyleader.x != 50 || preyleader.y != 50) {
                    if (Math.abs(wolf.getlocation().x - preyleader.x) < 10f&&Math.abs(wolf.getlocation().y - preyleader.y) < 10f) {
//                        System.out.println("den dich");
                        if (endtime == 0) {
                            endtime = TimeUtils.millis();
                            wolf.setTrangthaitancong(true);
                        }
                        if (TimeUtils.timeSinceMillis(endtime) < 10000) {
                            wolf.setCrencurrentState(PetState.IDLE_RIGHT);
                            check = 0;
                        } else {
                            wolf.setTrangthaitancong(false);
                            check = 2;
                            endtime = 0;
                        }
                    } else {
                        endtime = 0;
                    }

                    if (check == 1) {
                        movelocation(wolf, preyleader);
                    }
                }
                if (check == 2) {
                    movelocation(wolf, home,1f,1f);
                    if(Math.abs(wolf.getlocation().x - home.x) < 1f&&Math.abs(wolf.getlocation().y - home.y) < 1f) {
                        preyleader = new Vector2(0, 0);
                    }
                }
            } else {
                Vector2 bossLocation = null;
                boolean bossAttack = false;
                if (wolfmanager.get(0).getthulinh() == true) {
                    bossLocation = wolfmanager.get(0).getlocation().cpy();
                    bossAttack = wolfmanager.get(0).gettrangthaitancon();
                }
                wolf.setprey(buffaloManager);
                if (bossLocation != null) {
                    if (bossAttack == false || wolf.getPrey().x == 0) {
                        if(Math.abs(bossLocation.x-home.x)<1f&&Math.abs(bossLocation.y-home.y)<1f){
                            wolf.setCrencurrentState(PetState.IDLE_RIGHT);
                            movelocation(wolf,new Vector2(bossLocation.x+wolf.getdistancefrombossx(),bossLocation.y+wolf.getDistancefrombossy()),1f,1f);
                        }else {

                            if (Math.abs(wolf.getlocation().x - bossLocation.x) < Math.abs(wolf.getdistancefrombossx()) && Math.abs(wolf.getlocation().y - bossLocation.y) < Math.abs(wolf.getDistancefrombossy())) {
                                wolf.setCrencurrentState(PetState.IDLE_RIGHT);
//                            System.out.println(wolf.getCrencurrentState() + "1");
                            } else {
                                movelocation(wolf, bossLocation, Math.abs(wolf.getdistancefrombossx()), Math.abs(wolf.getDistancefrombossy()));
//                            System.out.println(wolf.getCrencurrentState() + "2");
                            }
                        }
                    } else {
                        for (Buffalo d : buffaloManager.getBuffaloManager()) {
                            float deltaX = wolf.getlocation().x - d.getlocation().x;
                            float deltaY = wolf.getlocation().y - d.getlocation().y;
                            float overlapX = (wolf.getCollider().width / 2 + d.getbox().width / 2) - Math.abs(deltaX);
                            float overlapY = (wolf.getCollider().height / 2 + d.getbox().height / 2) - Math.abs(deltaY);
                            if (overlapX > 0 && overlapY > 0) {
                                d.setmau();
                                float pushFactor = 5f;
                                if (Math.abs(overlapX) < Math.abs(overlapY)) {
                                    if (deltaX > 0) {
                                        d.getlocation().x += overlapX * pushFactor;
                                    } else {
                                        d.getlocation().x -= overlapX * pushFactor;
                                    }
                                } else {
                                    if (deltaY > 0) {
                                        d.getlocation().y += overlapY * pushFactor;
                                    } else {
                                        d.getlocation().y -= overlapY * pushFactor;
                                    }
                                }
                                d.setLocation(d.getlocation().x, d.getlocation().y);
                                d.setBox(d.getlocation().x, d.getlocation().y);
                            }
                        }
                        movelocation(wolf, wolf.getPrey());
                    }
                } else {
                    wolf.setCrencurrentState(PetState.IDLE_RIGHT);
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
        for(WolfRender wolfRender:wolfmanager){
            wolfRender.dispose();
        }
    }
}
