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
        if (pigManager.size() < 2) {
            if (breedingTime == 0) {
                breedingTime = TimeUtils.millis();
            }
            if (TimeUtils.timeSinceMillis(breedingTime) > 2000) {
                pigManager.add(new PigReander(new Vector2(2150,1300),100));
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
                pig.sethungry(10);
            }
            hptime=0;
        }

    }

    public void update(PlayerController playerController){
        checkquantity();
        checkHungry();
        for(PigReander pig: pigManager){
            for(PigReander pig1: pigManager){
                if(pig!=pig1){
                    pig.collide(pig,pig1);
                }
            }
            pig.ativate(pig,2100,2200,1300,1400);
            pig.update(Gdx.graphics.getDeltaTime());
        }
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
