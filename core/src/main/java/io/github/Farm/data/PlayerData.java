package io.github.Farm.data;

import com.badlogic.gdx.math.Vector2;

public class PlayerData {
    private float posX;
    private float posY;
    private float health;

    public PlayerData() {

    }

    public PlayerData(float posX, float posY, float health) {
        this.posX = posX;
        this.posY = posY;
        this.health = health;
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

