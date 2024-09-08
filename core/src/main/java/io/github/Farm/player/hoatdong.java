package io.github.Farm.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import io.github.Farm.Gamemap;

public class hoatdong extends PlayerController {




    public hoatdong(Vector2 startPosition, float speed,Gamemap map) {
        super(startPosition, speed, map);


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

    @Override
    public void update(float deltaTime) {
        plow(getMap());
        super.update(deltaTime);
    }
}
