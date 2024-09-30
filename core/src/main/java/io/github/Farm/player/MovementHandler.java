package io.github.Farm.player;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;

/// XU LY CAC VAT LY LIEN QUAN DEN CHUYEN DONG ///

public class MovementHandler {
    private World world;
    private Body body;

    public MovementHandler(World world, Body body) {
        this.world = world;
        this.body = body;
    }

    public void moveCharacter(Vector2 movement, float speed) {
        body.setLinearVelocity(movement.scl(speed));
    }

    public Vector2 getPosition() {
        return body.getPosition();
    }

    public void updateColliderPosition(Rectangle collider, Vector2 position) {
        collider.setPosition(position);
    }

    public Body getBody() {
        return body;
    }

    public World getWorld() {
        return world;
    }
}
