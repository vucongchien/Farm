package io.github.Farm.data;

import com.badlogic.gdx.math.Vector2;
import io.github.Farm.animal.Buffalo.Buffalo;

import java.util.ArrayList;
import java.util.List;

public class AnimalData {
    private List<Wolf> wolfRenders;
    private List<Wolf> storageWolfRenders;
    private List<Animal> buffalo;
    private List<Animal> chicken;
    private List<Animal> pig;

    public AnimalData(){
        wolfRenders=new ArrayList<>();
        storageWolfRenders=new ArrayList<>();
        buffalo=new ArrayList<>();
        chicken=new ArrayList<>();
        pig=new ArrayList<>();

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

    public List<Animal> getBuffalo() {
        return buffalo;
    }

    public void setBuffalo(List<Animal> buffalo) {
        this.buffalo = new ArrayList<>(buffalo) ;
    }

    public List<Animal> getChicken() {
        return chicken;
    }

    public void setChicken(List<Animal> chicken){this.chicken=new ArrayList<>(chicken);}

    public List<Animal> getPig() {
        return pig;
    }

    public void setPig(List<Animal> pig){this.pig=new ArrayList<>(pig);}

    public static class Wolf {
        private float posX;
        private float posY;
        private float health;
        private boolean leader;
        private float   distancefrombossx;
        private float   distancefrombossy;

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


        public boolean isLeader() {
            return leader;
        }

        public void setLeader(boolean leader) {
            this.leader = leader;
        }

        public float getDistancefrombossx() {
            return distancefrombossx;
        }

        public void setDistancefrombossx(float distancefrombossx) {
            this.distancefrombossx = distancefrombossx;
        }

        public float getDistancefrombossy() {
            return distancefrombossy;
        }

        public void setDistancefrombossy(float distancefrombossy) {
            this.distancefrombossy = distancefrombossy;
        }
    }

    public static class Animal{
        private float posX;
        private float posY;
        private float hp;
        private float hungry;

        public void setHp(float hp) {
            this.hp = hp;
        }

        public void setHungry(float hungry) {
            this.hungry = hungry;
        }
        public Vector2 getPosition() {
            return new Vector2(posX, posY);
        }

        public void setPosition(Vector2 position) {
            this.posX = position.x;
            this.posY = position.y;
        }

        public Animal(){}

        public Animal(float posX,float posY,float hungry,float hp){
            this.hungry=hungry;
            this.posX=posX;
            this.posY=posY;
            this.hp=hp;
        }


    }
}
