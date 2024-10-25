package io.github.Farm.animal.Buffalo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.Farm.animal.PetState;
import io.github.Farm.player.PlayerController;
import io.github.Farm.ui.MainMenu;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class BuffaloManager  {
    private ArrayList<Buffalo> buffaloManager;
    private long breedingTime;
    private long hptime=0;
    private final String link="animalData.json";

    private static BuffaloManager buffalomanager;

    public static BuffaloManager getbuffalomanager(){
        if(buffalomanager==null){
            buffalomanager=new BuffaloManager();
        }
        return buffalomanager;
    }

    public BuffaloManager(){
        if(MainMenu.isCheckcontinue()){
            System.out.println("doc oke");
            buffaloManager=new ArrayList<>();
            readBuffaloData(buffaloManager);
        }else {
            buffaloManager = new ArrayList<>();
        }
    }

    public  void checkquantity(){
        if (buffaloManager.size() < 2) {
            if (breedingTime == 0) {
                breedingTime = TimeUtils.millis();
            }
            if (TimeUtils.timeSinceMillis(breedingTime) > 2000) {
                buffaloManager.add(new Buffalo(new Vector2(2300,1500),100));
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
        for(Buffalo buffalo:buffaloManager){
            for(Buffalo buffalo1:buffaloManager){
                if(buffalo!=buffalo1){
                    buffalo.collide(buffalo,buffalo1);
                }
            }
            buffalo.ativate(buffalo,500,650,950,1050);
            buffalo.update(Gdx.graphics.getDeltaTime());
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

    public void readBuffaloData(ArrayList<Buffalo> buffalo) {
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            JsonNode rootNode = objectMapper.readTree(new File(link));
            JsonNode buffaloArray = rootNode.get("buffalo");
            for (JsonNode buffaloNode : buffaloArray) {
                float posX = (float) buffaloNode.get("posX").asDouble();
                float posY = (float) buffaloNode.get("posY").asDouble();
                float hp = (float) buffaloNode.get("hp").asDouble();
                float hungry = (float) buffaloNode.get("hungry").asDouble();
                Buffalo buffalo1=new Buffalo(new Vector2(posX,posY),(long) hungry);
                buffalo1.getHeath().setCurrHp(hp);
                buffalo.add(buffalo1);
            }

        } catch (IOException e) {
            throw new RuntimeException("Lỗi khi đọc file JSON", e);
        }
    }

}
