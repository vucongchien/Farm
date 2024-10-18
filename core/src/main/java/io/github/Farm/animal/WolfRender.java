package io.github.Farm.animal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.math.Rectangle;
import io.github.Farm.Interface.Collider;
import io.github.Farm.Interface.Heath;
import io.github.Farm.Interface.RenderableEntity;
import io.github.Farm.animal.Buffalo.Buffalo;
import io.github.Farm.animal.Buffalo.BuffaloManager;
import io.github.Farm.player.PlayerController;

import java.util.concurrent.ThreadLocalRandom;

import static com.badlogic.gdx.math.MathUtils.random;

public class WolfRender implements Collider, RenderableEntity {
    private boolean thulinh;
    private Vector2 location;
    private Rectangle box;
    private boolean trangthaitancong=false;
    private Buffalo prey;
    private PetState crencurrentState;
    private float   distancefrombossx;
    private float   distancefrombossy;
    private ShapeRenderer shapeRenderer;
    private Animation<TextureRegion> currentAnimation;
    private WolfImageManager imageManager;
    private  float stateTime =0;
    private  Heath hp;
    private boolean isknockback;
    private long timeknockback;
    private boolean attacked;
    private long timeattacked;
    private int check = 2;
//    private Vector2 preyphayeright;
//    private Vector2 preyphayerleft;
    private boolean idright;
    private boolean animationattack;




    //...........hitall
    private boolean hit;
    private boolean kill;
    private float cooldown;



    public WolfRender(Vector2 location, boolean thulinh) {
        this.location = location;
        this.thulinh = thulinh;
        box=new Rectangle(location.cpy().x-10, location.cpy().y-15, 15,15);
        crencurrentState= PetState.IDLE_LEFT;
        imageManager = new WolfImageManager();
        this.hp=new Heath(100);
    }

    public boolean getattacked(){return attacked;}

    public void reset(Vector2 a){
        hp.heal(100);
        location.x=a.x+distancefrombossx;
        location.y=a.y+distancefrombossy;
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

    public Buffalo getPrey(){
        return prey;
    }

    public void setCrencurrentState(PetState stare){
        crencurrentState= stare;
    }


    public void setprey(BuffaloManager buffaloManager){
        if(buffaloManager.getBuffaloManager().size()==0){
            prey =null;
        }else {
            Buffalo min = buffaloManager.getBuffaloManager().get(0);
            for (Buffalo x : buffaloManager.getBuffaloManager()) {
                if (x.getlocation().dst(getlocation())<min.getlocation().dst(getlocation())) {
                    min = x;
                }
            }
            prey = min;
        }
    }

    public void setanimationattack(boolean a){animationattack=a;}


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
    private Vector2 knockback(PlayerController playerController){
        if(playerController.isFacingRight()) {
            Vector2 endloction = new Vector2(location.x + 50, location.y);
            return endloction.cpy().sub(location).nor();
        }else{
            Vector2 endloction = new Vector2(location.x - 50, location.y);
            return endloction.cpy().sub(location).nor();
        }
     }

    @Override
    public float getY(){
        return location.y;
    }

    @Override
    public void render(SpriteBatch batch, Camera camera) {
        stateTime += Gdx.graphics.getDeltaTime();
        currentAnimation = imageManager.getAnimation(crencurrentState);
        batch.begin();
        TextureRegion frame = currentAnimation.getKeyFrame(stateTime, true);
        batch.draw(frame, getlocation().x-18, getlocation().y-22, 32, 32);
        batch.end();
//        shapeRenderer = new ShapeRenderer();
//        shapeRenderer.setProjectionMatrix(camera.combined);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.rect(box.x, box.y, box.width, box.height);
//        shapeRenderer.end();
    }

    public float getcooldown(){return cooldown;}

    public void setcooldown(){cooldown-=0.1f;}

    public void recoverycooldown(){cooldown=0.6f;}

    public void dispose(){
        if (imageManager != null) {
            imageManager.dispose(); // Giải phóng tài nguyên của BuffaloImageManager
        }
        if (shapeRenderer != null) {
            shapeRenderer.dispose(); // Giải phóng ShapeRenderer nếu đã được khởi tạo
        }
        if (currentAnimation != null) {
            Object[] keyFrames = currentAnimation.getKeyFrames();
            for (Object keyFrame : keyFrames) {
                if (keyFrame instanceof TextureRegion) {
                    TextureRegion frame = (TextureRegion) keyFrame;
                    Texture texture = frame.getTexture();
                    if (texture != null) {
                        texture.dispose(); // Giải phóng Texture
                    }
                }
            }
        }

    }

    public boolean gethit(){return hit;}

    public void checkidright(PlayerController playerController){
        if(Math.abs(location.x-playerController.getPosition().x)<11f&&Math.abs(location.y-playerController.getPosition().y)<11f&&playerController.getPosition().x<location.x){
            idright=true;
            crencurrentState=PetState.IDLE_LEFT;
        }else {
            idright=false;
            crencurrentState=PetState.IDLE_RIGHT;
        }
    }

//    public boolean getidright(){return idright;}

    public void checkHit(Collider other){
        if(other instanceof PlayerController){
            PlayerController playerController= (PlayerController) other;
            if(Math.abs(location.x-playerController.getPosition().x)<100f&&Math.abs(location.y-playerController.getPosition().y)<100f){
                hit=true;
            }else{
                hit=false;
            }
        }

    }

    public void setKill(boolean a){kill=a;}

    public boolean getKill(){return kill;}

}

