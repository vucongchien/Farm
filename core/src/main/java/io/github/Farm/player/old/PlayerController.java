package io.github.Farm.player.old;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.badlogic.gdx.utils.Disposable;
import io.github.Farm.Map.MapInteractionHandler;
import io.github.Farm.Plants.PlantManager;
import io.github.Farm.Plants.PlantRenderer;
import io.github.Farm.Plants.PlantType;
import io.github.Farm.UI.Inventory;
import io.github.Farm.UI.InventorySlot;
import io.github.Farm.player.Collider;
import io.github.Farm.player.PlayerState;

import java.util.Iterator;

public class PlayerController implements Collider,Disposable {
    private Vector2 position;
    private Vector2 positionInMap;
    private float speed;
    private PlayerState currentState = PlayerState.IDLE_RIGHT;
    private PlayerState lastState = PlayerState.IDLE_RIGHT;


    //boolean
    private boolean isFacingRight=true;
    private boolean lastFacingRight=true;


    // Plowing
    private float timeToPlow = 1.0f;
    private float startPlow = 0f;
    private Vector2 lastPosition;
    private boolean isPlowing = false;

    //hitting
    private boolean hitting=false;
    private float timeToHit=0.3f;
    private float timeHitCoolDown=0.2f;
    private float startHit=0f;


    //map
    private MapInteractionHandler mapInteractionHandler;

    //collider
    private World world;
    private Body body;
    private Rectangle playerCollider;


    //inventory
    private Inventory inventory;
    private boolean isOpenInventory=false;

    //plant
    private PlantManager plantManager;



    public PlayerController(Vector2 startPosition, float speed,World world,
                            MapInteractionHandler mapInteractionHandler,PlantManager plantManager) {
        this.position = startPosition;
        this.positionInMap=new Vector2( (int) (position.x / 16),(int)(position.y / 16));
        this.lastPosition = new Vector2(startPosition);
        this.speed = speed;
        this.mapInteractionHandler=mapInteractionHandler;

        inventory=new Inventory();
        this.plantManager=plantManager;

        this.world=world;
        playerCollider =new Rectangle(position.x,position.y,16,16);
        createBody();


    }

    private void createBody(){
        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        bodyDef.position.set(position);


        body = world.createBody(bodyDef);

        PolygonShape shape = new PolygonShape();
        shape.setAsBox(5 , 5);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.1f;

        body.createFixture(fixtureDef);
        shape.dispose();
    }

