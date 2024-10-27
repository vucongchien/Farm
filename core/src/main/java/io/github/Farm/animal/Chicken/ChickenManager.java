package io.github.Farm.animal.Chicken;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.Farm.player.PlayerController;
import io.github.Farm.ui.MainMenu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class ChickenManager {
    private ArrayList<ChickenRender> chickenManager;
    private long breedingTime;
    private long hptime=0;
    private final String link="animalData.json";

    private static ChickenManager chickenmanager;

    public static ChickenManager getChickenmanager(){
        if(chickenmanager ==null){
            chickenmanager =new ChickenManager();
        }
        return chickenmanager;
    }

    public ChickenManager(){
        chickenManager =new ArrayList<>();
        if(MainMenu.isCheckcontinue()){
            readChickeManagernData(chickenManager);
        }
    }

    public  void checkquantity(){
        if (chickenManager.size() < 2) {
            if (breedingTime == 0) {
                breedingTime = TimeUtils.millis();
            }
            if (TimeUtils.timeSinceMillis(breedingTime) > 2000) {
                chickenManager.add(new ChickenRender(new Vector2(2500,1700),100));
                breedingTime = 0;
            }
        }
        Iterator<ChickenRender> iterator = chickenManager.iterator();
        while (iterator.hasNext()) {
            ChickenRender chicken = iterator.next();
            if (chicken.getmau().getCurrHp() <= 0) {
                iterator.remove();
            }
        }
    }

    public void checkHungry(){
        if(hptime==0){
            hptime=TimeUtils.millis();
        }
        if(TimeUtils.timeSinceMillis(hptime) > 5000){
            for(ChickenRender chicken: chickenManager) {
                chicken.sethungry(20);
                if(chicken.gethungry()>50){
                    chicken.getChatbox().setCurrent("UI/other/happiness_01.png",7/2,8/2);
                }else{

                    chicken.getChatbox().setCurrent("UI/Item/SEED/wheat.png",7/2,8/2);
                    if(chicken.gethungry()<=0){
                        chicken.getChatbox().setCurrent("UI/other/happiness_04.png",7/2,8/2);
                    }
                }
            }
            hptime=0;
        }

    }

    public void update(PlayerController playerController){
        checkquantity();
        checkHungry();
        for(ChickenRender chicken: chickenManager){
            for(ChickenRender chicken1: chickenManager){
                if(chicken!=chicken1){
                    chicken.collide(chicken,chicken1);
                }
            }
            chicken.ativate(chicken,2500,2650,1700,1800);
            chicken.update(Gdx.graphics.getDeltaTime());
        }
    }


    public ArrayList<ChickenRender> getChickenManager(){
        return (chickenManager);
    }

    public void dispose() {
        for (ChickenRender chicken : chickenManager) {
            chicken.dispose();
        }
    }

    public void readChickeManagernData(ArrayList<ChickenRender> chickenManager) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(new File(link));
            JsonNode chickenArray = rootNode.get("chicken");
            for (JsonNode chickenNode : chickenArray) {
                float posX = (float) chickenNode.get("posX").asDouble();
                float posY = (float) chickenNode.get("posY").asDouble();
                float hp = (float) chickenNode.get("hp").asDouble();
                float hungry = (float) chickenNode.get("hungry").asDouble();
                ChickenRender chicken=new ChickenRender(new Vector2(posX,posY),(long) hungry);
                chicken.getHeath().setCurrHp(hp);
                chickenManager.add(chicken);
            }
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi đọc file JSON", e);
        }
    }

}
