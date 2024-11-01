package io.github.Farm.ui.Other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;

public class ExpressionManager implements Disposable {
    private Expression currentExpression;
    private SpriteBatch batch;


    private float dropPositionY = 0f;
    private float timeElapsed = 0f;

    public ExpressionManager()  {
        currentExpression = Expression.NULL;
        batch=new SpriteBatch();
    }

    public void setExpression(Expression expression) {
        this.currentExpression = expression;
    }

    public void render(Vector2 position, Camera camera,float dropSpeed,float dropDuration) {
        if (currentExpression == Expression.NULL && currentExpression.getTextureRegion() == null) return;


        dropPositionY-=dropSpeed*Gdx.graphics.getDeltaTime();
        timeElapsed+=Gdx.graphics.getDeltaTime();


        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(currentExpression.getTextureRegion(), position.x+5, position.y+10+ dropPositionY,currentExpression.getTextureRegion().getRegionWidth(),currentExpression.getTextureRegion().getRegionHeight());
        batch.end();

        if (timeElapsed > dropDuration) {
            dropPositionY = 0f;
            timeElapsed = 0f;
        }

    }

    @Override
    public void dispose() {
        batch.dispose();
    }
}
