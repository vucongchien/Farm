package io.github.Farm.animal.Buffalo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import java.util.Timer;

import com.badlogic.gdx.math.Rectangle;
import io.github.Farm.Interface.Animal;
import io.github.Farm.Interface.Heath;
import io.github.Farm.Interface.RenderableEntity;
import io.github.Farm.animal.Pet;
import io.github.Farm.animal.PetState;
import io.github.Farm.player.PlayerController;


public class Buffalo extends Pet implements RenderableEntity {
    private Heath mau;
    //...........
    private Rectangle mapbox;
    //.................chi so randum
    Vector2 startpoint = null;

    private Rectangle box;
    //.......thong so box;
    private float height;
    private float width;
    private BuffaloImageManager imageManager;
    private Animation<TextureRegion> currentAnimation;
    private float stateTime;
    private final Timer timer = new Timer();
    private Vector2 targetLocation = randomlocation();
    private int direction;
    private Vector2 previousLocation;
    private int beside = 3;
    //  dat 1 truoc 2 sau 3 trai 4 phai
    ShapeRenderer shapeRenderer;
    // vẽ hoạt động
    private long stopTime = 0;
    private PetState crencurrentState;
    //////////////////////////////////////////


    ///// vacham
    private long collisionStopTime = 0;
    private boolean isStopped = false;
    private boolean checkplow;

    //..........knockback
    private Vector2 knockbackVelocity;
    private float knockbackDuration;
    private float knockbackTimeElapsed;
    private float timehurt=0.31f;
    private boolean checkhurt;
    private boolean checkmove;

    public Buffalo(Vector2 location, long hungry, boolean killed) {
        super(location, hungry, killed);
        box = new Rectangle(getlocation().x + 10f, getlocation().y + 5f, 10, 15);
        imageManager = new BuffaloImageManager();
//        direction = 1;
        this.previousLocation = new Vector2(location);
        height = box.getHeight();
        width = box.getWidth();
        mau = new Heath(100);
        crencurrentState =PetState.IDLE_LEFT;
    }


    public  void setStopTime(long a){stopTime=a;}

    public long getStopTime() {return stopTime;}

    public void settargetLocation(Vector2 a){targetLocation=a;}

    public Vector2 getTargetLocation(){return targetLocation;}


    public  long getCollisionStopTime(){return collisionStopTime;}

    public void setCollisionStopTime(long a){collisionStopTime=a;}

    public boolean getIsStopped(){return isStopped;}

    public void setIsStopped(boolean a){isStopped=a;}

    public void setBeside(int a){beside=a;}

    public int getBeside(){return beside;}

    public float getheight(){return height;}

    public float getWidth(){return width;}

    public Vector2 getpreviousLocation() {
        return previousLocation;
    }

    public void setBox(float x, float y) {
        box.setPosition(x, y);
    }

    public Rectangle getbox() {
        return box;
    }


    public Heath getmau() {
        return mau;
    }

    public void setcrencurrentState(PetState crencurrentState){this.crencurrentState=crencurrentState;}



    public boolean collide(Buffalo a) {
        if(checkplow){return false;}
        if (getbox().overlaps(a.getbox())) {
            if (!isStopped) {
                isStopped = true;
                collisionStopTime = TimeUtils.millis();
            }
            if (!a.isStopped) {
                a.setIsStopped(true);
                a.setCollisionStopTime(TimeUtils.millis());
            }
            float deltaX = this.getlocation().x - a.getlocation().x;
            float deltaY = this.getlocation().y - a.getlocation().y;
            float overlapX = this.box.width / 2 + a.box.width / 2 - Math.abs(deltaX);
            float overlapY = this.box.height / 2 + a.box.height / 2 - Math.abs(deltaY);

           if (overlapX > 0 && overlapY > 0) {
            float pushFactor = 3f;

            if (Math.abs(overlapX) < Math.abs(overlapY)) {
                if (deltaX > 0) {
                    this.getlocation().x += overlapX * pushFactor;
                    a.getlocation().x -= overlapX * pushFactor;
                } else {
                    this.getlocation().x -= overlapX * pushFactor;
                    a.getlocation().x += overlapX * pushFactor;
                }
            } else {
                if (deltaY > 0) {
                    this.getlocation().y += overlapY * pushFactor;
                    a.getlocation().y -= overlapY * pushFactor;
                } else {
                    this.getlocation().y -= overlapY * pushFactor;
                    a.getlocation().y += overlapY * pushFactor;
                }
            }

            this.box.setPosition(this.getlocation().x, this.getlocation().y);
            a.setBox(a.getlocation().x, a.getlocation().y);

            return true;
        }
        }
        return false;
    }