    private void checkCollisions() {
        for (PlantRenderer plant : plantManager.getPlants()) {
            if (playerCollider.overlaps(plant.getCollider())) {
                onCollision(plant);
                plant.onCollision(this);
                break;
            }
        }
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
            isFacingRight=false;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            movement.x = 1;
            lastState = PlayerState.WALK_RIGHT;
            moving = true;
            isFacingRight=true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            isPlowing=true;
            currentState = PlayerState.valueOf("DIG_" + lastState.name().split("_")[1]);
            return;
        } else {
            isPlowing=false;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.SPACE)&& mapInteractionHandler.checkTile(positionInMap,"dug_soil")){
            isOpenInventory=!isOpenInventory;
            inventory.add(5,PlantType.wheat.toString());

        }
        if(isOpenInventory){
            handleInventoryInput();
        }

        if(Gdx.input.isKeyPressed(Input.Keys.J)){
            currentState = PlayerState.valueOf("HIT_" + lastState.name().split("_")[1]);
            hitting=true;
            return;
        }
        else {
            hitting=false;
        }

        if(Gdx.input.isKeyPressed(Input.Keys.R)){
            currentState=PlayerState.valueOf("WATER_"+lastState.name().split("_")[1]);
            return;
        }



        if (moving) {
            currentState = (movement.x != 0) ? lastState : currentState;
        } else {
            currentState = PlayerState.getIdleState(lastState);
        }
    }

    private void handleInventoryInput() {
        if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            inventory.moveSelection(-1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            inventory.moveSelection(1);
        }
        if (Gdx.input.isKeyPressed(Input.Keys.ENTER) && isOpenInventory) {
            InventorySlot selectedSlot = inventory.getSelectedSlot();
            if (selectedSlot.hasItem()) {
                String itemType = selectedSlot.getItemType();
                PlantType plantType = null;

                for (PlantType type : PlantType.values()) {
                    if (type.name().equalsIgnoreCase(itemType)) {
                        plantType = type;
                        break;
                    }
                }

                if (plantType != null) {
                    currentState=PlayerState.valueOf("DOING_"+lastState.name().split("_")[1]);

                    Vector2 plantPosition = new Vector2(positionInMap.x, positionInMap.y);
                    mapInteractionHandler.plantSeed(positionInMap);
                    plantManager.addPlant(plantPosition,plantType);

                    selectedSlot.decreaseQuantity(1);
                }
            }
            isOpenInventory=false;
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

        body.setLinearVelocity(movement.scl(speed));
        position.set(body.getPosition());
        this.positionInMap = new Vector2(
            isFacingRight ? (int) (position.x / 16) +1 : (int) (position.x / 16) -1,
            (int) (position.y / 16)
        );
    }

    private void plow(float deltaTime) {
        if (isPlowing) {
            startPlow += deltaTime;
            if (!getPosition().epsilonEquals(lastPosition, 0.1f)) {
                startPlow = 0f;
                lastPosition.set(getPosition());
            }
            if (startPlow >= timeToPlow) {
                startPlow = 0f;

                mapInteractionHandler.digSoil(positionInMap);
                //change tile

            }
        } else {
            isPlowing = false;
            startPlow = 0f;
        }
    }

    private void hit(float deltaTime){
        if(hitting){
            startHit+=deltaTime;
            if(isFacingRight!=lastFacingRight ){
                startHit=0;

                lastFacingRight=isFacingRight;

            }
            if(startHit>=timeToHit){
                hitSomeThing();
            }
            if(startHit>=0.5){
                startHit=0;
            }
        }
        else{
            startHit=0;
        }
    }

    private void hitSomeThing(){
        Iterator<PlantRenderer> iterator = plantManager.getPlants().iterator();
        while (iterator.hasNext()) {
            PlantRenderer plant = iterator.next();
            if (playerCollider.overlaps(plant.getCollider())) {
                iterator.remove();
            }
        }

        if(plantManager.getPlantAtPosition(positionInMap)!=null){



            System.out.println("hit");
            BitmapFont font=new BitmapFont();
            SpriteBatch batch=new SpriteBatch();
            batch.begin();
            font.draw(batch, "Hit!", position.x, position.y + 20);
            batch.end();
        }
    }

    public void update(float deltaTime) {
        handleInput();
        position.set(body.getPosition());
        playerCollider.setPosition(position);
        moveCharacter(deltaTime);
        plow(deltaTime);
        hit(deltaTime);
        checkCollisions();
    }

    //-----------use interface

    @Override
    public Rectangle getCollider() {
        return playerCollider;
    }
    @Override
    public void onCollision(Collider other) {
        if (other instanceof PlantRenderer) {
            PlantRenderer plant = (PlantRenderer) other;
            if (plant.isHarvestable()) {
                System.out.println("Player is harvesting " + plant.getType());
                plant.harvest();
            }
        }
    }



    @Override
    public void dispose(){
        world.destroyBody(body);
    }
    //------------setter, getter

    public Vector2 getPosition() {
        return position;
    }
    public boolean isWalking() {
        return currentState.toString().startsWith("WALK");
    }

    public boolean isPlowing() {
        return isPlowing;
    }

    public float getTimeToPlow() {
        return timeToPlow;
    }

    public float getStartPlow() {
        return startPlow;
    }

    public PlayerState getCurrentState() {
        return currentState;
    }

    public PlayerState getLastState(){return lastState;}

    public float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }

    public Vector2 getPositionInMap(){
        return positionInMap;
    }

    public Rectangle getPlayerCollider(){
        return playerCollider;
    }



}
