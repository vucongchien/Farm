package io.github.Farm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Map {
    private OrthographicCamera camera;
    private TiledMap tiledMap;
    private OrthogonalTiledMapRenderer mapRenderer;

    public void create(String path) {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        tiledMap = new TmxMapLoader().load(path);
        mapRenderer = new OrthogonalTiledMapRenderer(tiledMap);
    }

    public void render() {
        mapRenderer.setView(camera);
        mapRenderer.render();
    }

    public void setCamera(OrthographicCamera camera) {
        this.camera = camera;
    }

    public void dispose() {
        tiledMap.dispose();
        mapRenderer.dispose();
    }
}
