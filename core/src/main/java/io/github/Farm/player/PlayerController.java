package io.github.Farm.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;
import io.github.Farm.Gamemap;

public class PlayerController {
    private Vector2 position;
    private float speed;
    private PlayerState currentState = PlayerState.IDLE_RIGHT;
    private PlayerState lastState = PlayerState.IDLE_RIGHT;
    private boolean shoveling = false;
    private Gamemap map;

    public PlayerController(Vector2 startPosition, float speed,Gamemap map) {
        this.position = startPosition;
        this.speed = speed;
        this.map=map;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void update(float deltaTime) {
        handleInput();
        moveCharacter(deltaTime);
    }

    private void handleInput() {
        boolean moving = false;
        Vector2 movement = new Vector2();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            movement.y = 1;
            moving = true;
            currentState = PlayerState.valueOf("WALK_" + lastState.name().split("_")[1]);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            movement.y = -1;
            moving = true;
            currentState = PlayerState.valueOf("WALK_" + lastState.name().split("_")[1]);
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            movement.x = -1;
            lastState = PlayerState.WALK_LEFT;
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            movement.x = 1;
            lastState = PlayerState.WALK_RIGHT;
            moving = true;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            shoveling = true;
            currentState = PlayerState.valueOf("DIG_" + lastState.name().split("_")[1]);
            return;
        } else {
            shoveling = false;
        }


        if (moving) {
            currentState = (movement.x != 0) ? lastState : currentState;
        } else {
            currentState = PlayerState.getIdleState(lastState);
        }
    }

    private void moveCharacter(float deltaTime) {
        Vector2 movement = new Vector2();

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            movement.y = 1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            movement.y = -1;
        }

        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            movement.x = -1;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            movement.x = 1;
        }

        if (movement.len() > 1) {
            movement.nor();
        }

        position.add(movement.scl(speed * deltaTime));
    }

    public boolean isWalking() {
        return currentState.toString().startsWith("WALK");
    }

    public boolean isShoveling() {
        return shoveling;
    }

    public PlayerState getCurrentState() {
        return currentState;
    }

    public float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }

    public Gamemap getMap(){
        return map;
    }
}
