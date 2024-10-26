package io.github.Farm.data;

import com.badlogic.gdx.math.Vector2;

public class PlayerData {
    private float posX;
    private float posY;
    private float health;
    private float stamina;

    public PlayerData() {

    }

    public PlayerData(float posX, float posY, float health,float stamina) {
        this.posX = posX;
        this.posY = posY;
        this.health = health;
        this.stamina=stamina;
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

    public float getStamina() {
        return stamina;
    }

    public void setStamina(float stamina) {
        this.stamina = stamina;
    }
}

