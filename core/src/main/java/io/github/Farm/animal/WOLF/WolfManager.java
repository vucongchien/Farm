package io.github.Farm.animal.WOLF;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.Farm.animal.PetManager;
import io.github.Farm.animal.PetState;
import io.github.Farm.player.PlayerController;
import io.github.Farm.ui.MainMenu;

import java.io.File;
import java.io.IOException;
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
    //........mảng dự tru
    private final ArrayList<WolfRender> storagewolfmanager;

    private final ArrayList< WolfRender> wolfmanager;

    private Vector2 home = new Vector2(0, 0);
    private long breedingTime;
    private long starttime = 0, endtime = 0;
    private Vector2 preyleader = new Vector2(0, 0);
    private boolean acttack;
    private float radius;
    private float angle;
    private  int fistcheck=2;
    private PetState currentState;
    private long timeacttacked;
    private final String link="animalData.json";



    public WolfManager() {
        if(MainMenu.isCheckcontinue()){
            System.out.println("yessir");
            wolfmanager = new ArrayList<>();
            storagewolfmanager = new ArrayList<>();
            home =new Vector2(850,850);
            readWolfManagerData(storagewolfmanager,wolfmanager);
        }else {
            wolfmanager = new ArrayList<>();
            storagewolfmanager = new ArrayList<>();
            home = new Vector2(850, 850);
            for (int i = 0; i < 5; i++) {
                radius = ThreadLocalRandom.current().nextFloat(20, 40);
                angle = (float) Math.toRadians(ThreadLocalRandom.current().nextFloat(-90, 90));
                float xOffset = (float) Math.cos(angle) * radius;
                float yOffset = (float) Math.sin(angle) * radius;
                storagewolfmanager.add(new WolfRender(new Vector2(home.x + xOffset, home.y + yOffset), false));
                storagewolfmanager.get(storagewolfmanager.size() - 1).setDistancefrombossx(xOffset);
                storagewolfmanager.get(storagewolfmanager.size() - 1).setDistancefrombossy(yOffset);
            }
        }
    }

    public void setBoss() {
        boolean checkboss = false;
        if (!wolfmanager.isEmpty()) {
            for (WolfRender x : wolfmanager) {
                if (x.getthulinh()) {
                    checkboss = true;
                }
            }
            if (!checkboss) {
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
            if (wolfremove.getHp().getCurrHp() <= 0) {
                iterator.remove();
                storagewolfmanager.add(wolfremove);
                wolfremove.reset(home);
            }
        }
    }

    public void update(PetManager buffaloManager, PlayerController playerController) {
        setBoss();
        checkquantity();
        activate(buffaloManager);
        for(WolfRender x:wolfmanager){
            x.checkHit(playerController);
            x.update(Gdx.graphics.getDeltaTime());

        }
        attackplayer(playerController);
    }

    public void activate(PetManager buffaloManager) {
        if(!acttack) {
            timeacttacked=0;
            for (WolfRender wolf : wolfmanager) {
                wolf.getSpeak().setCurrent(null,8,8);
                if (wolf.getthulinh()) {
                    if (Math.abs(wolf.getlocation().x - home.x) < 1f && Math.abs(wolf.getlocation().y - home.y) < 1f) {
                        if (starttime == 0) {
                            starttime = TimeUtils.millis();
                        }
                        if (TimeUtils.timeSinceMillis(starttime) < 20000) {
                            wolf.setcheck(0);
                            wolf.setCrencurrentState(PetState.IDLE_RIGHT);
                        } else {
                            wolf.setprey(buffaloManager);
                            preyleader = wolf.getPrey().location().cpy().add(50, 50);
                            wolf.setcheck(1);
                            fistcheck=wolf.getcheck();
                        }
                    } else {
                        starttime = 0;
                        if (wolf.getcheck() == 2) {
                            movelocation(wolf, home, 1f, 1f,0.021f);
                            if (Math.abs(wolf.getlocation().x - home.x) < 1f && Math.abs(wolf.getlocation().y - home.y) < 1f) {
                                wolf.setprey(buffaloManager);
                                preyleader = wolf.getPrey().location().cpy().add(50, 50);
                            }
                        }
                    }
                    if (preyleader.x != 0 || preyleader.y != 0) {
                        if (Math.abs(wolf.getlocation().x - preyleader.x) < 10f && Math.abs(wolf.getlocation().y - preyleader.y) < 10f) {
                            if (endtime == 0) {
                                endtime = TimeUtils.millis();
                                wolf.setTrangthaitancong(true);
                            }
                            if (TimeUtils.timeSinceMillis(endtime) < 20000) {
                                wolf.setCrencurrentState(PetState.IDLE_RIGHT);
                                wolf.getSpeak().setCurrent("UI/other/expression_chat.png",8,8);
                                wolf.setcheck(0);
                            } else {
                                wolf.getSpeak().setCurrent(null,8,8);
                                wolf.setTrangthaitancong(false);
                                wolf.setcheck(2);
                                fistcheck=wolf.getcheck();
                            }
                        } else {
                            endtime = 0;
                            if (wolf.getcheck() == 1) {
                                movelocation(wolf, preyleader);
                            }
                        }
                    }

                } else {
                    Vector2 bossLocation = null;
                    boolean bossAttack = false;
                    bossLocation = wolfmanager.get(0).getlocation().cpy();
                    bossAttack = wolfmanager.get(0).gettrangthaitancon();
                    wolf.setprey(buffaloManager);
                    wolf.checkCoer(wolf.getPrey());
                    if (bossLocation != null) {
                        if (!bossAttack || wolf.getPrey() == null) {
                                wolf.setCrencurrentState(PetState.IDLE_RIGHT);
                                movelocation(wolf, new Vector2(bossLocation.x + wolf.getdistancefrombossx(), bossLocation.y + wolf.getDistancefrombossy()), 1f, 1f,0.021f);
                        } else {
                            suppotativate(wolf,wolf.getPrey().location().cpy().add((float) Math.cos(wolf.getRadius()) * 20,(float) Math.sin(wolf.getRadius()) * 20));
                        }
                    } else {
                        wolf.setCrencurrentState(PetState.IDLE_RIGHT);
                    }
                }
            }
        }
    }

    public void suppotativate(WolfRender wolf,Vector2 prey){
            if(wolf.getlocation().epsilonEquals(prey,5f)){
            wolf.getPrey().setCheckmove(true);
            if(wolf.getTimeActack() <0.32f){
                wolf.setTimeActack(wolf.getTimeActack() + Gdx.graphics.getDeltaTime());
                if(wolf.getPrey().location().x<wolf.getlocation().x) {
                    wolf.setCrencurrentState(PetState.ATTACK_LEFT);
                }else{
                    wolf.setCrencurrentState(PetState.ATTACK_RIGHT);
                }
            }else {
                wolf.getPrey().getmau().damaged(20);
                Vector2 wolfPosition = wolf.getlocation();
                Vector2 direction = new Vector2(wolf.getPrey().location()).sub(wolfPosition).nor();
                float knockbackForce = 200f;
                wolf.getPrey().setKnockbackVelocity(direction.scl(knockbackForce));
                wolf.getPrey().setKnockbackDuration(0.2f);
            }
        }else{
            wolf.getPrey().setCheckmove(false);
                movelocation(wolf,prey, 1f, 1f, 0.021f);
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
                if(timeacttacked==0){
                    timeacttacked=TimeUtils.millis();
                }
                if(TimeUtils.timeSinceMillis(timeacttacked)<500){
                    if(wolfRender.getlocation().x<playerController.getPosition().x){
                        wolfRender.setCrencurrentState(PetState.IDLE_RIGHT);
                    }else{
                        wolfRender.setCrencurrentState(PetState.IDLE_LEFT);
                    }
                    wolfRender.getSpeak().setCurrent("UI/other/expression_alerted.png",3,8);

                }else {
                    wolfRender.getSpeak().setCurrent(null,3,8);
                    if (wolfRender.getlocation().x > playerController.getPosition().x) {
                        if (Math.abs(wolfRender.getlocation().x - playerController.getPosition().x) <= 10f && Math.abs(wolfRender.getlocation().y - playerController.getPosition().y) <= 10f) {
                            wolfRender.setCrencurrentState(PetState.ATTACK_LEFT);
                            wolfRender.setKill(true);
                            if (wolfRender.getTimeActack() < 0.32f) {
                                wolfRender.setTimeActack(wolfRender.getTimeActack() + Gdx.graphics.getDeltaTime());
                            } else {
                                playerController.setHurt(true);
                                playerController.setEnemyDirection("RIGHT");
                            }

                            wolfRender.setanimationattack(true);
                            while (wolfRender.getcooldown() > 0) {
                                wolfRender.setcooldown();
                            }
                        } else {
                            movelocation(wolfRender, playerController.getPosition(), 10f, 10f, 0.021f);
                            wolfRender.setKill(false);
                            wolfRender.recoverycooldown();
                        }
                    } else {
                        if (Math.abs(wolfRender.getlocation().x - playerController.getPosition().x) <= 10f && Math.abs(wolfRender.getlocation().y - playerController.getPosition().y) <= 10f) {
                            wolfRender.setCrencurrentState(PetState.ATTACK_RIGHT);
                            wolfRender.setKill(true);
                            if (wolfRender.getTimeActack() < 0.32f) {
                                wolfRender.setTimeActack(wolfRender.getTimeActack() + Gdx.graphics.getDeltaTime());
                            } else {
                                playerController.setHurt(true);
                                playerController.setEnemyDirection("LEFT");
                            }
                            while (wolfRender.getcooldown() > 0) {
                                wolfRender.setcooldown();
                            }
                        } else {
                            wolfRender.setKill(false);
                            movelocation(wolfRender, playerController.getPosition(), 10f, 10f, 0.021f);
                            wolfRender.recoverycooldown();
                        }
                    }
                }
            }
            acttack = false;
            if(!wolfmanager.isEmpty()) {
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
        movelocation(a, b, 10f,10f,0.021f);
    }


    public void movelocation(WolfRender a, Vector2 b, float sox,float soy,float deltaTime) {
        if(!a.isCheckhurt()) {
            if (Math.abs(a.getlocation().x - b.x) > sox) {
                if (a.getlocation().x > b.x) {
                    a.getlocation().x -= 20f * deltaTime;
                    a.setLocation(a.getlocation().x, a.getlocation().y);
                    a.getCollider().setPosition(a.getlocation().cpy().x - 5, a.getlocation().cpy().y);
                    a.setCrencurrentState(PetState.WALK_LEFT);
                    currentState = a.getCrencurrentState();
                } else if (a.getlocation().x < b.x) {
                    a.getlocation().x += 20f * deltaTime;
                    a.setLocation(a.getlocation().x, a.getlocation().y);
                    a.getCollider().setPosition(a.getlocation().cpy().x - 5, a.getlocation().cpy().y);
                    a.setCrencurrentState(PetState.WALK_RIGHT);
                    currentState = a.getCrencurrentState();
                }
            }
            if (Math.abs(a.getlocation().y - b.y) > soy) {
                if (a.getlocation().y > b.y) {
                    a.getlocation().y -= 20f * deltaTime;
                    a.setLocation(a.getlocation().x, a.getlocation().y);
                    a.getCollider().setPosition(a.getlocation().cpy().x - 5, a.getlocation().cpy().y);
                    a.setCrencurrentState(currentState);
                } else if (a.getlocation().y < b.y) {
                    a.getlocation().y += 20f * deltaTime;
                    a.setLocation(a.getlocation().x, a.getlocation().y);
                    a.getCollider().setPosition(a.getlocation().cpy().x - 5, a.getlocation().cpy().y);
                    a.setCrencurrentState(currentState);
                }
            }
        }else{
            if(a.getTimehurt()>0.1f){
                a.setTimehurt(a.getTimehurt()-Gdx.graphics.getDeltaTime());
            }else{
                a.setTimehurt(0.32f);
                a.setCheckhurt(false);
            }

        }

    }

    public ArrayList<WolfRender> getwolfmanafer() {
        return (wolfmanager);
    }

    public ArrayList<WolfRender> getStoragewolfmanager() {
        return (storagewolfmanager);
    }

    public void dispose(){
        for(WolfRender wolfRender:storagewolfmanager){
            wolfRender.dispose();
        }
        for(WolfRender wolfRender:wolfmanager){
            wolfRender.dispose();
        }
    }

    public void readWolfManagerData(ArrayList<WolfRender> storagewolf,ArrayList<WolfRender> wolf){
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(new File(link));
            JsonNode storageWolfArray = rootNode.get("storageWolfRenders");
            if(!storageWolfArray.isEmpty()) {
                for (JsonNode storageWolfNode : storageWolfArray) {
                    float posX = (float) storageWolfNode.get("posX").asDouble();
                    float posY = (float) storageWolfNode.get("posY").asDouble();
                    float hp = (float) storageWolfNode.get("health").asDouble();
                    boolean leader =storageWolfNode.get("leader").asBoolean();
                    float distancefrombossx=(float) storageWolfNode.get("distancefrombossx").asDouble();
                    float distancefrombossy=(float) storageWolfNode.get("distancefrombossy").asDouble();
                    WolfRender wolfRender1 = new WolfRender(new Vector2(posX, posY),leader);
                    wolfRender1.getHp().setCurrHp(hp);
                    wolfRender1.setDistancefrombossx(distancefrombossx);
                    wolfRender1.setDistancefrombossy(distancefrombossy);
                    storagewolf.add(wolfRender1);
                }
            }
            JsonNode wolfArray = rootNode.get("wolfRenders");
            if(!wolfArray.isEmpty()) {
                for (JsonNode wolfNode : wolfArray) {
                    float posX = (float) wolfNode.get("posX").asDouble();
                    float posY = (float) wolfNode.get("posY").asDouble();
                    float hp = (float) wolfNode.get("health").asDouble();
                    boolean leader =wolfNode.get("leader").asBoolean();
                    float distancefrombossx=(float) wolfNode.get("distancefrombossx").asDouble();
                    float distancefrombossy=(float) wolfNode.get("distancefrombossy").asDouble();
                    WolfRender wolfRender = new WolfRender(new Vector2(posX, posY),leader);
                    wolfRender.getHp().setCurrHp(hp);
                    wolfRender.setDistancefrombossx(distancefrombossx);
                    wolfRender.setDistancefrombossy(distancefrombossy);
                    wolf.add(wolfRender);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi đọc file JSON", e);
        }

    }
}
