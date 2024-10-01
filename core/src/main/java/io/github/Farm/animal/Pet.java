package io.github.Farm.animal;

import com.badlogic.gdx.math.Vector2;
import io.github.Farm.Plants.PlantType;

public class Pet {
    private Vector2 location;
    private long hungry;
    private long reproduction;
    private long quantity;
    private boolean killed;

    public Pet(Vector2 location, long hungry,long reproduction,long quantity,boolean killed){
        this.quantity=quantity;
        this.hungry=hungry;
        this.reproduction=reproduction;
        this.killed=killed;
        this.location=location;
    }
    public Vector2 getlocation(){return location;}
    public void setLocation(float tmpx,float tmpy){this.location=new Vector2(tmpx,tmpy);}
    public void sethungry(long a){
        hungry-=a;
    }
    public long gethungry(){return hungry;}
    public void setReproduction(long a){
        reproduction=a;
    }
    public long getReproduction(){return reproduction;}
    public void setKilled(boolean a){
        killed=a;
    }
    public void eat(PlantType input){
         switch (input){
             case  TOMATO:
                 sethungry(gethungry()+10);
                 break;
             case POTATO:
                 sethungry(gethungry()+20);
                 break;
             case CARROT:
                 sethungry(gethungry()+50);
                 break;
             default:
                 break;
         }
    }

}
