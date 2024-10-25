package io.github.Farm.ui.Other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class LeaveHit {
    private final Animation<TextureRegion> animation;
    private float stateTime;

    public LeaveHit() {
        this.animation = loadAnimation();
        this.stateTime = 0f;
    }

    private Animation<TextureRegion> loadAnimation() {
        Array<TextureRegion> frames = new Array<>();
        for (int i = 1; i <= 10; i++) {
            Texture texture = new Texture(Gdx.files.internal("UI/LeaveHit/leaves_hit (" + i + ").png"));
            frames.add(new TextureRegion(texture));
        }

        return new Animation<>(0.1f, frames, Animation.PlayMode.NORMAL);
    }

    public void render(SpriteBatch batch, float x, float y) {
        batch.begin();
        stateTime += Gdx.graphics.getDeltaTime();

        TextureRegion currentFrame = animation.getKeyFrame(stateTime);

        batch.draw(currentFrame, x, y);
        batch.end();
    }

    public boolean isAnimationFinished() {
        return animation.isAnimationFinished(stateTime);
    }

    public void reset() {
        stateTime = 0f;
    }
}
