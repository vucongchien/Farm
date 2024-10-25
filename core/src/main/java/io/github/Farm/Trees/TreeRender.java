package io.github.Farm.Trees;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import io.github.Farm.Interface.Collider;
import io.github.Farm.Interface.RenderableEntity;
import io.github.Farm.inventory.ItemManager;
import io.github.Farm.player.PlayerController;
import io.github.Farm.ui.Other.LeaveHit;
import org.lwjgl.Sys;

public class TreeRender implements RenderableEntity,Collider{
    private final World world;
    private final Body body;
    private Rectangle collider;
    private Vector2 position;


    private TreeType treeType;


    private float stateTime;
    private final TreeImageManager treeImageManager;
    Animation<TextureRegion> animation ;
    private float colHeight,colWidth;

    private LeaveHit leaveHit;
    private boolean isHit;
    private boolean isRight;


    public TreeRender(World world, TreeImageManager treeImageManager, TreeType treeType, Vector2 position) {
        this.world = world;
        this.treeImageManager = treeImageManager;
        this.treeType = treeType;
        this.position = position;
        this.body = createBody(position, world,treeType);
        this.leaveHit=new LeaveHit();
        switch (treeType.toString()){
            case "tree":
                colHeight=30;
                colWidth=128/4-4;
                break;
            case"mushroom":
                colHeight=34;
                colWidth=128/4;
                break;
            default:
                colHeight=10;
                colWidth=10;
        }

        this.collider=new Rectangle(body.getPosition().x-colWidth/2,body.getPosition().y-colHeight/4,colWidth,colHeight);
        this.stateTime = 0f;
        this.animation = treeImageManager.getAnimation(treeType);
    }

    private Body createBody(Vector2 startPosition, World world,TreeType treeType) {
        float radius;

        switch (treeType.toString()){
            case "tree":
                radius=6f;
                break;
            case"mushroom":
                radius=1f;
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
        batch.draw(currentFrame, position.x-(float) currentFrame.getTexture().getWidth()/8, position.y-(float)currentFrame.getTexture().getHeight()/4);
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
            ItemManager.getInstance().addItemVip("MATERIAL_"+treeType.getTypeMaterial(),position.cpy().scl(1/16f),isRight,1);


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
