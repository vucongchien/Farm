package io.github.Farm.Ship;


import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.Farm.Interface.Collider;
import io.github.Farm.player.PlayerController;


public class Ship implements Collider {

    private Rectangle collider;
    private boolean isNearPlayer;
    private Vector2 position;



    public Ship(){
        position=new Vector2(3900,900);
        collider=new Rectangle(position.x, position.y, 50,50);

    }


    public void update(PlayerController playerController){
        checkCollision(playerController);
    }

    private void checkCollision(PlayerController player) {
        isNearPlayer= collider.overlaps(player.getCollider());
    }


    @Override
    public Rectangle getCollider() {
        return collider;
    }

    @Override
    public void onCollision(Collider other) {

    }

    public boolean isNearPlayer() {
        return isNearPlayer;
    }

    public void setNearPlayer(boolean nearPlayer) {
        isNearPlayer = nearPlayer;
    }

    public Vector2 getPosition() {
        return position;
    }

}
