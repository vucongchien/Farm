package io.github.Farm.inventory;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import io.github.Farm.Interface.Collider;
import io.github.Farm.Interface.RenderableEntity;
import io.github.Farm.player.PlayerController;

import java.util.concurrent.ThreadLocalRandom;

///item o tren mat dat
public class Item implements RenderableEntity, Collider, Disposable {
    private String name;
    private Rectangle collider;
    private Texture texture;

    private Vector2 position;
    private Vector2 endPosition;
    private Vector2 velocity;

    private boolean canTake;

    private float remainingTime=20f;

    public Item(String name,Vector2 position){
        canTake=false;
        this.name = name;
        this.position=position;
        this.endPosition=position;
        this.remainingTime=2;

        velocity=new Vector2(1,1);

        String[] tmp = name.split("_");
        String TYPE = tmp[0];
        String NAME = tmp[1];
        this.texture = new Texture("UI/Item/" + TYPE + "/" + NAME + ".png");

        collider = new Rectangle(endPosition.x * 16 + (16 / 2f) - (texture.getWidth() / 2f), endPosition.y * 16 + (16 / 2f - 3), texture.getWidth(), texture.getHeight());
    }

    public Item(String name, Vector2 position, boolean isPlayerFacingRight) {
        canTake=true;
        this.name = name;
        this.position=position;
        velocity=generateRandomPositionInSemiCircle(position,isPlayerFacingRight);

        String[] tmp = name.split("_");
        String TYPE = tmp[0];
        String NAME = tmp[1];
        this.texture = new Texture("UI/Item/" + TYPE + "/" + NAME + ".png");
        collider = new Rectangle(endPosition.x * 16 + (16 / 2f) - (texture.getWidth() / 2f), endPosition.y * 16 + (16 / 2f - 3), texture.getWidth(), texture.getHeight());
    }

    private Vector2 generateRandomPositionInSemiCircle(Vector2 position, boolean isPlayerFacingRight) {
        float radius = ThreadLocalRandom.current().nextFloat(3, 5);
        float angle;

        if (isPlayerFacingRight) {
            angle = (float) Math.toRadians(ThreadLocalRandom.current().nextFloat(-60, 60));
        } else {
            angle = (float) Math.toRadians(ThreadLocalRandom.current().nextFloat(120, 240));
        }

        float xOffset = (float) Math.cos(angle) * radius;
        float yOffset = (float) Math.sin(angle) * radius;

        endPosition = new Vector2(position.x + xOffset, position.y + yOffset);

        Vector2 direction = endPosition.cpy().sub(position).nor();

        return direction;
    }


    private void update(){
        remainingTime-= Gdx.graphics.getDeltaTime();
        if (remainingTime<0){
            ItemManager.getInstance().getItemList().remove(this);
        }

        if (position.dst(endPosition) < 0.5f)
            return;

        position.add(velocity);
    }

    public Vector2 getPosition() {
        return new Vector2(position.x*16 + (16 / 2f) - (texture.getWidth() / 2f),position.y*16+ (16 / 2f-3));
    }

    public float getWidth(){
        return texture.getWidth();
    }

    public float getHeight(){
        return texture.getHeight();
    }

    @Override
    public float getY() {
        return endPosition.y;
    }

    @Override
    public void render(SpriteBatch batch, Camera camera) {
        update();

        batch.begin();

        float tileSize = 16f;
        float renderX = position.x*16 + (tileSize / 2f) - (texture.getWidth() / 2f);
        float renderY = position.y*16+ (tileSize / 2f-3);
        if(canTake) {
            batch.draw(texture, renderX, renderY);
        }
        else {
            batch.draw(texture,renderX,renderY, (float) texture.getWidth() /2,(float) texture.getHeight() /2);
        }

        batch.end();


    }

    @Override
    public Rectangle getCollider() {
        return collider;
    }

    @Override
    public void onCollision(Collider other) {
        if(other instanceof PlayerController){
            Inventory.getInstance().addItem(name,1);
        }
    }

    @Override
    public void dispose() {
        texture.dispose();
    }

    public boolean isCanTake() {
        return canTake;
    }

    public void setCanTake(boolean canTake) {
        this.canTake = canTake;
    }
}
