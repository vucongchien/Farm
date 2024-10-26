package io.github.Farm.Materials;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.Farm.Interface.Collider;
import io.github.Farm.Interface.RenderableEntity;
import io.github.Farm.inventory.ItemManager;
import io.github.Farm.player.PlayerController;
import io.github.Farm.ui.Other.LeaveHit;

public class MaterialsRender implements RenderableEntity,Collider{
    private final World world;
    private final Body body;
    private Rectangle collider;
    private Vector2 position;


    private MaterialType materialType;


    private float stateTime;
    private final MaterialImageManager materialImageManager;
    Animation<TextureRegion> animation ;
    private float colHeight,colWidth;

    private LeaveHit leaveHit;
    private boolean isHit;
    private boolean isRight;


    public MaterialsRender(World world, MaterialImageManager materialImageManager, MaterialType materialType, Vector2 position) {
        this.world = world;
        this.materialImageManager = materialImageManager;
        this.materialType = materialType;
        this.position = position;
        this.body = createBody(position, world, materialType);
        this.leaveHit=new LeaveHit();
        switch (materialType.toString()){
            case "tree":
                colHeight=30;
                colWidth=128/4-4;
                break;
            case"mushroom":
                colHeight=34;
                colWidth=128/4;
                break;
            case"rock":
                colHeight=24;
                colWidth=26;
                break;
            default:
                colHeight=10;
                colWidth=10;
        }

        this.collider=new Rectangle(body.getPosition().x-colWidth/2,body.getPosition().y-colHeight/4,colWidth,colHeight);
        this.stateTime = 0f;
        this.animation = materialImageManager.getAnimation(materialType);
    }

    private Body createBody(Vector2 startPosition, World world, MaterialType materialType) {
        float radius;

        switch (materialType.toString()){
            case "tree":
                radius=6f;
                break;
            case"mushroom":
                radius=1f;
                break;
            case"rock":
                radius=8f;
                break;
            default:
                radius=0f;
        }


        BodyDef bodyDef = new BodyDef();
        bodyDef.type = BodyDef.BodyType.StaticBody;
        bodyDef.position.set(startPosition);
        Body body = world.createBody(bodyDef);

        CircleShape shape = new CircleShape();
        shape.setRadius(radius);

        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 0.5f;
        fixtureDef.friction = 0.4f;
        fixtureDef.restitution = 0.1f;

        body.createFixture(fixtureDef);
        shape.dispose();
        return body;
    }




    @Override
    public float getY() {
        return body.getPosition().y;
    }

    @Override
    public void render(SpriteBatch batch, Camera camera) {

        batch.setProjectionMatrix(camera.combined);

        stateTime += Gdx.graphics.getDeltaTime();
        TextureRegion currentFrame = animation.getKeyFrame(stateTime, true);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        float drawX= position.x;
        float drawY= position.y;
        switch (materialType.toString()){
            case "tree":
                drawX-=(float) currentFrame.getTexture().getWidth()/8;
                drawY-=-(float)currentFrame.getTexture().getHeight()/4;
                break;
            case"rock":
                drawX-=16;
                drawY-=10;
                break;
        }





        batch.draw(currentFrame, drawX, drawY);
        batch.end();
        if(isHit){
            leaveHit.render(batch,isRight? position.x: position.x-colWidth*2, position.y);
        }
        if(leaveHit.isAnimationFinished()){
            isHit=false;
            leaveHit.reset();
            System.out.println("reset");
        }
    }

    public void dispose() {
       world.destroyBody(body);
    }

    @Override
    public Rectangle getCollider() {
        return collider;
    }

    @Override
    public void onCollision(io.github.Farm.Interface.Collider other) {
        if(other instanceof PlayerController){
            isRight= ((PlayerController) other).isFacingRight();
            ItemManager.getInstance().addItemVip("MATERIAL_"+ materialType.getTypeMaterial(),position.cpy().scl(1/16f),isRight,1);


            if(isHit){
                leaveHit.reset();
            }
            isHit=true;
        }
    }

    public Rectangle getRectangle(){
        return collider;
    }
}
