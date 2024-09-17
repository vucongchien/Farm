package io.github.Farm.player;

import com.badlogic.gdx.math.Rectangle;

public interface Collider {
    Rectangle getCollider();
    void onCollision(Collider other);
}