    public void movelocation() {
        if(!checkmove) {
            float deltaTime = Gdx.graphics.getDeltaTime();
                if (Math.abs(getlocation().x - targetLocation.x) > 1f) {
                    if (getlocation().x < targetLocation.x) {
                        getlocation().x += 10f * deltaTime;
                        setLocation(getlocation().x, getlocation().y);
                        box.setPosition(getlocation().x, getlocation().y);
                        setcrencurrentState(PetState.WALK_RIGHT);
                        beside = 4;
                    } else {
                        getlocation().x -= 10f * deltaTime;
                        setLocation(getlocation().x, getlocation().y);
                        box.setPosition(getlocation().x, getlocation().y);
                        setcrencurrentState(PetState.WALK_LEFT);
                        beside = 3;
                    }
                }
                if (getlocation().y < targetLocation.y) {
                    getlocation().y += 10f * deltaTime;
                    setLocation(getlocation().x, getlocation().y);
                    box.setPosition(getlocation().x, getlocation().y);

                } else {
                    getlocation().y -= 10f * deltaTime;
                    setLocation(getlocation().x, getlocation().y);
                    box.setPosition(getlocation().x, getlocation().y);
                }

            }
    }

    public void plow(PlayerController playerController){
        if(checkplow){
            setLocation(playerController.getPosition().x,playerController.getPosition().y);
            if(playerController.isFacingRight()){
                setcrencurrentState(PetState.WALK_RIGHT);
            }else{
                setcrencurrentState(PetState.WALK_LEFT);
            }
        }
    }

    public Vector2 randomlocation() {
        int randomIntx;
        int randomInty;
//        if(startpoint==null){
//             randomIntx = ThreadLocalRandom.current().nextInt(0,1920);
//             randomInty= ThreadLocalRandom.current().nextInt(0,1080);
//        }else {
//             randomIntx = ThreadLocalRandom.current().nextInt((int)(getStartpoint().x),(int)(getStartpoint().x+100));
//             randomInty = ThreadLocalRandom.current().nextInt((int)(getStartpoint().y),(int)(getStartpoint().y+100));
        //}
        randomIntx = ThreadLocalRandom.current().nextInt(500, 650);
        randomInty = ThreadLocalRandom.current().nextInt(950, 1050);
        return new Vector2(randomIntx, randomInty);
    }



    public void xaychuong(Vector2 a) {
        mapbox = new Rectangle(a.x, a.y, 100, 100);
        startpoint = a;
    }

    @Override
    public float getY() {
        return getlocation().y;
    }

    @Override
    public void render(SpriteBatch batch, Camera camera) {
        box.setPosition(getlocation().x, getlocation().y);
//        shapeRenderer = new ShapeRenderer();
//        shapeRenderer.setProjectionMatrix(camera.combined);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.rect(box.x, box.y, box.width, box.height);
//        shapeRenderer.end();
        currentAnimation = imageManager.getAnimation(crencurrentState);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, true);
        batch.draw(frame, getlocation().x-25, getlocation().y, 32, 32);
        batch.end();

    }
    public void dispose(){
        if (imageManager != null) {
            imageManager.dispose(); // Giải phóng tài nguyên của BuffaloImageManager
        }
        if (shapeRenderer != null) {
            shapeRenderer.dispose(); // Giải phóng ShapeRenderer nếu đã được khởi tạo
        }
    }

    public Vector2 getKnockbackVelocity() {
        return knockbackVelocity;
    }

    public void setKnockbackVelocity(Vector2 knockbackVelocity) {
        this.knockbackVelocity = knockbackVelocity;
    }

    public float getKnockbackDuration() {
        return knockbackDuration;
    }

    public void setKnockbackDuration(float knockbackDuration) {
        this.knockbackDuration = knockbackDuration;
    }

    public float getKnockbackTimeElapsed() {
        return knockbackTimeElapsed;
    }

    public void setKnockbackTimeElapsed(float knockbackTimeElapsed) {
        this.knockbackTimeElapsed = knockbackTimeElapsed;
    }

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

    public void update(float deltaTime) {
        // Xử lý knockback
        if (knockbackDuration > 0) {
            getlocation().add(knockbackVelocity.cpy().scl(deltaTime));
            knockbackTimeElapsed += deltaTime;
            knockbackDuration -= deltaTime;

            if (knockbackDuration <= 0) {
                knockbackVelocity.set(0, 0);
            }
        } else {
            movelocation();
        }
        box.setPosition(getlocation().x + 10, getlocation().y + 5);
    }

    public boolean isCheckmove() {
        return checkmove;
    }

    public void setCheckmove(boolean checkmove) {
        this.checkmove = checkmove;
    }
}
