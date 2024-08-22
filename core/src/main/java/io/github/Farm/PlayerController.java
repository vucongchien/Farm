package io.github.Farm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Vector2;

import java.util.Vector;

public class PlayerController {
    private Vector2 position;
    private float speed;
    private Boolean facingRight=true;
    private float size;

    public PlayerController(Vector2 startPosition,float speed,float size){
        this.position=startPosition;
        this.speed=speed;
        this.size=size;
    }

    public Vector2 getPosition() {
        return position;
    }
    public float getSize(){
        return size;
    }
    public void update(float deltaTime){
        HandleInput(deltaTime);
    }
    private void HandleInput(float deltaTime){
        float moveAmount=speed*deltaTime;
        float moveX=0;
        float moveY=0;
        if(Gdx.input.isKeyPressed(Input.Keys.A)){
            moveX-=moveAmount;
            facingRight=false;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.D)){
            moveX+=moveAmount;
            facingRight=true;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.W)){
            moveY+=moveAmount;
        }
        if(Gdx.input.isKeyPressed(Input.Keys.S)){
            moveY-=moveAmount;
        }


        //fix di cheo Pytago
        if (moveX != 0 && moveY != 0) {
            float length = (float) Math.sqrt(moveX * moveX + moveY * moveY);
            moveX /= length;
            moveY /= length;
        }

        position.x += moveX * moveAmount;
        position.y += moveY * moveAmount;
    }
}
