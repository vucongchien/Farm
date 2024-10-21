package io.github.Farm.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.Farm.Interface.Collider;
import io.github.Farm.Interface.Heath;
import io.github.Farm.Map.MapInteractionHandler;
import io.github.Farm.SoundManager;
import io.github.Farm.animal.Buffalo.Buffalo;
import io.github.Farm.inventory.Inventory;
import io.github.Farm.player.PLAYER_STATE.*;
import io.github.Farm.ui.MainMenu;
import io.github.Farm.ui.Other.Expression;
import io.github.Farm.ui.Other.ExpressionManager;

import java.io.*;

public class PlayerController implements Collider, Disposable {
    private final Heath heath;
    private float speed;
    private final Vector2 positionInMap;

    //boolean
    private boolean isFacingRight = true;
    private boolean isFishing = false;
    private boolean isSwim = false;
    private boolean isPlanting = false;
    private boolean isHurt = false; StringBuilder enemyDirection;

    private final InputHandler inputHandler;
    private final CollisionHandler collisionHandler;
    private final PlayerStateManager stateManager;

    //collider
    private final Rectangle collider;
    private final ShapeRenderer shapeRenderer;
    private final World world;
    private final Body body;

    private final Camera camera;
    private ExpressionManager expressionManager;
    //..................readfile
    private final String link="playerData.json";

    public PlayerController(Vector2 startPosition, World world, MapInteractionHandler mapInteractionHandler, Camera camera) {
        if(MainMenu.isCheckcontinue()){
            System.out.println("da doc nha may");
            System.out.println(MainMenu.isCheckcontinue());
            this.heath = new Heath(100);
            readPlayerData(this.getHeath(),startPosition);
            this.positionInMap = new Vector2((int) (startPosition.x / 16), (int) (startPosition.y / 16));
            this.body = createBody(startPosition, world);

        }else {
            this.heath = new Heath(100);
            this.positionInMap = new Vector2((int) (startPosition.x / 16), (int) (startPosition.y / 16));
            this.body = createBody(startPosition, world);
        }
        this.speed = 100f;
        this.inputHandler = new InputHandler(this);
        this.collisionHandler = new CollisionHandler(mapInteractionHandler, this);
        this.world = world;
        this.camera = camera;
        this.stateManager = new PlayerStateManager(new IdleState("RIGHT"));
        this.collider = new Rectangle(body.getPosition().x, body.getPosition().y, 16, 16);
        this.shapeRenderer = new ShapeRenderer();
        this.expressionManager=new ExpressionManager();

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
        updatePlayerState(deltaTime);
        updateExpress();


        collider.setPosition(isFacingRight ? body.getPosition().x + 5 : body.getPosition().x - 20, body.getPosition().y - 5);

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            Inventory.getInstance().setOpened();
            Inventory.getInstance().addItem("SEED_pumpkin", 1);
            Inventory.getInstance().addItem("FOOD_pumpkin", 1);
            Inventory.getInstance().addItem("SEED_kale", 1);
            Inventory.getInstance().addItem("SEED_carrot", 1);
        }

        updateMovement(deltaTime);

