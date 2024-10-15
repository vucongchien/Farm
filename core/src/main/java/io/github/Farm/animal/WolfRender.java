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
import com.badlogic.gdx.utils.TimeUtils;
import io.github.Farm.Interface.Collider;
import io.github.Farm.Interface.Heath;
import io.github.Farm.Interface.RenderableEntity;
import io.github.Farm.animal.Buffalo.Buffalo;
import io.github.Farm.animal.Buffalo.BuffaloManager;
import io.github.Farm.player.PlayerController;

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




    public WolfRender(Vector2 location, boolean thulinh) {
        this.location = location;
        this.thulinh = thulinh;
        box=new Rectangle(location.x+15, location.y+15, 15,15);
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

    public Buffalo getPrey(){
        return prey;
    }

    public void setCrencurrentState(PetState stare){
        crencurrentState= stare;
    }

    public PetState getCrencurrentState(){return crencurrentState;}

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

    @Override
    public Rectangle getCollider() {
        return box;
    }

    @Override
    public void onCollision(Collider other) {
        if(other instanceof PlayerController){
            PlayerController playerController =(PlayerController) other;


            if (playerController.getCurrentState().startsWith("HIT_")) {
                isknockback=true;
                hp.damaged(20);
                crencurrentState=PetState.WALK_LEFT;
                System.out.println("ngu"+ hp.getCurrHp());
                attacked=true;
                timeattacked=0;

            }
        }
        if(other instanceof Buffalo){
            Buffalo buffalo=(Buffalo) other;
        }

    }
     private Vector2 knockback(){
        Vector2 endloction=new Vector2(location.x+50,location.y);
        return endloction.cpy().sub(location).nor();
     }
    @Override
    public float getY(){
        return location.y;
    }

    @Override
    public void render(SpriteBatch batch, Camera camera) {

        if(timeattacked==0){
            timeattacked=TimeUtils.millis();
        }
        if(TimeUtils.timeSinceMillis(timeattacked)>=10000){
            attacked=false;
        }

        if(isknockback){
            if(timeknockback==0){
                timeknockback= TimeUtils.millis();
            }
            if(TimeUtils.timeSinceMillis(timeknockback)<2000){
                crencurrentState=PetState.WALK_LEFT;
            }else{
                isknockback=false;
                crencurrentState=PetState.IDLE_LEFT;
                timeknockback=0;
                check=2;
            }
            location.add(knockback());
        }
        box.setPosition(getlocation().x, getlocation().y);
//        shapeRenderer=new ShapeRenderer();
//        shapeRenderer.setProjectionMatrix(camera.combined);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.rect(box.x, box.y, box.width, box.height);
//        shapeRenderer.end();
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
}

