package io.github.Farm.Map;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class MapLoader {
    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;

    public MapLoader(String mapPath) {
        map = new TmxMapLoader().load(mapPath);
        renderer = new OrthogonalTiledMapRenderer(map);
    }

    public void renderMap(Camera camera) {
        renderer.setView((OrthographicCamera) camera);
        renderer.render();
    }

    public TiledMap getMap() {
        return map;
    }


    public void dispose() {
        map.dispose();
        renderer.dispose();
    }
}

