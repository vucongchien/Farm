package io.github.Farm.ui.Other;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

public class TimeCoolDown {
    private SpriteBatch batch;
    private TextureRegion[] GreenBar;
    private TextureRegion[] RedBar;
    private TextureRegion[] BlueBar;
    private float timeToChangeState;
    private float countTime;



    public TimeCoolDown(){

        GreenBar=new TextureRegion[7];
        GreenBar[0] =new TextureRegion(new Texture("UI/other/greenbar_00.png"));
        GreenBar[1] =new TextureRegion(new Texture("UI/other/greenbar_01.png"));
        GreenBar[2] =new TextureRegion(new Texture("UI/other/greenbar_02.png"));
        GreenBar[3] =new TextureRegion(new Texture("UI/other/greenbar_03.png"));
        GreenBar[4] =new TextureRegion(new Texture("UI/other/greenbar_04.png"));
        GreenBar[5] =new TextureRegion(new Texture("UI/other/greenbar_05.png"));
        GreenBar[6] =new TextureRegion(new Texture("UI/other/greenbar_06.png"));
//
//        RedBar=new TextureRegion[7];
//        RedBar[0] =new TextureRegion(new Texture("UI/other/redbar_00.png"));
//        RedBar[1] =new TextureRegion(new Texture("UI/other/redbar_01.png"));
//        RedBar[2] =new TextureRegion(new Texture("UI/other/redbar_02.png"));
//        RedBar[3] =new TextureRegion(new Texture("UI/other/redbar_03.png"));
//        RedBar[4] =new TextureRegion(new Texture("UI/other/redbar_04.png"));
//        RedBar[5] =new TextureRegion(new Texture("UI/other/redbar_05.png"));
//        RedBar[6] =new TextureRegion(new Texture("UI/other/redbar_06.png"));
//
//        BlueBar=new TextureRegion[7];
//        BlueBar[0] =new TextureRegion(new Texture("UI/other/bluebar_00.png"));
//        BlueBar[1] =new TextureRegion(new Texture("UI/other/bluebar_01.png"));
//        BlueBar[2] =new TextureRegion(new Texture("UI/other/bluebar_02.png"));
//        BlueBar[3] =new TextureRegion(new Texture("UI/other/bluebar_03.png"));
//        BlueBar[4] =new TextureRegion(new Texture("UI/other/bluebar_04.png"));
//        BlueBar[5] =new TextureRegion(new Texture("UI/other/bluebar_05.png"));

        countTime=0f;
        batch=new SpriteBatch();
    }

    private int getIndex(float timeDelay){
        this.timeToChangeState =timeDelay/7;
        countTime+= Gdx.graphics.getDeltaTime();

        int index = (int) (countTime / timeToChangeState);;
        index = Math.min(index, GreenBar.length - 1);

        if (index == GreenBar.length - 1) {
            countTime = 0;
            System.out.println("da ve xong");
        }

        return index;
    }

    public void renderGreenBar( Vector2 position,float timeDelay,float width,float height){
        int index=getIndex(timeDelay);


        batch.begin();
        batch.draw(GreenBar[index],position.x,position.y -330,width, height);
        batch.end();


    }





}
