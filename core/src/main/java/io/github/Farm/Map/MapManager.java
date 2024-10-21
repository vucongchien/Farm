package io.github.Farm.Map;

import com.badlogic.gdx.maps.MapGroupLayer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TiledMapTileSet;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.utils.XmlWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;

import java.util.HashSet;
import java.util.Set;

public class MapManager {
    private TiledMap map;
    private boolean nightLayerVisible=false;


    public MapManager(TiledMap map) {
        this.map = map;
        setNightLayerVisible(false);
    }

    public String getTileClass(Vector2 positonInMap) {
        TiledMapTileLayer landLayer = (TiledMapTileLayer) map.getLayers().get("land");

        TiledMapTileLayer.Cell cell = landLayer.getCell((int) positonInMap.x,(int) positonInMap.y);
        if (cell != null) {
            TiledMapTile tile = cell.getTile();
            return (String) tile.getProperties().get("type");
        }

        return null;
    }

    public void changeTile(Vector2 postionInMap, String newTileClass,String layer) {

        TiledMapTileLayer tileLayer = (TiledMapTileLayer) map.getLayers().get(layer);

        TiledMapTileLayer.Cell cell = tileLayer.getCell((int) postionInMap.x,(int) postionInMap.y);

        if (cell == null) {
            cell = new TiledMapTileLayer.Cell();
            tileLayer.setCell((int) postionInMap.x, (int) postionInMap.y, cell);
        }


        for (TiledMapTileSet tileSet : map.getTileSets()) {
            for (TiledMapTile tile : tileSet) {

                String tileClass = (String) tile.getProperties().get("type");
                if (newTileClass.equals(tileClass)) {

                    cell.setTile(tile);
                    return;
                }
            }
        }

    }

    public void setNightLayerVisible(boolean visible) {
        TiledMapTileLayer nightLayer =(TiledMapTileLayer) map.getLayers().get("NIGHT");
        if (nightLayer != null) {
            nightLayer.setVisible(visible);
        }

    }

    public void saveMapWithChanges(TiledMap map, String newFilePath, Set<String> changedTiles) {
        try {
            // Khởi tạo XmlWriter với FileWriter
            XmlWriter writer = new XmlWriter(new FileWriter(newFilePath));

            // Bắt đầu ghi phần tử map
            writer.element("map")
                .attribute("version", "1.10")
                .attribute("tiledversion", "1.11.0")
                .attribute("orientation", "orthogonal")
                .attribute("renderorder", "right-down")
                .attribute("width", 144)
                .attribute("height", 80)
                .attribute("tilewidth", 16)
                .attribute("tileheight", 16)
                .attribute("infinite", 0);

            // Ghi các tileset
            writer.element("tileset").attribute("firstgid", 1).attribute("source", "tileset.tsx").pop();
            writer.element("tileset").attribute("firstgid", 4097).attribute("source", "tileset_vippp.tsx").pop();
            writer.element("tileset").attribute("firstgid", 8193).attribute("source", "Other.tsx").pop();

            // Ghi layer
            TiledMapTileLayer layer = (TiledMapTileLayer) map.getLayers().get("land");
            writer.element("layer")
                .attribute("id", 1)
                .attribute("name", "land")
                .attribute("width", layer.getWidth())
                .attribute("height", layer.getHeight())
                .element("data").attribute("encoding", "csv");

            // Ghi dữ liệu các ô (tiles) đã thay đổi
            for (int y = 0; y < layer.getHeight(); y++) {
                for (int x = 0; x < layer.getWidth(); x++) {
                    String pos = x + "," + y;
                    if (changedTiles.contains(pos)) {
                        TiledMapTileLayer.Cell cell = layer.getCell(x, y);
                        if (cell != null && cell.getTile() != null) {
                            writer.text(cell.getTile().getId() + ",");
                        } else {
                            writer.text("0,");  // Tile trống
                        }
                    } else {
                        writer.text("0,");  // Giữ nguyên các ô không thay đổi
                    }
                }
                writer.text("\n");
            }

            writer.pop();  // Đóng thẻ "data"
            writer.pop();  // Đóng thẻ "layer"
            writer.pop();  // Đóng thẻ "map"

            // Đóng XmlWriter
            writer.close();
            System.out.println("Các thay đổi đã được lưu thành công vào " + newFilePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public TiledMap getTiledMap() {
        return map;
    }



}
