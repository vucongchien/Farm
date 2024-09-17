package io.github.Farm.player.lam_lai_file;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;
import io.github.Farm.Map.MapInteractionHandler;
import io.github.Farm.Plants.PlantManager;
import io.github.Farm.UI.Inventory;
import io.github.Farm.player.Collider;
import io.github.Farm.player.PlayerState;
import io.github.Farm.player.lam_lai_file.PLAYER_STAGE.IdleState;
import io.github.Farm.player.lam_lai_file.PLAYER_STAGE.PlayerStateManager;
import io.github.Farm.player.lam_lai_file.PLAYER_STAGE.PlayerStateee;
import io.github.Farm.player.lam_lai_file.PLAYER_STAGE.WalkState;

public class PlayerCotrollerr implements Collider, Disposable {
    private Vector2 position;
    private Vector2 positionInMap;
//    private PlayerState currentState = PlayerState.IDLE_RIGHT;
//    private PlayerState lastState = PlayerState.IDLE_RIGHT;
    private boolean isFacingRight = true;
    private boolean isOpenInventory = false;
    private boolean isFishing=false;

    //fishing
    private float TimeStartFish=0f;
    private float TimeToFishing=5f;

    private InputHandler inputHandler;
    private CollisionHandler collisionHandler;
    private MovementHandler movementHandler;
    private PlayerStateManager stateManager;

    //inventory
    private Inventory inventory;

    //collider
    private Rectangle collider;

    public PlayerCotrollerr(Vector2 startPosition, float speed, World world,
                            MapInteractionHandler mapInteractionHandler, PlantManager plantManager) {
        this.position=startPosition;
        this.positionInMap = new Vector2((int) (startPosition.x / 16), (int) (startPosition.y / 16));

        this.inputHandler = new InputHandler();
        this.collisionHandler = new CollisionHandler(plantManager, mapInteractionHandler);
        this.movementHandler = new MovementHandler(world, createBody(startPosition, world));
        this.stateManager = new PlayerStateManager(new IdleState("RIGHT"));

        this.inventory = new Inventory();
        this.collider=new Rectangle(position.x,position.y,16,16);
    }

    private Body createBody(Vector2 startPosition, World world) {
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(startPosition);
        Body body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(5, 5);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.1f;

        body.createFixture(fixtureDef);
        shape.dispose();
        return body;
    }

    public void update(float deltaTime) {
        //updatePlayerState(deltaTime);
        Vector2 movement = inputHandler.handleMovementInput();
        if(movement.x!=0){
            stateManager.changeState(this, new WalkState(isFacingRight ? "RIGHT" : "LEFT"));
        }
        stateManager.updateState(this,deltaTime);

        movementHandler.moveCharacter(movement, 1500f);  // Ví dụ về tốc độ
        position.set(movementHandler.getPosition());
        this.positionInMap = new Vector2(
            isFacingRight ? (int) (position.x / 16) +1 : (int) (position.x / 16) -1,
            (int) (position.y / 16)
        );

        collisionHandler.checkCollisions(collider);

        if (inputHandler.isPlowing()) {
            collisionHandler.handlePlowing(positionInMap);
        }

        if (inputHandler.isOpeningInventory()) {
            isOpenInventory = !isOpenInventory;
        }

    }

    public void changeState(PlayerStateee newState) {
        stateManager.changeState(this, newState);
    }

    public void setCurrentState(String state) {
        // Cập nhật trạng thái hiện tại của người chơi
    }
    public void updatePlayerState(float deltaTime){
        String LeftOrRight;
        Vector2 movement = inputHandler.handleMovementInput();

        if(movement.x>0)        { isFacingRight=true; }
        else if(movement.x<0)   { isFacingRight=false;}
        LeftOrRight=isFacingRight?"RIGHT":"LEFT";





        //input
//        if(inputHandler.isPlowing()){
//
//            //currentState=PlayerState.valueOf("DIG_"+LeftOrRight);
//        } else if (inputHandler.isHitting()) {
//            //currentState=PlayerState.valueOf("HIT_"+LeftOrRight);
//        } else if (inputHandler.isWatering()) {
//            //currentState=PlayerState.valueOf("WATER_"+LeftOrRight);
//        } else if (inputHandler.isCasting()) {
//            //currentState=PlayerState.valueOf("CASTING_"+LeftOrRight);
//            isFishing=true;
//            TimeStartFish=0f;
//        } else if(!isFishing) {
//            //currentState=PlayerState.valueOf((movement.x==0&&movement.y==0?"IDLE_":"WALK_")+LeftOrRight);
//        }

//        if(isFishing){
//            TimeStartFish+=deltaTime;
//            if(TimeStartFish>=TimeToFishing+0.4){
//                isFishing=false;
//                return;
//            }
//            if(TimeStartFish>=TimeToFishing){
//                currentState=PlayerState.valueOf("CAUGHT_"+LeftOrRight);
//                return;
//            }
//            if(TimeStartFish>=0.4){
//                currentState=PlayerState.valueOf("WAITING_"+LeftOrRight);
//            }
//        }

    }

    @Override
    public void dispose() {
        movementHandler.getWorld().destroyBody(movementHandler.getBody());
    }

    @Override
    public Rectangle getCollider() {
        return null;
    }

    @Override
    public void onCollision(Collider other) {

    }
    public float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }

    public Vector2 getPosition() {
        return position;
    }

//    public PlayerState getCurrentState() {
//        return currentState;
//    }

//    public void setCurrentState(PlayerState currentState) {
//        this.currentState = currentState;
//    }

//    public PlayerState getLastState() {
//        return lastState;
//    }

//    public void setLastState(PlayerState lastState) {
//        this.lastState = lastState;
//    }
    public String getCurrentState(){
        return stateManager.getCurrentStateName();
    }

    public boolean isFacingRight() {
        return isFacingRight;
    }

    public boolean isOpenInventory() {
        return isOpenInventory;
    }

    public CollisionHandler getCollisionHandler() {
        return collisionHandler;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public MovementHandler getMovementHandler() {
        return movementHandler;
    }

    public Vector2 getPositionInMap() {
        return positionInMap;
    }
}
