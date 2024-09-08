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


    private TextureRegion[] selectionBoxTextures;

    public hoatdong(Vector2 startPosition, float speed,Gamemap map) {
        super(startPosition, speed, map);

        selectionBoxTextures = new TextureRegion[4];
        selectionBoxTextures[0] = new TextureRegion(new Texture("UI/selectbox_tl.png"));
        selectionBoxTextures[1] = new TextureRegion(new Texture("UI/selectbox_tr.png"));
        selectionBoxTextures[2] = new TextureRegion(new Texture("UI/selectbox_bl.png"));
        selectionBoxTextures[3] = new TextureRegion(new Texture("UI/selectbox_br.png"));
    }
    public void laycitri(SpriteBatch batch, Gamemap map) {

        TiledMap tiledMap = map.getTiledMap();
        TiledMapTileLayer lay = (TiledMapTileLayer) tiledMap.getLayers().get("bandau");

        float tileWidth = lay.getTileWidth();
        float tileHeight = lay.getTileHeight();
        int tileX = (int) (getPosition().x / tileWidth) + 2;
        int tileY = (int) (getPosition().y / tileHeight) + 1;
        TiledMapTileLayer.Cell cell = lay.getCell(tileX, tileY);

        if (cell != null) {
            TextureRegion layhinhanh = cell.getTile().getTextureRegion();
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

        batch.setColor(1f, 1f, 1f, 1f); // Resetting color if necessary
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
