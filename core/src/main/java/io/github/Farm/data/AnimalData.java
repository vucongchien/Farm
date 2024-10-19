package io.github.Farm.data;

import com.badlogic.gdx.math.Vector2;
import io.github.Farm.animal.Buffalo.Buffalo;

import java.util.ArrayList;
import java.util.List;

public class AnimalData {
    private List<Wolf> wolfRenders;
    private List<Wolf> storageWolfRenders;
    private List<Wolf> buffalo;

    public AnimalData(){
        wolfRenders=new ArrayList<>();
        storageWolfRenders=new ArrayList<>();
        buffalo=new ArrayList<>();

    }

    public List<Wolf> getWolfRenders() {
        return wolfRenders;
    }

    public void setWolfRenders(List<Wolf> wolfRenders) {
        this.wolfRenders =new ArrayList<>(wolfRenders);
    }

    public List<Wolf> getStorageWolfRenders() {
        return storageWolfRenders;
    }

    public void setStorageWolfRenders(List<Wolf> storageWolfRenders) {
        this.storageWolfRenders = new ArrayList<>(storageWolfRenders);
    }

    public List<Wolf> getBuffalo() {
        return buffalo;
    }

    public void setBuffalo(List<Wolf> buffalo) {
        this.buffalo = new ArrayList<>(buffalo) ;
    }

    public static class Wolf {
        private float posX;
        private float posY;
        private float health;

        public Wolf(){}

        public Wolf(float posX,float posY,float health){
            this.health=health;
            this.posX=posX;
            this.posY=posY;
        }

        public Vector2 getPosition() {
            return new Vector2(posX, posY);
        }

        public void setPosition(Vector2 position) {
            this.posX = position.x;
            this.posY = position.y;
        }

        public float getHealth() {
            return health;
        }

        public void setHealth(float health) {
            this.health = health;
        }


    }
}
