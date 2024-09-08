package io.github.Farm.UI;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import io.github.Farm.Gamemap;

public class Selecbox {
    private TextureRegion[] selectionBoxTextures;

    public Selecbox(Vector2 position, SpriteBatch batch, Gamemap map){
        selectionBoxTextures = new TextureRegion[4];
        selectionBoxTextures[0] = new TextureRegion(new Texture("UI/selectbox_tl.png"));
        selectionBoxTextures[1] = new TextureRegion(new Texture("UI/selectbox_tr.png"));
        selectionBoxTextures[2] = new TextureRegion(new Texture("UI/selectbox_bl.png"));
        selectionBoxTextures[3] = new TextureRegion(new Texture("UI/selectbox_br.png"));

        TiledMap tiledMap = map.getTiledMap();
        TiledMapTileLayer lay = (TiledMapTileLayer) tiledMap.getLayers().get("bandau");

        float tileWidth = lay.getTileWidth();
        float tileHeight = lay.getTileHeight();
        int tileX = (int) (position.x / tileWidth) + 2;
        int tileY = (int) (position.y / tileHeight) + 1;
        TiledMapTileLayer.Cell cell = lay.getCell(tileX, tileY);

        if (cell != null) {
            batch.begin();
            // Top-left corner
            batch.draw(selectionBoxTextures[0], tileX * tileWidth-1, tileY * tileHeight + tileHeight-2, tileWidth / 4, tileHeight / 4);
            // Top-right corner
            batch.draw(selectionBoxTextures[1], (tileX + 1) * tileWidth - tileWidth / 4+1, tileY * tileHeight + tileHeight-2, tileWidth / 4, tileHeight / 4);
            // Bottom-left corner
            batch.draw(selectionBoxTextures[2], tileX * tileWidth-1, tileY * tileHeight-1, tileWidth / 4, tileHeight / 4);
            // Bottom-right corner
            batch.draw(selectionBoxTextures[3], (tileX + 1) * tileWidth - tileWidth / 4+1, tileY * tileHeight-1, tileWidth / 4, tileHeight / 4);

            batch.end();
        }

        batch.setColor(1f, 1f, 1f, 1f);
    }

}