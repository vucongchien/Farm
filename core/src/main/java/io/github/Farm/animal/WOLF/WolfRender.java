package io.github.Farm.animal.WOLF;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.math.Rectangle;
import io.github.Farm.Interface.Collider;
import io.github.Farm.Interface.Heath;
import io.github.Farm.Interface.RenderableEntity;
import io.github.Farm.animal.Buffalo.Buffalo;
import io.github.Farm.animal.Pet;
import io.github.Farm.animal.PetManager;
import io.github.Farm.animal.PetState;
import io.github.Farm.animal.chatbox;
import io.github.Farm.player.PlayerController;

import java.util.concurrent.ThreadLocalRandom;

public class WolfRender implements Collider, RenderableEntity {
    private boolean thulinh;
    private Vector2 location;
    private final Rectangle box;
    private float   distancefrombossx;
    private float   distancefrombossy;
    private PetState crencurrentState;
    private PetState lastState;
    //
    private boolean trangthaitancong=false;
    private Pet prey;
    private Pet fistprey;

    private Animation<TextureRegion> currentAnimation;
    private final WolfImageManager imageManager;
    private  float stateTime =0;
    private final Heath hp;
    private int check = 2;//...woflleader
    private float timeActack;

    //...........hitall
    private boolean hit;
    private float cooldown;

    //........knockback
    private Vector2 knockbackVelocity;
    private float knockbackDuration;
    private float knockbackTimeElapsed;
    private float timehurt=0.31f;
    private boolean checkhurt;

    //........thongbao
    private final chatbox speak;

    //................check goc can bo
    private int checkCoer;
    //1.tren phai,2.duoi phai,3.duoitrai,4.trentrai
    private float radius;


    public WolfRender(Vector2 location, boolean thulinh) {
        this.location = location;
        this.thulinh = thulinh;
        box=new Rectangle(location.cpy().x-5, location.cpy().y, 15,15);
        lastState=crencurrentState= PetState.IDLE_LEFT;
        imageManager = new WolfImageManager();
        this.hp=new Heath(100);
        speak=new chatbox();
    }

    public void reset(Vector2 a){
        knockbackVelocity =null;
        knockbackDuration=0f;
        knockbackTimeElapsed=0f;
        hp.heal(100);
        location.x=a.x+distancefrombossx;
        location.y=a.y+distancefrombossy;
        check=2;
        thulinh=false;
        checkhurt=false;
    }

    public  int getcheck(){return check;}

    public void setcheck(int a){check=a;}

    public Heath getHp(){return hp;}

    public float getdistancefrombossx(){
        return distancefrombossx;
    }

    public void setDistancefrombossx(float a){distancefrombossx=a;}

    public float getDistancefrombossy() {
        return distancefrombossy;
    }

    public void setDistancefrombossy(float a){distancefrombossy=a;}

    public Vector2 getlocation(){
        return location;
    }

    public void setLocation(float tmpx,float tmpy){
        this.location=new Vector2(tmpx,tmpy);
    }

    public void setTrangthaitancong(boolean a){
        trangthaitancong=a;
    }

    public  boolean gettrangthaitancon(){
        return trangthaitancong;
    }

    public boolean getthulinh(){
        return thulinh;
    }

    public void setthulinh(boolean a){
        thulinh=a;
    }

    public Pet getPrey(){
        return prey;
    }

    public void setCrencurrentState(PetState stare){
        crencurrentState= stare;
    }

    public PetState getCrencurrentState(){return  crencurrentState;}

    public float getTimehurt() {
        return timehurt;
    }

    public void setTimehurt(float timehurt) {
        this.timehurt = timehurt;
    }

    public boolean isCheckhurt() {
        return checkhurt;
    }

    public void setCheckhurt(boolean checkhurt) {
        this.checkhurt = checkhurt;
    }


    public void setanimationattack(boolean a){
    }

    public float getcooldown(){return cooldown;}

    public void setcooldown(){cooldown-=0.1f;}

    public void recoverycooldown(){cooldown=0.6f;}

    public boolean gethit(){return hit;}

    public float getTimeActack() {
        return timeActack;
    }

    public void setTimeActack(float timeActack) {
        this.timeActack = timeActack;
    }

    public void setprey(PetManager petManager){
        if (petManager.getPetManager().isEmpty()) {
            prey = null;
        } else {
            Pet min = petManager.getPetManager().get(0);
            for (Pet x : petManager.getPetManager()) {
                if (x.location().dst(getlocation()) < min.location().dst(getlocation())) {
                    min = x;
                }
            }
            prey = min;
        }

    }

