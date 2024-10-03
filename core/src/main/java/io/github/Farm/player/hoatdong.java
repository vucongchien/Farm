package io.github.Farm.player;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import io.github.Farm.Gamemap;
import io.github.Farm.UI.TimeCoolDown;

public class hoatdong extends PlayerController {

    private float TimeToPlow = 3.0f;
    private float StartPlow=0f;
    private TimeCoolDown timeCoolDownBar;
    private Vector2 lastPosition;
    private boolean Plowing =false;

    public hoatdong(Vector2 startPosition, float speed,Gamemap map) {
        super(startPosition, speed, map);
        lastPosition = new Vector2(startPosition);

    }
    public void plow(Gamemap map,float deltaTime) {
        if (Gdx.input.isKeyPressed(Input.Keys.F)) {
            Plowing =true;
            StartPlow+= deltaTime;
            if (!getPosition().epsilonEquals(lastPosition, 0.1f)) {
                StartPlow=0f;
                lastPosition.set(getPosition());
            }
            if(StartPlow>=TimeToPlow) {
                StartPlow=0f;
                TiledMap tiledMap = map.getTiledMap();
                TiledMapTileLayer doi = (TiledMapTileLayer) tiledMap.getLayers().get("dat");
                TiledMapTileLayer hoan = (TiledMapTileLayer) tiledMap.getLayers().get("bandau");


                float tileWidth = doi.getTileWidth();
                float tileHeight = doi.getTileHeight();
                int tileX = (int) (getPosition().x / tileWidth) + 2;
                int tileY = (int) (getPosition().y / tileHeight) + 1;
                TiledMapTileLayer.Cell cellToCopy = doi.getCell(tileX, tileY);


                if (cellToCopy != null) {
                    TiledMapTileLayer.Cell tmp = new TiledMapTileLayer.Cell();
                    tmp.setTile(cellToCopy.getTile());
                    hoan.setCell(tileX, tileY, tmp);
                }
            }
        }
        else {
            Plowing =false;
        }
    }

    public boolean isPlowing() {
        return Plowing;
    }
    public float getTimeToPlow(){
        return TimeToPlow;
    }
    public float getStartPlow(){
        return StartPlow;
    }

    @Override
    public void update(float deltaTime) {
        plow(getMap(),deltaTime);
        super.update(deltaTime);
    }
}