        if (isSwim) return;
        collisionHandler.checkCollisions();
    }

    public void updateMovement(float deltaTime){

        if (Inventory.getInstance().isOpened()) {
            inputHandler.handleInventoryInput();
            body.setLinearVelocity(Vector2.Zero);
            return;
        }
        updateSpeed();
        stateManager.updateState(this, deltaTime);

        if(getCurrentState().startsWith("HURT_")){
            return;
        }

        Vector2 movement = inputHandler.handleMovementInput();
        body.setLinearVelocity(movement.scl(speed));
        this.positionInMap.set(
            isFacingRight ? (int) (body.getPosition().x / 16) + 1 : (int) (body.getPosition().x / 16) - 1,
            (int) (body.getPosition().y / 16)
        );
    }

    public void updatePlayerState(float deltaTime) {
        if(Inventory.getInstance().isOpened()) return;


        Vector2 movement = inputHandler.handleMovementInput();
        if (movement.x > 0) {
            isFacingRight = true;
        } else if (movement.x < 0) {
            isFacingRight = false;
        }
        String direction = isFacingRight ? "RIGHT" : "LEFT";

        //hurt any whereeeeeeeeeeeeeeeeeeeeee
        if(isHurt){
            stateManager.changeState(this,new HurtState(direction,enemyDirection.toString()));
            return;
        }

        //frezeeeeeeeeeeeeeeeeeeeee
        if(Inventory.getInstance().isOpened()) return;



        //returnnnnnnnnnnnnnnnnnnnnnnnnnnnnnn
        Vector2 positionCheckSwim = new Vector2((int) (body.getPosition().x / 16), (int) (body.getPosition().y / 16));
        if (collisionHandler.getMapInteractionHandler().checkTile(positionCheckSwim, "water")) {
            stateManager.changeState(this, new SwimState(direction));
            isSwim = true;
            SoundManager.getInstance().stopFootStep();
            return;
        } else {
            isSwim = false;
        }

        //sound moveeeeeeeeeeeeeeeeeeeeee
        if(inputHandler.isMoving()&&!isSwim){
            SoundManager.getInstance().playFootStep();
        }
        else {
            SoundManager.getInstance().stopFootStep();
        }


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


        else if (inputHandler.isWatering()) {
            stateManager.changeState(this, new WaterState(direction));
        }
        else if (inputHandler.isHitting()) {
            stateManager.changeState(this, new HitState(direction));
        }
        else if (inputHandler.isPlowing()) {

            stateManager.changeState(this, new DigState(direction));
        }
        else if (inputHandler.isMoving()) {
            stateManager.changeState(this, new WalkState(direction));
        }
        else if (isPlanting) {
            stateManager.changeState(this, new DoingState(direction));
        }
        else if (!isFishing&&!stateManager.getCurrentStateName().startsWith("HURT_")) {
            stateManager.changeState(this, new IdleState(direction));
        }
        //is fishing
        if(!stateManager.getCurrentStateName().matches("^(WAITING_|CASTING_|CAUGHT_).*")){
            isFishing=false;
        }
        if (!stateManager.getCurrentStateName().startsWith("DOING_")) {
            isPlanting = false;
        }



    }

    public void updateExpress(){
        if(getCurrentState().startsWith("DOING_")){
            expressionManager.setExpression(Expression.WORKING);
        }
        else {
            expressionManager.setExpression(Expression.NULL);
        }
        expressionManager.render(body.getPosition(),camera);
    }

    public void updateSpeed(){
        if(isSwim){
            speed=40f;
        }
        else{
            speed=150f;
        }
    }

    public void changeState(InterfacePlayerState newState) {
        stateManager.changeState(this, newState);
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
        if (other instanceof Buffalo){
            Buffalo buffalo=(Buffalo) other;
            if(Gdx.input.isKeyJustPressed(Input.Keys.F)) {
                if(Inventory.getInstance().useItem("FOOD_pumpkin")) {
                    buffalo.setCheckeating(true);
                    buffalo.getmau().damaged(20);

                }
            }
        }
    }


    public float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }

    public Vector2 getPosition() {
        return body.getPosition();
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

    public Heath getHeath(){
        return heath;
    }

    public void setPlanting(boolean planting) {
        isPlanting = planting;
    }

    public boolean isPlanting() {
        return isPlanting;
    }

    public Camera getCamera() {
        return camera;
    }

    public Body getBody() {
        return body;
    }

    public boolean isHurt() {
        return isHurt;
    }

    public void setHurt(boolean hurt) {
        isHurt = hurt;
    }

    public void setEnemyDirection(String enemyDirection) {
        this.enemyDirection = new StringBuilder(enemyDirection);
    }

    public void readPlayerData(Heath heath,Vector2 a) {
            ObjectMapper objectMapper = new ObjectMapper();
            try {
                JsonNode playerData = objectMapper.readTree(new File(link));
                double posX = playerData.get("posX").asDouble();
                double posY = playerData.get("posY").asDouble();
                double health = playerData.get("health").asDouble();
                a.set((float) posX,(float) posY);
                heath.setCurrHp((float) health);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
    }

}
