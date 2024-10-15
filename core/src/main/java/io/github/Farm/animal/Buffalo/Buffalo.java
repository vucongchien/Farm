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


public class Buffalo extends Pet implements RenderableEntity {
    private long mau;
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
    private Vector2 targetLocation = null;
    private int direction;
    private Vector2 previousLocation;
    private int beside = 1;
    //  dat 1 truoc 2 sau 3 trai 4 phai
    ShapeRenderer shapeRenderer;
    // vẽ hoạt động
    private boolean check = false;
    private long stopTime = 0;
    private PetState crencurrentState;
    //////////////////////////////////////////


    ///// vacham
    private long collisionStopTime = 0;
    private boolean isStopped = false;

    public Buffalo(Vector2 location, long hungry, boolean killed) {
        super(location, hungry, killed);
        box = new Rectangle(getlocation().x + 10f, getlocation().y + 5f, 10, 15);
        imageManager = new BuffaloImageManager();
        direction = 1;
        this.previousLocation = new Vector2(location);
        height = box.getHeight();
        width = box.getWidth();
        mau = hungry;
        crencurrentState =PetState.IDLE_FACE;
    }


    public  void setStopTime(long a){stopTime=a;}

    public long getStopTime() {return stopTime;}

    public void settargetLocation(Vector2 a){targetLocation=a;}

    public Vector2 getTargetLocation(){return targetLocation;}

    public  boolean getcheck(){return check;}

    public void setcheck(boolean a){check=a;}

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

    public void setBoxSize(float width, float height) {
        box.setSize(width, height);
    }

    public Rectangle getbox() {
        return box;
    }

    public Vector2 getStartpoint() {
        return startpoint;
    }

    public void setDirection(int a) {
        direction = a;
    }

    public int getdirection() {
        return direction;
    }

    public void setmau() {
        mau -= 25;
    }

    public long getmau() {
        return mau;
    }

    public void setcrencurrentState(PetState crencurrentState){this.crencurrentState=crencurrentState;}



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
        float deltaTime = Gdx.graphics.getDeltaTime();
        if (check && targetLocation!=null) {
            if (getlocation().x < targetLocation.x) {
                setDirection(4);
                getlocation().x += 10f * deltaTime;
                setLocation(getlocation().x, getlocation().y);
                box.setPosition(getlocation().x, getlocation().y);
                setBoxSize(height,width);
                setcrencurrentState(PetState.WALK_RIGHT);
                beside = 4;
            } else if (getlocation().x > targetLocation.x) {
                setDirection(3);
                getlocation().x -= 10f * deltaTime;
                setLocation(getlocation().x, getlocation().y);
                box.setPosition(getlocation().x, getlocation().y);
                setBoxSize(height,width);
                setcrencurrentState(PetState.WALK_LEFT);
                beside = 3;
            }
        }

