package io.github.Farm.ui.Other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/// HEAL BAR

public class RedBar {
    private SpriteBatch batch;
    private TextureRegion[] redBar;

    public RedBar(){

        redBar =new TextureRegion[7];
        redBar[0] =new TextureRegion(new Texture("UI/other/redbar_00.png"));
        redBar[1] =new TextureRegion(new Texture("UI/other/redbar_01.png"));
        redBar[2] =new TextureRegion(new Texture("UI/other/redbar_02.png"));
        redBar[3] =new TextureRegion(new Texture("UI/other/redbar_03.png"));
        redBar[4] =new TextureRegion(new Texture("UI/other/redbar_04.png"));
        redBar[5] =new TextureRegion(new Texture("UI/other/redbar_05.png"));
        redBar[6] =new TextureRegion(new Texture("UI/other/redbar_06.png"));

        batch=new SpriteBatch();
    }

    private int getIndex(float currHp,float maxHp){
        int tmp=(int)maxHp/6;

        int index =(int) (currHp/tmp);
        if(index<0) return 0;
        return Math.min(index, redBar.length-1);
    }

    public void render(Vector2 position, Camera camera, float currHp,float maxHp, float width, float height){
        int index=getIndex(currHp,maxHp);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(redBar[index],position.x-height,position.y+width ,width, height);
        batch.end();
    }
}
