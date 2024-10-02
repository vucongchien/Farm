package io.github.Farm.UI.Other;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class TimeCoolDown {
    private TextureRegion[] GreenBar;
    private float TimeToChangeState;
    private Vector2 position;



    public TimeCoolDown(){

        GreenBar=new TextureRegion[7];
        GreenBar[0] =new TextureRegion(new Texture("UI/greenbar_00.png"));
        GreenBar[1] =new TextureRegion(new Texture("UI/greenbar_01.png"));
        GreenBar[2] =new TextureRegion(new Texture("UI/greenbar_02.png"));
        GreenBar[3] =new TextureRegion(new Texture("UI/greenbar_03.png"));
        GreenBar[4] =new TextureRegion(new Texture("UI/greenbar_04.png"));
        GreenBar[5] =new TextureRegion(new Texture("UI/greenbar_05.png"));
        GreenBar[6] =new TextureRegion(new Texture("UI/greenbar_06.png"));
    }
    public void render(SpriteBatch batch,Vector2 position, float Time, float TimeMax, TiledMap map){
        this.position=position;
        TimeToChangeState=TimeMax/7;
        TiledMap tiledMap = map;
        TiledMapTileLayer lay = (TiledMapTileLayer) tiledMap.getLayers().get("bandau");

        float tileWidth = lay.getTileWidth();
        float tileHeight = lay.getTileHeight();
        int tileX = (int) (position.x / tileWidth) + 2;
        int tileY = (int) (position.y / tileHeight) + 1;
        TiledMapTileLayer.Cell cell = lay.getCell(tileX, tileY);

        if(cell!=null) {
            batch.begin();
            int index = (int) (Time / TimeToChangeState);
            index = Math.min(index, GreenBar.length - 1);
            System.out.println(index+" "+Time);
            batch.draw(GreenBar[index],tileX * tileWidth+tileWidth*1/4,tileY * tileHeight+tileHeight*3/2 ,tileWidth / 2, tileHeight / 4);
            batch.end();
        }
    }


}
