package io.github.Farm.animal;

import com.badlogic.gdx.math.Vector2;
import io.github.Farm.Interface.Heath;

import java.util.logging.Handler;

public class Pet {
    private Vector2 location;
    private long hungry;
    private final Heath heath;

    public Pet(Vector2 location, long hungry,int a) {
        heath=new Heath(a);
        this.hungry = hungry;
        this.location = location;
    }

    public Vector2 getlocation() {
        return location;
    }

    public void setLocation(float tmpx, float tmpy) {
        this.location = new Vector2(tmpx, tmpy);
    }

    public void sethungry(long a) {
        hungry -= a;
    }

    public long gethungry() {
        return hungry;
    }

    public Heath getHeath() {
        return heath;
    }

}


