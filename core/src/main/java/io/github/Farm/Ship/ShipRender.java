package io.github.Farm.Ship;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import io.github.Farm.ui.Other.Task;
import com.badlogic.gdx.utils.Array;

public class ShipRender {
    private Ship ship;
    private Task task;

    private enum ShipState {
        IDLE_LEFT, IDLE_RIGHT, MOVE_LEFT, MOVE_RIGHT, DIE
    }

    private ShipState currentState;
    private final Animation<TextureRegion>[] animations;
    private float stateTime;

    public ShipRender(Ship ship){
        this.ship=ship;
        this.task =new Task();

        this.animations = new Animation[5];
        this.stateTime = 0f;
        this.currentState = ShipState.IDLE_LEFT;

        animations[ShipState.IDLE_LEFT.ordinal()] = loadAnimation("ship/ship", 8, 0.2f);
//        animations[ShipState.IDLE_RIGHT.ordinal()] = loadAnimation("ship/idle_right_", 6, 0.1f);
//        animations[ShipState.MOVE_LEFT.ordinal()] = loadAnimation("ship/move_left_", 6, 0.1f);
//        animations[ShipState.MOVE_RIGHT.ordinal()] = loadAnimation("ship/move_right_", 6, 0.1f);
//        animations[ShipState.DIE.ordinal()] = loadAnimation("ship/die_", 6, 0.15f);
    }




    public void render(SpriteBatch batch, Camera camera){

        stateTime+= Gdx.graphics.getDeltaTime();
        if(ship.isNearPlayer()){
            task.render(batch,camera);
        }
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        TextureRegion currentFrame = animations[currentState.ordinal()].getKeyFrame(stateTime, true);
        batch.draw(currentFrame, 3900, 900,currentFrame.getRegionWidth()/4f,currentFrame.getRegionHeight()/4f);
        batch.end();

    }


    private Animation<TextureRegion> loadAnimation(String pathPrefix, int frameCount, float frameDuration) {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 0; i < frameCount; i++) {
            frames.add(new TextureRegion(new Texture(pathPrefix + Integer.toString(i) + ".png")));
        }
        return new Animation<>(frameDuration, frames, Animation.PlayMode.LOOP);
    }

}
