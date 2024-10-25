package io.github.Farm.animal;

import com.badlogic.gdx.math.Vector2;
import io.github.Farm.Plants.PlantManager;
import io.github.Farm.Plants.PlantRenderer;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

public class MouseManager {

    private static MouseManager mouseManager;
    public static MouseManager getmousemanager(){
        if(mouseManager==null){
            mouseManager= new MouseManager();
        }
        return mouseManager;
    }
    private static Map<Vector2,MouseRener> mousemanager;

    public MouseManager(){
        mousemanager=new HashMap<>();
    }



    public void checkquantity(){
        if(!PlantManager.getInstance().getListPlants().isEmpty()){

            for(PlantRenderer plantRenderer:PlantManager.getInstance().getListPlants()){
                if(plantRenderer.isHarvestable()){
                    if (!checktrung(mousemanager,plantRenderer.getPosition())){
                        float radius = ThreadLocalRandom.current().nextFloat(20, 40);
                        float angle = (float) Math.toRadians(ThreadLocalRandom.current().nextFloat(-90, 90));
                        float xOffset = (float) Math.cos(angle) * radius;
                        float yOffset = (float) Math.sin(angle) * radius;
                        mousemanager.put(plantRenderer.getPosition(),new MouseRener(new Vector2(plantRenderer.getPosition().x+xOffset,plantRenderer.getPosition().y+yOffset)));
                    }
                }
            }
        }
    }

    public boolean checktrung(Map<Vector2,MouseRener> a,Vector2 b){
        for (Vector2 key : a.keySet()) {
            if (key.epsilonEquals(b, 0.001f)) {
                return true;
            }
        }
        return false;
    }
}
