package io.github.Farm.ui.Other;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class ExpressionManager {
    private Expression currentExpression;
    private SpriteBatch batch;

    public ExpressionManager() {
        currentExpression = Expression.NULL;
        batch=new SpriteBatch();
    }

    public void setExpression(Expression expression) {
        this.currentExpression = expression;
    }

    public void render(Vector2 position, Camera camera) {
        if (currentExpression == Expression.NULL && currentExpression.getTextureRegion() == null) return;
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(currentExpression.getTextureRegion(), position.x+5, position.y+2,5,7);
        batch.end();
    }
}
