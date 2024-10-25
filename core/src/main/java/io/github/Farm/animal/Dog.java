package io.github.Farm.animal;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.Farm.Interface.Collider;

public class Dog extends Pet {
    public Dog(Vector2 postision, long hungry,long reproduction,long quantity){
        super(postision,hungry,100);
    }

    @Override
    public Rectangle getCollider() {
        return null;
    }

    @Override
    public void onCollision(Collider other) {

    }
}