        if(targetLocation!=null) {
            if (Math.abs(getlocation().x - targetLocation.x) < 1f) {
                if (getlocation().y < targetLocation.y) {
                    setDirection(2);
                    getlocation().y += 10f * deltaTime;
                    setLocation(getlocation().x, getlocation().y);
                    box.setPosition(getlocation().x, getlocation().y);
                    setBoxSize(width, height);
                    setcrencurrentState(PetState.WALK_BACK);
                    check = false;
                    beside = 2;
                } else if (getlocation().y > targetLocation.y) {
                    setDirection(1);
                    getlocation().y -= 10f * deltaTime;
                    setLocation(getlocation().x, getlocation().y);
                    box.setPosition(getlocation().x, getlocation().y);
                    setBoxSize(width, height);
                    setcrencurrentState(PetState.WALK_FACE);
                    check = false;
                    beside = 1;
                }
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
//    public void vavham(Rectangle a) {
//        if (getbox().overlaps(a)) {
//            if (!isStopped) {
//                isStopped = true;
//                collisionStopTime = TimeUtils.millis();
//            }
//        }
//    }
//    public void ve(SpriteBatch batch, int initialSize, float deltaTime, OrthographicCamera camera) {
//
//        if (gethungry() <= 0) {
//            switch (beside) {
//                case 1:
//                    render(batch, initialSize, PetState.SLEEP_FACE, camera);
//                    break;
//                case 2:
//                    render(batch, initialSize, PetState.SLEEP_BACK, camera);
//                    break;
//                case 3:
//                    render(batch, initialSize, PetState.SLEEP_LEFT, camera);
//                    break;
//                default:
//                    render(batch, initialSize, PetState.SLEEP_RIGHT, camera);
//                    break;
//            }
//            return;
//        }
//        if (isStopped) {
//            if (TimeUtils.timeSinceMillis(collisionStopTime) >= 5000) {
//                isStopped = false;
//                targetLocation = randomlocation();
//                stopTime = TimeUtils.millis();
//                check = true;
//            } else {
//                switch (beside) {
//                    case 1:
//                        render(batch, initialSize, PetState.IDLE_FACE, camera);
//                        break;
//                    case 2:
//                        render(batch, initialSize, PetState.IDLE_BACK, camera);
//                        break;
//                    case 3:
//                        render(batch, initialSize, PetState.IDLE_LEFT, camera);
//                        break;
//                    default:
//                        render(batch, initialSize, PetState.IDLE_RIGHT, camera);
//                        break;
//                }
//                return;
//            }
//        }
//
//        if (targetLocation == null || getlocation().epsilonEquals(targetLocation, 1f)) {
//            if (stopTime == 0) {
//                stopTime = TimeUtils.millis();
//            }
//
//            if (TimeUtils.timeSinceMillis(stopTime) >= 5000 && gethungry() > 0) {
//                targetLocation = randomlocation();
//                stopTime = 0;
//                check = true;
//            } else {
//                switch (beside) {
//                    case 1:
//                        render(batch, initialSize, PetState.IDLE_FACE, camera);
//                        break;
//                    case 2:
//                        render(batch, initialSize, PetState.IDLE_BACK, camera);
//                        break;
//                    case 3:
//                        render(batch, initialSize, PetState.IDLE_LEFT, camera);
//                        break;
//                    default:
//                        render(batch, initialSize, PetState.IDLE_RIGHT, camera);
//                        break;
//                }
//                return;
//            }
//        }
//
//
//        if (check) {
//            if (getlocation().x < targetLocation.x) {
//                setDirection(4);
//                getlocation().x += 10f * deltaTime;
//                setLocation(getlocation().x, getlocation().y);
//                box.setPosition(getlocation().x, getlocation().y);
//                setBoxSize(height, width);
//                render(batch, initialSize, PetState.WALK_RIGHT, camera);
//                beside = 4;
//            } else if (getlocation().x > targetLocation.x) {
//                setDirection(3);
//                getlocation().x -= 10f * deltaTime;
//                setLocation(getlocation().x, getlocation().y);
//                box.setPosition(getlocation().x, getlocation().y);
//                setBoxSize(height, width);
//                render(batch, initialSize, PetState.WALK_LEFT, camera);
//                beside = 3;
//            }
//        }
//
//        if (Math.abs(getlocation().x - targetLocation.x) < 1f) {
//            if (getlocation().y < targetLocation.y) {
//                setDirection(2);
//                getlocation().y += 10f * deltaTime;
//                setLocation(getlocation().x, getlocation().y);
//                box.setPosition(getlocation().x, getlocation().y);
//                setBoxSize(width, height);
//                render(batch, initialSize, PetState.WALK_BACK, camera);
//                check = false;
//                beside = 2;
//            } else if (getlocation().y > targetLocation.y) {
//                setDirection(1);
//                getlocation().y -= 10f * deltaTime;
//                setLocation(getlocation().x, getlocation().y);
//                box.setPosition(getlocation().x, getlocation().y);
//                setBoxSize(width, height);
//                render(batch, initialSize, PetState.WALK_FACE, camera);
//                check = false;
//                beside = 1;
//            }
//        }
//    }
//    public void movelocation(Vector2 targetLocation) {
//        float deltaTime = Gdx.graphics.getDeltaTime();
//        if (Math.abs(getlocation().x - targetLocation.x) > 1f) {
//            if (getlocation().x < targetLocation.x) {
//                setDirection(4);
//                getlocation().x += 10f * deltaTime;
//                setLocation(getlocation().x, getlocation().y);
//                box.setPosition(getlocation().x, getlocation().y);
//                setBoxSize(height, width);
//                setcrencurrentState(PetState.WALK_RIGHT);
//                beside = 4;
//            } else if (getlocation().x > targetLocation.x) {
//                setDirection(3);
//                getlocation().x -= 10f * deltaTime;
//                setLocation(getlocation().x, getlocation().y);
//                box.setPosition(getlocation().x, getlocation().y);
//                setBoxSize(height, width);
//
//                setcrencurrentState(PetState.WALK_LEFT);
//                beside = 3;
//            }
//
//            setLocation(getlocation().x, getlocation().y);
//            box.setPosition(getlocation().x, getlocation().y);
//            setBoxSize(height, width);
//        }else if (Math.abs(getlocation().y - targetLocation.y) > 1f) {
//
//            if (getlocation().y < targetLocation.y) {
//                setDirection(2);
//                getlocation().y += 10f * deltaTime;
//                setLocation(getlocation().x, getlocation().y);
//                box.setPosition(getlocation().x, getlocation().y);
//                setBoxSize(width, height);
//                setcrencurrentState(PetState.WALK_BACK);
//                check = false;
//                beside = 2;
//            } else if (getlocation().y > targetLocation.y) {
//                setDirection(1);
//                getlocation().y -= 10f * deltaTime;
//                setLocation(getlocation().x, getlocation().y);
//                box.setPosition(getlocation().x, getlocation().y);
//                setBoxSize(width, height);
//                setcrencurrentState(PetState.WALK_FACE);
//                check = false;
//                beside = 1;
//            }
//
//        }
//
//     }


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
        batch.draw(frame, getlocation().x, getlocation().y, 32, 32);
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

}
