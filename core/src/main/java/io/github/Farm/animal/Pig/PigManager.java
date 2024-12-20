package io.github.Farm.animal.Pig;

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

public class PigManager {
    private ArrayList<PigReander> pigManager;
    private long breedingTime;
    private long hptime=0;
    private static long timeCreate=0;
    private final String link="animalData.json";

    private static PigManager pigmanager;

    public static PigManager getPigmanager(){
        if(pigmanager ==null){
            pigmanager =new PigManager();
        }
        return pigmanager;
    }

    public PigManager(){
        pigManager =new ArrayList<>();
        if(MainMenu.isCheckcontinue()){
            readPigManagernData(pigManager);
        }
    }

    public  void checkquantity(){
        if (pigManager.size() < 3) {
            if (breedingTime == 0) {
                breedingTime = TimeUtils.millis();
            }
            if (TimeUtils.timeSinceMillis(breedingTime) > 5000) {
                pigManager.add(new PigReander(new Vector2(2150,1500),100));
                breedingTime = 0;
            }
        }
        Iterator<PigReander> iterator = pigManager.iterator();
        while (iterator.hasNext()) {
            PigReander pig = iterator.next();
            if (pig.getmau().getCurrHp() <= 0) {
                iterator.remove();
            }
        }
    }

    public void checkHungry(){
        if(hptime==0){
            hptime=TimeUtils.millis();
        }
        if(TimeUtils.timeSinceMillis(hptime) > 5000){
            for(PigReander pig: pigManager) {
                pig.sethungry(2);
                if(pig.gethungry()>50){
                    pig.getChatbox().setCurrent("UI/other/happiness_01.png",7,8);
                }else{
                    pig.getChatbox().setCurrent("UI/Item/FOOD/kale.png",7,8);
                    if(pig.gethungry()<=0){
                        pig.getChatbox().setCurrent("UI/other/happiness_04.png",7,8);
                    }
                }
            }
            hptime=0;
        }
    }

    public void update(PlayerController playerController){
        checkquantity();
        for (int i = 0; i < pigManager.size(); i++) {
            PigReander pig = pigManager.get(i);
            for (int j = i + 1; j < pigManager.size(); j++) {
                PigReander pig1 = pigManager.get(j);
                pig.collide(pig, pig1);
            }
            pig.ativate(pig,2100,2200,1500,1600);
            pig.update(Gdx.graphics.getDeltaTime());
        }
        checkHungry();
    }


    public ArrayList<PigReander> getPigManager(){
        return (pigManager);
    }

    public void dispose() {
        for (PigReander pig : pigManager) {
            pig.dispose();
        }
    }

    public void readPigManagernData(ArrayList<PigReander> PigManager) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(new File(link));
            JsonNode pigArray = rootNode.get("pig");
            for (JsonNode pigNode : pigArray) {
                float posX = (float) pigNode.get("posX").asDouble();
                float posY = (float) pigNode.get("posY").asDouble();
                float hp = (float) pigNode.get("hp").asDouble();
                float hungry = (float) pigNode.get("hungry").asDouble();
                PigReander pig=new PigReander(new Vector2(posX,posY),(long) hungry);
                pig.getHeath().setCurrHp(hp);
                PigManager.add(pig);
            }
        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi đọc file JSON", e);
        }
    }
}
