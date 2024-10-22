package io.github.Farm.animal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;
import io.github.Farm.Interface.Collider;
import io.github.Farm.Interface.Heath;
import io.github.Farm.animal.Buffalo.Buffalo;
import io.github.Farm.animal.Chicken.ChickenRender;
import io.github.Farm.animal.Pig.PigReander;

import java.util.concurrent.ThreadLocalRandom;

public abstract class Pet implements Collider {
    private Vector2 location;
    private long hungry;
    private final Heath heath;
    private Rectangle box;

    private float stateTime;
    private Vector2 targetLocation ;
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
    //........
    //.....check di chuyen
    private boolean checkmove;
    private boolean checkeating;
    private long timeeating;
    //........readdata

    public Pet(Vector2 location, long hungry, int a) {
        heath = new Heath(a);
        this.hungry = hungry;
        this.location = location;
    }


    public void setBox(Rectangle a){box=a;}

    public boolean isCheckeating() {
        return checkeating;
    }

    public void setCheckeating(boolean checkeating) {
        this.checkeating = checkeating;
    }

    public long getTimeeating() {
        return timeeating;
    }

    public void setTimeeating(long timeeating) {
        this.timeeating = timeeating;
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

    public void setLeft(boolean a){isLeft=a;}

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

    public float getKnockbackDuration(){return knockbackDuration;}


    public void setcrencurrentState(PetState crencurrentState){this.crencurrentState=crencurrentState;}

    public void setCheckmove(boolean checkmove) {
        this.checkmove = checkmove;
    }

    public boolean getCheckmove(){return checkmove;}

    public Vector2 location() {
        return location;
    }

    public void setLocation(float tmpx, float tmpy) {
        this.location = new Vector2(tmpx, tmpy);
    }

    public void sethungry(long a) {
        hungry -= a;
    }

    public long gethungry() {
        return hungry;
    }

    public Heath getHeath() {
        return heath;
    }

    public Vector2 getKnockbackVelocity() {
        return knockbackVelocity;
    }

    public float getStateTime() {
        return stateTime;
    }

    public void setStateTime(float stateTime) {
        this.stateTime = stateTime;
    }

    public PetState getCrencurrentState() {
        return crencurrentState;
    }

    public void setCrencurrentState(PetState crencurrentState) {
        this.crencurrentState = crencurrentState;
    }

    public Vector2 randomlocation(int x1,int x2,int y1,int y2) {
        int randomIntx;
        int randomInty;
//        if(startpoint==null){
//             randomIntx = ThreadLocalRandom.current().nextInt(0,1920);
//             randomInty= ThreadLocalRandom.current().nextInt(0,1080);
//        }else {
//             randomIntx = ThreadLocalRandom.current().nextInt((int)(getStartpoint().x),(int)(getStartpoint().x+100));
//             randomInty = ThreadLocalRandom.current().nextInt((int)(getStartpoint().y),(int)(getStartpoint().y+100));
        //}
        randomIntx = ThreadLocalRandom.current().nextInt(x1, x2);
        randomInty = ThreadLocalRandom.current().nextInt(y1, y2);
        return new Vector2(randomIntx, randomInty);
    }

    public void movelocation(Pet other) {
        if (other instanceof Buffalo) {
            Buffalo no = (Buffalo) other;
            if (!no.getCheckmove()) {
                float deltaTime = Gdx.graphics.getDeltaTime();
                if (Math.abs(location.x - no.getTargetLocation().x) > 1f) {
                    if (location.x < no.getTargetLocation().x) {
                        location.x += 10f * deltaTime;
                        setLocation(location.x, location.y);
                        no.getbox().setPosition(location.x, location.y);
                        no.setcrencurrentState(PetState.WALK_RIGHT);
                        no.setLeft(false);
                    } else {
                        location().x -= 10f * deltaTime;
                        setLocation(location.x, location.y);
                        no.getbox().setPosition(location.x, location.y);
                        no.setcrencurrentState(PetState.WALK_LEFT);
                        no.setLeft(true);
                    }
                }
                if (Math.abs(location().y - no.getTargetLocation().y) > 1f) {
                    if (location.y < no.getTargetLocation().y) {
                        location.y += 10f * deltaTime;
                        setLocation(location.x, location.y);
                        no.getbox().setPosition(location.x, location.y);
                        if (no.getLeft()) {
                            no.setcrencurrentState(PetState.WALK_LEFT);
                        } else {
                            no.setcrencurrentState(PetState.WALK_RIGHT);
                        }

                    } else {
                        location.y -= 10f * deltaTime;
                        setLocation(location.x, location.y);
                        no.getbox().setPosition(location.x, location.y);
                        if (no.getLeft()) {
                            no.setcrencurrentState(PetState.WALK_LEFT);
                        } else {
                            no.setcrencurrentState(PetState.WALK_RIGHT);
                        }
                    }
                }
            }
        }
        if(other instanceof PigReander){
            PigReander no = (PigReander) other;
            if (!no.getCheckmove()) {
                float deltaTime = Gdx.graphics.getDeltaTime();
                if (Math.abs(location.x - no.getTargetLocation().x) > 1f) {
                    if (location.x < no.getTargetLocation().x) {
                        location.x += 10f * deltaTime;
                        setLocation(location.x, location.y);
                        no.getbox().setPosition(location.x, location.y);
                        no.setcrencurrentState(PetState.WALK_RIGHT);
                        no.setLeft(false);
                    } else {
                        location().x -= 10f * deltaTime;
                        setLocation(location.x, location.y);
                        no.getbox().setPosition(location.x, location.y);
                        no.setcrencurrentState(PetState.WALK_LEFT);
                        no.setLeft(true);
                    }
                }
                if (Math.abs(location().y - no.getTargetLocation().y) > 1f) {
                    if (location.y < no.getTargetLocation().y) {
                        location.y += 10f * deltaTime;
                        setLocation(location.x, location.y);
                        no.getbox().setPosition(location.x, location.y);
                        if (no.getLeft()) {
                            no.setcrencurrentState(PetState.WALK_LEFT);
                        } else {
                            no.setcrencurrentState(PetState.WALK_RIGHT);
                        }

                    } else {
                        location.y -= 10f * deltaTime;
                        setLocation(location.x, location.y);
                        no.getbox().setPosition(location.x, location.y);
                        if (no.getLeft()) {
                            no.setcrencurrentState(PetState.WALK_LEFT);
                        } else {
                            no.setcrencurrentState(PetState.WALK_RIGHT);
                        }
                    }
                }
            }
        }
        if(other instanceof ChickenRender){
            ChickenRender no = (ChickenRender) other;
            if (!no.getCheckmove()) {
                float deltaTime = Gdx.graphics.getDeltaTime();
                if (Math.abs(location.x - no.getTargetLocation().x) > 1f) {
                    if (location.x < no.getTargetLocation().x) {
                        location.x += 10f * deltaTime;
                        setLocation(location.x, location.y);
                        no.getbox().setPosition(location.cpy().x-10f, location.cpy().y-10f);
                        no.setcrencurrentState(PetState.WALK_RIGHT);
                        no.setLeft(false);
                    } else {
                        location().x -= 10f * deltaTime;
                        setLocation(location.x, location.y);
                        no.getbox().setPosition(location.cpy().x-10f, location.cpy().y-10f);
                        no.setcrencurrentState(PetState.WALK_LEFT);
                        no.setLeft(true);
                    }
                }
                if (Math.abs(location().y - no.getTargetLocation().y) > 1f) {
                    if (location.y < no.getTargetLocation().y) {
                        location.y += 10f * deltaTime;
                        setLocation(location.x, location.y);
                        no.getbox().setPosition(location.cpy().x-10f, location.cpy().y-10f);
                        if (no.getLeft()) {
                            no.setcrencurrentState(PetState.WALK_LEFT);
                        } else {
                            no.setcrencurrentState(PetState.WALK_RIGHT);
                        }

                    } else {
                        location.y -= 10f * deltaTime;
                        setLocation(location.x, location.y);
                        no.getbox().setPosition(location.cpy().x-10f, location.cpy().y-10f);
                        if (no.getLeft()) {
                            no.setcrencurrentState(PetState.WALK_LEFT);
                        } else {
                            no.setcrencurrentState(PetState.WALK_RIGHT);
                        }
                    }
                }
            }
        }

    }

    public void knockBack(Pet a, Pet b){
        if(a instanceof Buffalo && b instanceof Buffalo){
            Buffalo buffalo1=(Buffalo) a;
            Buffalo buffalo2=(Buffalo) b;
                Vector2 wolfPosition = buffalo1.location();
                Vector2 direction = new Vector2(buffalo2.location()).sub(wolfPosition).nor();
                float knockbackForce = 20f;
                buffalo2.setKnockbackVelocity(direction.scl(knockbackForce));
                buffalo2.setKnockbackDuration(0.2f);
                buffalo2.getbox().setPosition(buffalo2.location().x+10, buffalo2.location().y+5);
        }
        if(a instanceof PigReander && b instanceof PigReander){
            PigReander pig1=(PigReander) a;
            PigReander pig2=(PigReander) b;
            Vector2 wolfPosition = pig1.location();
            Vector2 direction = new Vector2(pig2.location()).sub(wolfPosition).nor();
            float knockbackForce = 20f;
            pig2.setKnockbackVelocity(direction.scl(knockbackForce));
            pig2.setKnockbackDuration(0.2f);
            pig2.getbox().setPosition(pig2.location().x, pig2.location().y);
        }
        if(a instanceof ChickenRender && b instanceof ChickenRender){
            ChickenRender chicken1=(ChickenRender) a;
            ChickenRender chicken2=(ChickenRender) b;
            Vector2 wolfPosition = chicken1.location();
            Vector2 direction = new Vector2(chicken2.location()).sub(wolfPosition).nor();
            float knockbackForce = 20f;
            chicken2.setKnockbackVelocity(direction.scl(knockbackForce));
            chicken2.setKnockbackDuration(0.2f);
            chicken2.getbox().setPosition(chicken2.location().x-10f, chicken2.location().y-10f);
        }
    }

    public void collide(Pet a,Pet b) {
        if(a instanceof Buffalo && b instanceof Buffalo) {
            Buffalo buffalo1=(Buffalo) a;
            Buffalo buffalo2=(Buffalo) b;
            if (buffalo1.getbox().overlaps(buffalo2.getbox())) {
                if (!buffalo1.getIsStopped()) {
                    buffalo1.setIsStopped(true);
                    buffalo1.setCollisionStopTime(TimeUtils.millis());
                }
                if (!buffalo2.getIsStopped()) {
                    buffalo2.setIsStopped(true);
                    buffalo2.setCollisionStopTime(TimeUtils.millis());
                }
                if (buffalo1.getKnockbackDuration() <= 0 && buffalo2.getKnockbackDuration() <= 0) {
                    knockBack(buffalo1, buffalo2);
                }
            }
        }
        if(a instanceof PigReander && b instanceof PigReander){
            PigReander pig1=(PigReander) a;
            PigReander pig2=(PigReander) b;
            if (pig1.getbox().overlaps(pig2.getbox())) {
                if (!pig1.getIsStopped()) {
                    pig1.setIsStopped(true);
                    pig1.setCollisionStopTime(TimeUtils.millis());
                }
                if (!pig2.getIsStopped()) {
                    pig2.setIsStopped(true);
                    pig2.setCollisionStopTime(TimeUtils.millis());
                }
                if (pig1.getKnockbackDuration() <= 0 && pig2.getKnockbackDuration() <= 0) {
                    knockBack(pig1, pig2);
                }
            }
        }
        if(a instanceof ChickenRender && b instanceof ChickenRender){
            ChickenRender chicken1=(ChickenRender) a;
            ChickenRender chicken2=(ChickenRender) b;
            if (chicken1.getbox().overlaps(chicken2.getbox())) {
                if (!chicken1.getIsStopped()) {
                    chicken1.setIsStopped(true);
                    chicken1.setCollisionStopTime(TimeUtils.millis());
                }
                if (!chicken2.getIsStopped()) {
                    chicken2.setIsStopped(true);
                    chicken2.setCollisionStopTime(TimeUtils.millis());
                }
                if (chicken1.getKnockbackDuration() <= 0 && chicken2.getKnockbackDuration() <= 0) {
                    knockBack(chicken1, chicken2);
                }
            }
        }
    }

    public void ativate(Pet a,int x1,int x2,int y1,int y2){
        if(a instanceof Buffalo) {
            Buffalo buffalo=(Buffalo) a;
            if(buffalo.isCheckeating()){
                if(buffalo.getTimeeating()==0){
                    buffalo.setTimeeating(TimeUtils.millis());
                }
                if(TimeUtils.timeSinceMillis(buffalo.getTimeeating())>2000){
                    buffalo.setCheckeating(false);
                    buffalo.setTimeeating(0);
                }else {
                    if (buffalo.getLeft()) {
                        buffalo.setcrencurrentState(PetState.EAT_LEFT);
                    } else {
                        buffalo.setcrencurrentState(PetState.EAT_RIGHT);
                    }
                }
            }else {
                    if (buffalo.gethungry() <= 0) {
                        if (buffalo.getLeft()) {
                            buffalo.setcrencurrentState(PetState.SLEEP_LEFT);
                        } else {
                            buffalo.setcrencurrentState(PetState.SLEEP_RIGHT);
                        }
                } else {
                    if (buffalo.getIsStopped()) {
                        if (TimeUtils.timeSinceMillis(buffalo.getCollisionStopTime()) >= 5000) {
                            buffalo.setIsStopped(false);
                            buffalo.settargetLocation(buffalo.randomlocation(x1,x2,y1,y2));
                        } else {
                            if (buffalo.getLeft()) {
                                buffalo.setcrencurrentState(PetState.IDLE_LEFT);
                            } else {
                                buffalo.setcrencurrentState(PetState.IDLE_RIGHT);
                            }
                        }
                    } else {
                        if (buffalo.getTargetLocation() == null || buffalo.location().epsilonEquals(buffalo.getTargetLocation(), 1f)) {
                            if (buffalo.getStopTime() == 0) {
                                buffalo.setStopTime(TimeUtils.millis());
                            }
                            if (TimeUtils.timeSinceMillis(buffalo.getStopTime()) >= 5000 && buffalo.gethungry() > 0) {
                                buffalo.settargetLocation(buffalo.randomlocation(x1,x2,y1,y2));
                                buffalo.setStopTime(0);

                            } else {
                                if (buffalo.getLeft()) {
                                    buffalo.setcrencurrentState(PetState.IDLE_LEFT);
                                } else {
                                    buffalo.setcrencurrentState(PetState.IDLE_RIGHT);
                                }
                            }
                        } else {
                            movelocation(buffalo);
                        }

                    }
                }
            }
        }
        if(a instanceof PigReander){
            PigReander pig=(PigReander) a;
            if(pig.isCheckeating()){
                if(pig.getTimeeating()==0){
                    pig.setTimeeating(TimeUtils.millis());
                }
                if(TimeUtils.timeSinceMillis(pig.getTimeeating())>2000){
                    pig.setCheckeating(false);
                    pig.setTimeeating(0);
                }else {
                    if (pig.getLeft()) {
                        pig.setcrencurrentState(PetState.EAT_LEFT);
                    } else {
                        pig.setcrencurrentState(PetState.EAT_RIGHT);
                    }
                }
            }else {
                if (pig.gethungry() <= 0) {
                    if (pig.getLeft()) {
                        pig.setcrencurrentState(PetState.SLEEP_LEFT);
                    } else {
                        pig.setcrencurrentState(PetState.SLEEP_RIGHT);
                    }
                } else {
                    if (pig.getIsStopped()) {
                        if (TimeUtils.timeSinceMillis(pig.getCollisionStopTime()) >= 5000) {
                            pig.setIsStopped(false);
                            pig.settargetLocation(pig.randomlocation(x1,x2,y1,y2));
                        } else {
                            if (pig.getLeft()) {
                                pig.setcrencurrentState(PetState.IDLE_LEFT);
                            } else {
                                pig.setcrencurrentState(PetState.IDLE_RIGHT);
                            }
                        }
                    } else {
                        if (pig.getTargetLocation() == null || pig.location().epsilonEquals(pig.getTargetLocation(), 1f)) {
                            if (pig.getStopTime() == 0) {
                                pig.setStopTime(TimeUtils.millis());
                            }
                            if (TimeUtils.timeSinceMillis(pig.getStopTime()) >= 5000 && pig.gethungry() > 0) {
                                pig.settargetLocation(pig.randomlocation(x1,x2,y1,y2));
                                pig.setStopTime(0);

                            } else {
                                if (pig.getLeft()) {
                                    pig.setcrencurrentState(PetState.IDLE_LEFT);
                                } else {
                                    pig.setcrencurrentState(PetState.IDLE_RIGHT);
                                }
                            }
                        } else {
                            movelocation(pig);
                        }

                    }
                }
            }
        }
        if(a instanceof  ChickenRender){
            ChickenRender chicken=(ChickenRender) a;
            if(chicken.isCheckeating()){
                if(chicken.getTimeeating()==0){
                    chicken.setTimeeating(TimeUtils.millis());
                }
                if(TimeUtils.timeSinceMillis(chicken.getTimeeating())>2000){
                    chicken.setCheckeating(false);
                    chicken.setTimeeating(0);
                }else {
                    if (chicken.getLeft()) {
                        chicken.setcrencurrentState(PetState.EAT_LEFT);
                    } else {
                        chicken.setcrencurrentState(PetState.EAT_RIGHT);
                    }
                }
            }else {
                if (chicken.gethungry() <= 0) {
                    if (chicken.getLeft()) {
                        chicken.setcrencurrentState(PetState.SLEEP_LEFT);
                    } else {
                        chicken.setcrencurrentState(PetState.SLEEP_RIGHT);
                    }
                } else {
                    if (chicken.getIsStopped()) {
                        if (TimeUtils.timeSinceMillis(chicken.getCollisionStopTime()) >= 5000) {
                            chicken.setIsStopped(false);
                            chicken.settargetLocation(chicken.randomlocation(x1,x2,y1,y2));
                        } else {
                            if (chicken.getLeft()) {
                                chicken.setcrencurrentState(PetState.IDLE_LEFT);
                            } else {
                                chicken.setcrencurrentState(PetState.IDLE_RIGHT);
                            }
                        }
                    } else {
                        if (chicken.getTargetLocation() == null || chicken.location().epsilonEquals(chicken.getTargetLocation(), 1f)) {
                            if (chicken.getStopTime() == 0) {
                                chicken.setStopTime(TimeUtils.millis());
                            }
                            if (TimeUtils.timeSinceMillis(chicken.getStopTime()) >= 5000 && chicken.gethungry() > 0) {
                                chicken.settargetLocation(chicken.randomlocation(x1,x2,y1,y2));
                                chicken.setStopTime(0);

                            } else {
                                if (chicken.getLeft()) {
                                    chicken.setcrencurrentState(PetState.IDLE_LEFT);
                                } else {
                                    chicken.setcrencurrentState(PetState.IDLE_RIGHT);
                                }
                            }
                        } else {
                            movelocation(chicken);
                        }

                    }
                }
            }
        }
    }


   
    public abstract Rectangle getCollider();

    public abstract void onCollision(Collider other);
}


