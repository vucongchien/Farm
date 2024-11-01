package io.github.Farm.animal.Buffalo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.Farm.animal.PetState;
import io.github.Farm.animal.Pig.PigReander;
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
    private Vector2 startpoint;
    private final String link="animalData.json";

    private static BuffaloManager buffalomanager;

    public static BuffaloManager getbuffalomanager(){
        if(buffalomanager==null){
            buffalomanager=new BuffaloManager();
        }
        return buffalomanager;
    }

    public BuffaloManager(){
        buffaloManager=new ArrayList<>();
        if(MainMenu.isCheckcontinue()){
            readBuffaloData(buffaloManager);
        }
    }

    public  void checkquantity(){
        if (buffaloManager.size() < 2) {
            if (breedingTime == 0) {
                breedingTime = TimeUtils.millis();
            }
            if (TimeUtils.timeSinceMillis(breedingTime) > 2000) {
                buffaloManager.add(new Buffalo(new Vector2(2300,1300),100));
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
                    if(buffalo.gethungry()>=10) {
                        buffalo.sethungry(2);
                        if(buffalo.gethungry()>50){
                            buffalo.getChatbox().setCurrent("UI/other/happiness_01.png",7,8);
                        }else{

                            buffalo.getChatbox().setCurrent("UI/Item/FOOD/wheat.png",7,8);
                            if(buffalo.gethungry()<=0){
                                buffalo.getChatbox().setCurrent("UI/other/happiness_04.png",7,8);
                            }
                        }
                    }
                }
                hptime=0;
            }
    }

    public void update(PlayerController playerController){
        checkquantity();
        checkHungry();
        for (int i = 0; i < buffaloManager.size(); i++) {
            Buffalo buffalo = buffaloManager.get(i);
            for (int j = i + 1; j < buffaloManager.size(); j++) {
                Buffalo buffalo1 = buffaloManager.get(j);
                buffalo.collide(buffalo, buffalo1);
            }
            if(startpoint==null) {
                buffalo.ativate(buffalo, 2200, 2350, 1500, 1600);
            }else{
                buffalo.ativate(buffalo,(int)startpoint.x,(int)startpoint.cpy().x+100,(int)startpoint.y,(int)startpoint.cpy().y+100);
            }
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

    public Vector2 getStartpoint() {
        return startpoint;
    }

    public void setStartpoint(Vector2 startpoint) {
        this.startpoint = startpoint;
    }
}
