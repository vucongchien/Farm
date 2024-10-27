package io.github.Farm.Ship;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import io.github.Farm.ui.Other.Task;

public class ShipRender {
    private Texture texture;
    private Ship ship;
    private Task task;

    public ShipRender(Ship ship){
        this.texture=new Texture("ship/ship_Idle_left_00.png");
        this.ship=ship;
        this.task =new Task();
    }


    public void render(SpriteBatch batch, Camera camera){
        if(ship.isNearPlayer()){
            task.render(batch,camera);
        }
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(texture,3900,900,60,40);
        batch.end();

    }
}
