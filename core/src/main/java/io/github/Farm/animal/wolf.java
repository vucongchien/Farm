package io.github.Farm.animal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.ArrayList;

import static java.lang.System.out;
import static java.lang.System.setOut;

public class wolf extends pests{
    private Animation<TextureRegion> currentAnimation;
    private boolean thulinh;
    private ShapeRenderer shapeRenderer;
    private Vector2 targetLocation=null;
    private int check=0;
    private int beside=1;
    //  dat 1 truoc 2 sau 3 trai 4 phai
    private float stateTime;
    private long starttime=0 ,endtime=0;
    private wolfImageManager imageManager;
    private Vector2 conmoitl;
    private Vector2 conmoi;
    private float so;
//    private boolean check=false;


    public wolf(Vector2 location, int hp,boolean thulinh){
        super(location,hp);
        imageManager = new wolfImageManager();
        this.thulinh=thulinh;
        so=(float) (Math.random()*10)+10f;
    }

    public Vector2  timgannhat(ArrayList<Buffalo> buffalo){
        if(buffalo.size()==0){
            return new Vector2(0,0);
        }else {
            Vector2 min = buffalo.get(0).getlocation();
            for (Buffalo a : buffalo) {
                if (Math.abs(a.getlocation().x - getlocation().x) < Math.abs(min.x - getlocation().x) || Math.abs(a.getlocation().y - getlocation().y) < Math.abs(min.y - getlocation().y)) {
                    min = a.getlocation();
                }
            }
            return min;
        }
    }



