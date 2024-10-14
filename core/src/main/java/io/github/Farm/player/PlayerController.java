package io.github.Farm.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.Timer;
import io.github.Farm.Interface.Collider;
import io.github.Farm.Interface.Heath;
import io.github.Farm.Map.MapInteractionHandler;
import io.github.Farm.inventory.Inventory;
import io.github.Farm.player.PLAYER_STATE.*;

public class PlayerController implements Collider, Disposable {
    private Heath heath;
    private Vector2 position;
    private Vector2 positionInMap;

    //boolean
    private boolean canAct=true;
    private boolean isFacingRight = true;
    private boolean isFishing=false;
    private boolean isSwim=false;
    private boolean isPlanting=false;

    //fishing
    private float TimeStartFish=0f;
    private float TimeToFishing=5f;

    private InputHandler inputHandler;
    private  CollisionHandler collisionHandler;
    private PlayerStateManager stateManager;



    //collider
    private Rectangle collider;
    private ShapeRenderer shapeRenderer;
    private World world;
    private Body body;

    public PlayerController(Vector2 startPosition, World world,MapInteractionHandler mapInteractionHandler) {

        this.heath=new Heath(100);

        this.position=startPosition;
        this.positionInMap = new Vector2((int) (startPosition.x / 16), (int) (startPosition.y / 16));

        this.inputHandler = new InputHandler(this);
        this.collisionHandler = new CollisionHandler( mapInteractionHandler);
        this.world=world;
        this.body=createBody(startPosition, world);

        this.stateManager = new PlayerStateManager(new IdleState("RIGHT"));

        this.collider=new Rectangle(position.x,position.y,16,16);
        this.shapeRenderer=new ShapeRenderer();
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
        //state
        updatePlayerState(deltaTime);

        //update may bien tao lao
        collider.setPosition(isFacingRight ? position.x + 5 : position.x - 20, position.y - 5);

        if(Gdx.input.isKeyJustPressed(Input.Keys.SPACE)){
            Inventory.getInstance().setOpened();
            Inventory.getInstance().addItem("SEED_pumpkin",1);
            Inventory.getInstance().addItem("FOOD_pumpkin",1);
        }

        if(Inventory.getInstance().isOpened()){
            body.setLinearVelocity(Vector2.Zero);
            inputHandler.handleInventoryInput();
            canAct=false;
            return;
        }else {
            canAct=true;
        }

//---------------------------all input
        //move
        Vector2 movement = inputHandler.handleMovementInput();
        stateManager.updateState(this, deltaTime);

        body.setLinearVelocity(movement.scl(150f));
        position.set(body.getPosition());
        this.positionInMap.set(
            isFacingRight ? (int) (position.x / 16) + 1 : (int) (position.x / 16) - 1,
            (int) (position.y / 16)
        );

        //logic swim
        if (isSwim) return;
        collisionHandler.checkCollisions(this);

        if (inputHandler.isPlowing()) {
            collisionHandler.handlePlowing(positionInMap);
        }


    }

    public void changeState(InterfacePlayerState newState) {
        stateManager.changeState(this, newState);
    }

    public void updatePlayerState(float deltaTime){
        if(!canAct) return;

        Vector2 movement = inputHandler.handleMovementInput();
        if(movement.x>0)        { isFacingRight=true; }
        else if(movement.x<0)   { isFacingRight=false;}
        String direction = isFacingRight ? "RIGHT" : "LEFT";

        //check swim
        Vector2 positionCheckSwim= new Vector2((int) (position.x / 16) ,(int) (position.y / 16));
        if(collisionHandler.getMapInteractionHandler().checkTile(positionCheckSwim,"water")){
            stateManager.changeState(this,new SwimState(direction));
            isSwim=true;
            return;
        }
        else isSwim=false;

        if (inputHandler.isCasting()&&!isFishing)
        {
            if (collisionHandler.isPlayerCanFish(collider))
            {
                isFishing = true;
                stateManager.changeState(this, new CastingState(direction));
            }
            else
            {
                System.out.println("del duoc cau ca dume m");
            }
        }
        else if (inputHandler.isWatering())
        {
            stateManager.changeState(this,new WaterState(direction));
        }
        else if (inputHandler.isHitting())
        {
            stateManager.changeState(this,new HitState(direction));
        }
        else if (inputHandler.isPlowing())
        {
            stateManager.changeState(this,new DigState(direction));
        }
        else if (inputHandler.isMoving())
        {
            stateManager.changeState(this, new WalkState(direction));
        }
        else if (isPlanting){
            stateManager.changeState(this,new DoingState(direction));
        }
        else if(!isFishing )
        {
            stateManager.changeState(this, new IdleState(direction));
        }




        //is fishing
        if(!stateManager.getCurrentStateName().startsWith("WAITING_")||!stateManager.getCurrentStateName().startsWith("CASTING_")||!stateManager.getCurrentStateName().startsWith("CAUGHT_")){
            isFishing=false;
        }
        if(!stateManager.getCurrentStateName().startsWith("DOING_")){
            isPlanting=false;
        }



    }

    @Override
    public void dispose() {
        shapeRenderer.dispose();
        world.destroyBody(body);
    }

    @Override
    public Rectangle getCollider() {
        return collider;
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

    public String getCurrentState(){
        return stateManager.getCurrentStateName();
    }

    public boolean isFacingRight() {
        return isFacingRight;
    }


    public CollisionHandler getCollisionHandler() {
        return collisionHandler;
    }

    public InputHandler getInputHandler() {
        return inputHandler;
    }


    public Vector2 getPositionInMap() {
        return positionInMap;
    }

    public void setPosition(Vector2 position){
        this.position=position;
    }

    public Heath getHeath(){
        return heath;
    }

    public boolean isCanAct() {
        return canAct;
    }

    public void setCanAct(boolean canAct) {
        this.canAct = canAct;
    }

    public void setPlanting(boolean planting) {
        isPlanting = planting;
    }

    public boolean isPlanting() {
        return isPlanting;
    }
}
