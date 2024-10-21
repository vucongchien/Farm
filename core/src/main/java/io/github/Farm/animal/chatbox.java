package io.github.Farm.animal;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class chatbox {
    private String current;
    private SpriteBatch batch;
    private int width;
    private int height;

    public chatbox(){}



    public void setCurrent(String a,int width,int height){
        current=a;
        this.width=width;
        this.height=height;
    }

    public String getCurrent(){
        return current;
    }

    public void setBatch(SpriteBatch batch){
        this.batch=batch;
    }

    public void Render(Vector2 location){
        batch.draw(new Texture(current), location.x, location.y,width, height);
    }
}
