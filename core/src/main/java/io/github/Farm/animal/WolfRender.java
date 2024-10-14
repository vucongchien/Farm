package io.github.Farm.animal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.math.Rectangle;
import io.github.Farm.Interface.Collider;
import io.github.Farm.Interface.RenderableEntity;
import io.github.Farm.animal.Buffalo.Buffalo;
import io.github.Farm.animal.Buffalo.BuffaloManager;
import io.github.Farm.player.PlayerController;

import static com.badlogic.gdx.math.MathUtils.random;

public class WolfRender implements Collider, RenderableEntity {
    private boolean thulinh;
    private Vector2 location;
    private int hp;
    private Rectangle box;
    private boolean trangthaitancong=false;
    private Vector2 prey;
    private PetState crencurrentState;
    private float   distancefrombossx;
    private float   distancefrombossy;
    private ShapeRenderer shapeRenderer;
    private Animation<TextureRegion> currentAnimation;
    private WolfImageManager imageManager;
    private  float stateTime =0;



    public WolfRender(Vector2 location, boolean thulinh) {
        this.location = location;
        this.thulinh = thulinh;
        box=new Rectangle(location.x, location.y, 15,15);
        hp=100;
        crencurrentState= PetState.IDLE_LEFT;
        imageManager = new WolfImageManager();
    }

    public float getdistancefrombossx(){
        return distancefrombossx;
    }

    public void setDistancefrombossx(float a){distancefrombossx=a;}

    public float getDistancefrombossy() {
        return distancefrombossy;
    }

    public void setDistancefrombossy(float a){distancefrombossy=a;}

    public void sethp(){hp-=20;}

    public int gethp(){return hp;}

    public void setBox(float x,float y){
        box.setPosition(x,y);
    }

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

    public Vector2 getPrey(){
        return prey;
    }

    public void setCrencurrentState(PetState stare){
        crencurrentState= stare;
    }

    public PetState getCrencurrentState(){return crencurrentState;}

    public void setprey(BuffaloManager buffaloManager){

        if(buffaloManager.getBuffaloManager().size()==0){
            prey =new Vector2(0,0);
        }else {
            Vector2 min = buffaloManager.getBuffaloManager().get(0).getlocation();
            for (Buffalo x : buffaloManager.getBuffaloManager()) {
                if (x.getlocation().dst(getlocation())<min.dst(getlocation())) {
                    min = x.getlocation();
                }
            }
            prey = min;
        }
    }

    @Override
    public Rectangle getCollider() {
        return box;
    }

    @Override
    public void onCollision(Collider other) {
        if(other instanceof PlayerController){
            PlayerController playerController =(PlayerController) other;
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

        box.setPosition(getlocation().x, getlocation().y);
        shapeRenderer=new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        shapeRenderer.setColor(Color.RED);
        shapeRenderer.rect(box.x, box.y, box.width, box.height);
        shapeRenderer.end();
        currentAnimation = imageManager.getAnimation(crencurrentState);
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

