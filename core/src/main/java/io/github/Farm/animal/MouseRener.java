package io.github.Farm.animal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import io.github.Farm.Interface.Collider;
import io.github.Farm.Interface.RenderableEntity;
import io.github.Farm.Plants.PlantManager;
import io.github.Farm.Plants.PlantRenderer;
import io.github.Farm.animal.Chicken.ChickenImageManager;
import io.github.Farm.player.PlayerController;

public class MouseRener implements Collider, RenderableEntity {
    private Vector2 location;
    private Rectangle box;
    private Vector2 prey;
    private Vector2 home;
    private long timehome;
    private long timeeating;
    private long timegohome;
    private PetState currentState;
    private ChickenImageManager imageManager;
    private boolean checkdie;
    private boolean checkdanger;
    private chatbox chatbox;
    private long stateTime;

    private Animation<TextureRegion> currentAnimation;

    public MouseRener(Vector2 home){
        this.home=home;
        this.location=this.home;
        box=new Rectangle(location.x,location.y,10,10);
        imageManager=new ChickenImageManager();
        this.currentState = PetState.IDLE_LEFT;
    }

    public void setLocation(float a,float b){location=new Vector2(a,b);}

    public void setCrencurrentState(PetState a){currentState=a;}

    public void setPrey(){
        if (!PlantManager.getInstance().getListPlants().isEmpty()){
            prey=PlantManager.getInstance().getListPlants().get(0).getPosition();
            for(PlantRenderer plantRenderer:PlantManager.getInstance().getListPlants()){
                if(plantRenderer.getPosition().dst(location)<prey.dst(location)){
                    prey =plantRenderer.getPosition();
                }
            }
        }

    }

    public void checkdanger(PlayerController playerController){
        checkdanger=Math.abs(location.x - playerController.getPosition().x) < 100f && Math.abs(location.y - playerController.getPosition().y) < 100f;
        if( Math.abs(location.x - playerController.getPosition().x) < 100f && Math.abs(location.y - playerController.getPosition().y) < 100f) {
            timegohome = TimeUtils.millis();
        }

    }

    public void activate(){
        if (location.dst(home) <= 1f) {
            if (timehome == 0) {
                timehome = TimeUtils.millis();
            }
            if (TimeUtils.timeSinceMillis(timehome) > 1000) {
                checkdie = true;
            }
        }
        if(checkdanger) {
            if (TimeUtils.timeSinceMillis(timegohome) < 5000) {
                movelocation(home, 1f, 1f, 0.2f);
            }
        }else{
            if(location.dst(prey)<1f){
                if(timeeating==0){
                    timeeating=TimeUtils.millis();
                }
                if(TimeUtils.timeSinceMillis(timeeating)>1000) {
                    movelocation(home, 1f, 1f, 0.2f);
//                    chatbox.setCurrent("no", 32, 32);
                }
            }else{
                movelocation(prey,1f,1f,0.2f);
            }
        }
    }

    public void movelocation(Vector2 b, float sox,float soy,float deltaTime) {
        if(!checkdanger) {
            if (Math.abs(location.x - b.x) > sox) {
                if (location.x > b.x) {
                    location.x -= 20f * deltaTime;
                    setLocation(location.x, location.y);
                    box.setPosition(location.cpy().x - 5, location.cpy().y);
                    currentState=PetState.WALK_LEFT;
                } else if (location.x < b.x) {
                    location.x += 20f * deltaTime;
                    setLocation(location.x, location.y);
                    box.setPosition(location.cpy().x - 5, location.cpy().y);
                    currentState=PetState.WALK_RIGHT;
                }
            }
            if (Math.abs(location.y - b.y) > soy) {
                if (location.y > b.y) {
                    location.y -= 20f * deltaTime;
                    setLocation(location.x, location.y);
                    box.setPosition(location.cpy().x - 5, location.cpy().y);
                    setCrencurrentState(currentState);
                } else if (location.y < b.y) {
                    location.y += 20f * deltaTime;
                    setLocation(location.x, location.y);
                    box.setPosition(location.cpy().x - 5, location.cpy().y);
                    setCrencurrentState(currentState);
                }
            }
        }
    }

    @Override
    public Rectangle getCollider() {
        return null;
    }

    @Override
    public void onCollision(Collider other) {

    }

    @Override
    public float getY() {
        return location.y;
    }

    @Override
    public void render(SpriteBatch batch, Camera camera) {
        stateTime += Gdx.graphics.getDeltaTime();
        if(currentAnimation==null){
            currentAnimation=imageManager.getAnimation(PetState.IDLE_LEFT);
        }
        currentAnimation = imageManager.getAnimation(currentState);
        batch.begin();
        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, true);
        batch.draw(frame, location.x-30, location.y-25, 64, 64);
        batch.end();

    }
}