    private void updateAnimation() {
        if (crencurrentState !=lastState) {
            stateTime = 0f;
            currentAnimation = imageManager.getAnimation(crencurrentState);
            lastState=crencurrentState;
            timeActack=0;
        }
    }

    public void checkHit(Collider other){
        if(other instanceof PlayerController){
            PlayerController playerController= (PlayerController) other;
            hit= Math.abs(location.x - playerController.getPosition().x) < 100f && Math.abs(location.y - playerController.getPosition().y) < 100f;
        }

    }

    public void update(float deltaTime) {
        if (knockbackDuration > 0) {
            location.add(knockbackVelocity.cpy().scl(deltaTime));
            knockbackTimeElapsed += deltaTime;
            knockbackDuration -= deltaTime;
            if (knockbackDuration <= 0) {
                knockbackVelocity.set(0, 0);
            }
        }
        box.setPosition(location.x - 5, location.y);
    }

    @Override
    public Rectangle getCollider() {
        return box;
    }

    @Override
    public void onCollision(Collider other) {
        if(other instanceof PlayerController){
            PlayerController playerController =(PlayerController) other;
            if(playerController.getCurrentState().startsWith("HIT_")){
                hp.damaged(20);
                Vector2 playerPosition = playerController.getPosition();
                Vector2 direction = new Vector2(location).sub(playerPosition).nor();
                float knockbackForce = 200f;
                knockbackVelocity = direction.scl(knockbackForce);
                knockbackDuration = 0.1f;
                knockbackTimeElapsed = 0f;
                checkhurt=true;
                if(location.x>playerController.getPosition().x) {
                    crencurrentState = PetState.HURT_LEFT;
                }else{
                    crencurrentState = PetState.HURT_RIGHT;
                }
            }

        }


        if(other instanceof Buffalo){
            Buffalo buffalo=(Buffalo) other;
        }
    }

    @Override
    public float getY(){
        return location.y;
    }

    @Override
    public void render(SpriteBatch batch, Camera camera) {
        stateTime += Gdx.graphics.getDeltaTime();
        updateAnimation();
        currentAnimation = imageManager.getAnimation(crencurrentState);
        speak.setBatch(batch);
        if (currentAnimation == null) {
            currentAnimation = imageManager.getAnimation(PetState.IDLE_RIGHT);
        }
        if(checkhurt) {
            hp.getHealBar().renderGoblin(location.cpy().add(2, 4), batch, hp.getCurrHp(), hp.getMaxHp(), 12, 6);

        }
        batch.begin();
        if(speak.getCurrent()!=null) {
            speak.Render(location.cpy().add(8,12));
        }
        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, true);
        batch.draw(frame, getlocation().x-30, getlocation().y-25, 64, 64);
        batch.end();

    }

    public void dispose(){
        if (imageManager != null) {
            imageManager.dispose();
        }
        if (currentAnimation != null) {
            Object[] keyFrames = currentAnimation.getKeyFrames();
            for (Object keyFrame : keyFrames) {
                if (keyFrame instanceof TextureRegion) {
                    TextureRegion frame = (TextureRegion) keyFrame;
                    Texture texture = frame.getTexture();
                    if (texture != null) {
                        texture.dispose();
                    }
                }
            }
        }

    }

    public chatbox getSpeak() {
        return speak;
    }

    public int getCheckCoer() {
        return checkCoer;
    }

    public void setCheckCoer(int checkCoer) {
        this.checkCoer = checkCoer;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }

    public void checkCoer(Pet a) {
        if (a != null) {
            if (location.x > a.location().x) {
                if (location.y > a.location().y) {
                    checkCoer = 1;
                    if (radius == 0 || prey!=fistprey) {
                        fistprey=prey;
                        radius = (float) Math.toRadians(ThreadLocalRandom.current().nextFloat(0, 90));
                    }
                } else {
                    checkCoer = 2;
                    if (radius == 0 || prey!=fistprey) {
                        fistprey=prey;
                        radius = (float) Math.toRadians(ThreadLocalRandom.current().nextFloat(270, 360));
                    }
                }
            } else {
                if (location.y > a.location().y) {

                    checkCoer = 4;
                    if (radius == 0 || prey!=fistprey) {
                        fistprey=prey;
                        radius = (float) Math.toRadians(ThreadLocalRandom.current().nextFloat(90, 180));
                    }
                } else {
                    checkCoer = 3;
                    if (radius == 0 || prey!=fistprey) {
                        fistprey=prey;
                        radius = (float) Math.toRadians(ThreadLocalRandom.current().nextFloat(180, 270));
                    }
                }
            }
        }
    }
}

