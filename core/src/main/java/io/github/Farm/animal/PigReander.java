package io.github.Farm.animal;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import io.github.Farm.Interface.Collider;
import io.github.Farm.Interface.RenderableEntity;
import io.github.Farm.player.PlayerController;

public class PigReander extends Pet implements RenderableEntity {
    private PigImageManager imageManager;
    private Animation<TextureRegion> currentAnimation;

    public PigReander(Vector2 location, long hungry) {
        super(location, hungry,100);
        imageManager = new PigImageManager();
        setcrencurrentState(PetState.IDLE_LEFT);
        settargetLocation(randomlocation(300,450,850,950));
    }

    public void update(float deltaTime) {
        if (getKnockbackDuration() > 0) {
            location().add(getKnockbackVelocity().cpy().scl(deltaTime));
            setKnockbackDuration(getKnockbackDuration() - deltaTime) ;
            if (getKnockbackDuration() <= 0) {
                getKnockbackVelocity().set(0, 0);
                setIsStopped(false);
            }
        }
        getbox().setPosition(location().x + 10, location().y + 5);

    }

    @Override
    public float getY() {
        return location().y;
    }

    @Override
    public void render(SpriteBatch batch, Camera camera) {
        getbox().setPosition(location().x, location().y);
//        shapeRenderer = new ShapeRenderer();
//        shapeRenderer.setProjectionMatrix(camera.combined);
//        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
//        shapeRenderer.setColor(Color.RED);
//        shapeRenderer.rect(box.x, box.y, box.width, box.height);
//        shapeRenderer.end();
        currentAnimation = imageManager.getAnimation(getCrencurrentState());
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        setStateTime(getStateTime()+ Gdx.graphics.getDeltaTime()); ;
        TextureRegion frame = currentAnimation.getKeyFrame(getStateTime(), true);
        batch.draw(frame, location().x-10f, location().y-5f, 32, 32);
        batch.end();

    }
    public void dispose(){
        if (imageManager != null) {
            imageManager.dispose(); // Giải phóng tài nguyên của BuffaloImageManager
        }
    }


    @Override
    public Rectangle getCollider() {
        return getbox();
    }
    @Override
    public void onCollision(Collider other) {

        if(other instanceof PlayerController){
            PlayerController playerController=(PlayerController) other;
            if(playerController.getCurrentState().startsWith("DOING_")) {
                getHeath().heal(20);
            }
        }
    }
}