    public void hoatdong(ArrayList<wolf> c,ArrayList<Buffalo> a, SpriteBatch batch, float initialSize, float deltaTime, OrthographicCamera camera) {
    if(thulinh==true){
        if (a.size() == 0) {
            check = 2;
            setTrangthaitancong(false);
        } else {
            if (conmoitl == null) {
                conmoitl = timgannhat(a).cpy();
                conmoitl.x += 50;
                conmoitl.y += 50;
            }
        }
        if(getlocation().dst(850,1050) < 1f){
            if(starttime==0){
                starttime=TimeUtils.millis();
            }
            if(TimeUtils.timeSinceMillis(starttime)<30000){check=0;
                render(batch, initialSize, PetState.IDLE_RIGHT, camera);
            }else {
                starttime=0;
                check=1;
            }
        }else {
            starttime=0;
        }
        if(getlocation().dst(conmoitl) < 10f){
            if(endtime==0){
                endtime=TimeUtils.millis();
                setTrangthaitancong(true);
            }
            if (TimeUtils.timeSinceMillis(endtime) < 30000) {
                render(batch, initialSize, PetState.IDLE_RIGHT, camera);
                check=0;

            }else{
                setTrangthaitancong(false);
                check = 2;
                endtime = 0;
            }
        }else {
            endtime=0;
        }


        if(check==1){
                if (Math.abs(getlocation().x - conmoitl.x) > 1f) {
                    if (getlocation().x > conmoitl.x) {
                        getlocation().x -= 10f * deltaTime;
                        setLocation(getlocation().x, getlocation().y);
                        getbox().setPosition(getlocation().x, getlocation().y);
                        render(batch, initialSize, PetState.WALK_LEFT, camera);
                    } else if (getlocation().x < conmoitl.x) {
                        getlocation().x += 10f * deltaTime;
                        setLocation(getlocation().x, getlocation().y);
                        getbox().setPosition(getlocation().x, getlocation().y);
                        render(batch, initialSize, PetState.WALK_RIGHT, camera);
                    }
                } else if (Math.abs(getlocation().y - conmoitl.y) > 1f) {
                    if (getlocation().y > conmoitl.y) {
                        getlocation().y -= 10f * deltaTime;
                        setLocation(getlocation().x, getlocation().y);
                        getbox().setPosition(getlocation().x, getlocation().y);
                        render(batch, initialSize, PetState.WALK_FACE, camera);
                    } else if (getlocation().y < conmoitl.y) {
                        getlocation().y += 10f * deltaTime;
                        setLocation(getlocation().x, getlocation().y);
                        getbox().setPosition(getlocation().x, getlocation().y);
                        render(batch, initialSize, PetState.WALK_BACK, camera);
                    }


            }
        }
        if(check == 2){
            setTrangthaitancong(false);
            if (Math.abs(getlocation().x - 850) > 1f) {
                if (getlocation().x > 850) {
                    getlocation().x -= 10f * deltaTime;
                    setLocation(getlocation().x, getlocation().y);
                    getbox().setPosition(getlocation().x, getlocation().y);
                    render(batch, initialSize, PetState.WALK_LEFT, camera);
                } else if (getlocation().x < 850) {
                    getlocation().x += 10f * deltaTime;
                    setLocation(getlocation().x, getlocation().y);
                    getbox().setPosition(getlocation().x, getlocation().y);
                    render(batch, initialSize, PetState.WALK_RIGHT, camera);
                }
            } else if (Math.abs(getlocation().y - 850) > 1f) {
                if (getlocation().y > 850) {
                    getlocation().y -= 10f * deltaTime;
                    setLocation(getlocation().x, getlocation().y);
                    getbox().setPosition(getlocation().x, getlocation().y);
                    render(batch, initialSize, PetState.WALK_FACE, camera);
                } else if (getlocation().y < 850) {
                    getlocation().y += 10f * deltaTime;
                    setLocation(getlocation().x, getlocation().y);
                    getbox().setPosition(getlocation().x, getlocation().y);
                    render(batch, initialSize, PetState.WALK_BACK, camera);
                }
            }
            if (getlocation().dst(850, 1050) < 1f) {
                conmoitl = timgannhat(a).cpy();
                conmoitl.x += 50;
                conmoitl.y += 50;
                check = 1;
            }
        }

    }else{
        Vector2 chu=null;
        boolean tc = false;
        for(wolf b:c){
            if(b.thulinh==true){
                chu=b.getlocation();
                tc= b.gettrangthaitancon();
            }
        }
        conmoi=timgannhat(a);
        if(tc==false|| conmoi.x==0){
        if(chu!=null){
            if(Math.abs(getlocation().x - chu.x) < so&&Math.abs(getlocation().y - chu.y) < so){
                render(batch, initialSize, PetState.IDLE_RIGHT, camera);
            }
            if (Math.abs(getlocation().x - chu.x) > so) {
                if (getlocation().x > chu.x+so) {
                getlocation().x -= 10f * deltaTime;
                setLocation(getlocation().x, getlocation().y);
                getbox().setPosition(getlocation().x, getlocation().y);
                render(batch, initialSize, PetState.WALK_LEFT, camera);
                } else if (getlocation().x <chu.x+so) {
                getlocation().x += 10f * deltaTime;
                setLocation(getlocation().x, getlocation().y);
                getbox().setPosition(getlocation().x, getlocation().y);
                render(batch, initialSize, PetState.WALK_RIGHT, camera);
                }
            } else if (Math.abs(getlocation().y - chu.y) > so) {
                if (getlocation().y > chu.y+so) {
                getlocation().y -= 10f * deltaTime;
                setLocation(getlocation().x, getlocation().y);
                getbox().setPosition(getlocation().x, getlocation().y);
                render(batch, initialSize, PetState.WALK_FACE, camera);
                } else if (getlocation().y < chu.y+so) {
                getlocation().y += 10f * deltaTime;
                setLocation(getlocation().x, getlocation().y);
                getbox().setPosition(getlocation().x, getlocation().y);
                render(batch, initialSize, PetState.WALK_BACK, camera);
                }

            }
        }
        }
        else{
            int i=0;
            for(Buffalo d:a){
                i++;
                if (getbox().overlaps(d.getbox())) {
                    d.setmau();
                    float deltaX = this.getlocation().x - d.getlocation().x;
                    float deltaY = this.getlocation().y - d.getlocation().y;
                    float overlapX = getbox().width / 2 + d.getbox().width / 2 - Math.abs(deltaX);
                    float overlapY = getbox().height / 2 + d.getbox().height / 2 - Math.abs(deltaY);

                    if (overlapX > 0 && overlapY > 0) {
                        float pushFactor = 10f;

                        if (Math.abs(overlapX) < Math.abs(overlapY)) {
                            if (deltaX > 0) {
                                d.getlocation().x -= overlapX * pushFactor;
                            } else {
                                d.getlocation().x += overlapX * pushFactor;
                            }
                        } else {
                            if (deltaY > 0) {
                                d.getlocation().y -= overlapY * pushFactor;
                            } else {
                                d.getlocation().y += overlapY * pushFactor;
                            }
                        }

                        d.setLocation(d.getlocation().x,d.getlocation().y);
                        d.setBox(d.getlocation().x, d.getlocation().y);
                    }
                }
            }

            if (Math.abs(getlocation().x - conmoi.x) > 1f) {
                if (getlocation().x > conmoi.x) {
                    getlocation().x -= 10f * deltaTime;
                    setLocation(getlocation().x, getlocation().y);
                    getbox().setPosition(getlocation().x, getlocation().y);
                    render(batch, initialSize, PetState.WALK_LEFT, camera);
                } else if (getlocation().x < conmoi.x) {
                    getlocation().x += 10f * deltaTime;
                    setLocation(getlocation().x, getlocation().y);
                    getbox().setPosition(getlocation().x, getlocation().y);
                    render(batch, initialSize, PetState.WALK_RIGHT, camera);
                }
            } else if (Math.abs(getlocation().y - conmoi.y) > 1f) {
                if (getlocation().y > conmoi.y) {
                    getlocation().y -= 10f * deltaTime;
                    setLocation(getlocation().x, getlocation().y);
                    getbox().setPosition(getlocation().x, getlocation().y);
                    render(batch, initialSize, PetState.WALK_FACE, camera);
                } else if (getlocation().y < conmoi.y) {
                    getlocation().y += 10f * deltaTime;
                    setLocation(getlocation().x, getlocation().y);
                    getbox().setPosition(getlocation().x, getlocation().y);
                    render(batch, initialSize, PetState.WALK_BACK, camera);
                }
            }
        }
    }

}


    public void render(SpriteBatch batch, float initialSize, PetState x, OrthographicCamera camera) {
        getbox().setPosition(getlocation().x, getlocation().y);
        shapeRenderer=new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(getbox().x, getbox().y, getbox().width, getbox().height);
        shapeRenderer.end();
        currentAnimation = imageManager.getAnimation(x);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, true);
        batch.draw(frame, getlocation().x, getlocation().y, initialSize, initialSize);
        batch.end();
    }

}
