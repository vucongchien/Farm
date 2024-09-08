package io.github.Farm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;

public class hoatdong extends PlayerController {


    public hoatdong(Vector2 startPosition, float speed) {
        super(startPosition, speed);
    }
    public void laycitri(SpriteBatch batch,Gamemap map){
        TiledMap tiledMap = map.getTiledMap();
        TiledMapTileLayer lay=(TiledMapTileLayer) tiledMap.getLayers().get("bandau");

        float tileWidth = lay.getTileWidth();
        float tileHeight = lay.getTileHeight();
        int tileX = (int) (getPosition().x / tileWidth)+2;
        int tileY = (int) (getPosition().y / tileHeight)+1;
        TiledMapTileLayer.Cell cell = lay.getCell(tileX, tileY);
        TextureRegion layhinhanh = cell.getTile().getTextureRegion();
        batch.begin();
        batch.setColor(0f,0f,0f,2.5f);
        batch.draw(layhinhanh, tileX * tileWidth, tileY * tileHeight, tileWidth, tileHeight);
        batch.end();
        batch.setColor(1f, 1f, 1f, 1f);
    }
    public void plow(Gamemap map) {
        if (Gdx.input.isKeyPressed(Input.Keys.F)) {

            TiledMap tiledMap = map.getTiledMap();
            TiledMapTileLayer doi = (TiledMapTileLayer) tiledMap.getLayers().get("dat");
            TiledMapTileLayer hoan = (TiledMapTileLayer) tiledMap.getLayers().get("bandau");
            float tileWidth = doi.getTileWidth();
            float tileHeight = doi.getTileHeight();
            int tileX = (int) (getPosition().x / tileWidth)+2;
            int tileY = (int) (getPosition().y / tileHeight)+1;
            TiledMapTileLayer.Cell cellToCopy = doi.getCell(tileX, tileY);
            if (cellToCopy != null) {
                TiledMapTileLayer.Cell tmp = new TiledMapTileLayer.Cell();
                tmp.setTile(cellToCopy.getTile());
                hoan.setCell(tileX, tileY, tmp);
            }
        }
    }
}
