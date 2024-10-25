package io.github.Farm.ui.Other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/// TIME COOL DOWN

public class GreenBar {
    private SpriteBatch batch;
    private TextureRegion[] greenBar;



    public GreenBar(){

        greenBar =new TextureRegion[7];
        greenBar[0] =new TextureRegion(new Texture("UI/other/greenbar_00.png"));
        greenBar[1] =new TextureRegion(new Texture("UI/other/greenbar_01.png"));
        greenBar[2] =new TextureRegion(new Texture("UI/other/greenbar_02.png"));
        greenBar[3] =new TextureRegion(new Texture("UI/other/greenbar_03.png"));
        greenBar[4] =new TextureRegion(new Texture("UI/other/greenbar_04.png"));
        greenBar[5] =new TextureRegion(new Texture("UI/other/greenbar_05.png"));
        greenBar[6] =new TextureRegion(new Texture("UI/other/greenbar_06.png"));

//        BlueBar=new TextureRegion[7];
//        BlueBar[0] =new TextureRegion(new Texture("UI/other/bluebar_00.png"));
//        BlueBar[1] =new TextureRegion(new Texture("UI/other/bluebar_01.png"));
//        BlueBar[2] =new TextureRegion(new Texture("UI/other/bluebar_02.png"));
//        BlueBar[3] =new TextureRegion(new Texture("UI/other/bluebar_03.png"));
//        BlueBar[4] =new TextureRegion(new Texture("UI/other/bluebar_04.png"));
//        BlueBar[5] =new TextureRegion(new Texture("UI/other/bluebar_05.png"));
        batch=new SpriteBatch();
    }

    private int getIndex(float time,float timeMax){
        float tmp=timeMax/6;

        int index =(int) (time/tmp);
        if(index<0) return 0;
        return Math.min(index, greenBar.length-1);
    }

    public void render(Vector2 position, Camera camera, float time,float timeMax, float width, float height){
        int index=getIndex(time,timeMax);
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        batch.draw(greenBar[index],position.x-height,position.y+width ,width, height);
        batch.end();
    }






}
