package io.github.Farm.animal.Buffalo;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.concurrent.ThreadLocalRandom;
import com.badlogic.gdx.utils.TimeUtils;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;

import com.badlogic.gdx.math.Rectangle;
import io.github.Farm.Interface.Collider;
import io.github.Farm.Interface.Heath;
import io.github.Farm.Interface.RenderableEntity;
import io.github.Farm.animal.Pet;
import io.github.Farm.animal.PetState;
import io.github.Farm.player.PlayerController;


public class Buffalo extends Pet implements RenderableEntity, Collider {
    private Rectangle box;
    //.....animation
    private BuffaloImageManager imageManager;
    private Animation<TextureRegion> currentAnimation;
    //...timeanimation
    private float stateTime;
    private Vector2 targetLocation = randomlocation();
    private boolean isLeft = true;
    //......ve box
    ShapeRenderer shapeRenderer;
    // vẽ hoạt động
    private long stopTime = 0;
    private PetState crencurrentState;
    //////////////////////////////////////////
    ///// vacham
    private long collisionStopTime = 0;
    private boolean isStopped = false;

    //..........knockback
    private Vector2 knockbackVelocity;
    private float knockbackDuration;
    private float knockbackTimeElapsed;
    private float timehurt=0.31f;
    //.....check di chuyen
    private boolean checkmove;
    private boolean checkeating;

    public Buffalo(Vector2 location, long hungry) {
        super(location, hungry,100);
        box = new Rectangle(getlocation().x + 10f, getlocation().y + 5f, 15, 10);
        imageManager = new BuffaloImageManager();
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

    public boolean getLeft(){return isLeft;}

    public void setBox(float x, float y) {
        box.setPosition(x, y);
    }

    public Rectangle getbox() {
        return box;
    }

    public Heath getmau() {
        return getHeath();
    }

    public void setKnockbackVelocity(Vector2 knockbackVelocity) {
        this.knockbackVelocity = knockbackVelocity;
    }

    public void setKnockbackDuration(float knockbackDuration) {
        this.knockbackDuration = knockbackDuration;
    }

    public void setKnockbackTimeElapsed(float knockbackTimeElapsed) {
        this.knockbackTimeElapsed = knockbackTimeElapsed;
    }

    public void setcrencurrentState(PetState crencurrentState){this.crencurrentState=crencurrentState;}

    public void setCheckmove(boolean checkmove) {
        this.checkmove = checkmove;
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

    public boolean collide(Buffalo a) {
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
            float pushFactor = 5f;

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
                        isLeft = false;
                    } else {
                        getlocation().x -= 10f * deltaTime;
                        setLocation(getlocation().x, getlocation().y);
                        box.setPosition(getlocation().x, getlocation().y);
                        setcrencurrentState(PetState.WALK_LEFT);
                        isLeft = true;
                    }
                }
                if (Math.abs(getlocation().y - targetLocation.y) > 1f) {
                    if (getlocation().y < targetLocation.y) {
                        getlocation().y += 10f * deltaTime;
                        setLocation(getlocation().x, getlocation().y);
                        box.setPosition(getlocation().x, getlocation().y);
                        if (isLeft) {
                            setcrencurrentState(PetState.WALK_LEFT);
                        } else {
                            setcrencurrentState(PetState.WALK_RIGHT);
                        }

                    } else {
                        getlocation().y -= 10f * deltaTime;
                        setLocation(getlocation().x, getlocation().y);
                        box.setPosition(getlocation().x, getlocation().y);
                        if (isLeft) {
                            setcrencurrentState(PetState.WALK_LEFT);
                        } else {
                            setcrencurrentState(PetState.WALK_RIGHT);
                        }
                    }
                }
            }
    }

    public void update(float deltaTime) {
        if (knockbackDuration > 0) {
            getlocation().add(knockbackVelocity.cpy().scl(deltaTime));
            knockbackTimeElapsed += deltaTime;
            knockbackDuration -= deltaTime;

            if (knockbackDuration <= 0) {
                knockbackVelocity.set(0, 0);
            }
        }
//        else {
//            movelocation();
//        }
        box.setPosition(getlocation().x + 10, getlocation().y + 5);
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
        batch.draw(frame, getlocation().x-10f, getlocation().y-5f, 32, 32);
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


    @Override
    public Rectangle getCollider() {
        return box;
    }

    @Override
    public void onCollision(Collider other) {

        if (other instanceof PlayerController) {
            PlayerController playerController = (PlayerController) other;
            if (playerController.getCurrentState().startsWith("DOING_")) {
                getHeath().heal(20);
            }
        }
    }

    public boolean isCheckeating() {
        return checkeating;
    }

    public void setCheckeating(boolean checkeating) {
        this.checkeating = checkeating;
    }
}
