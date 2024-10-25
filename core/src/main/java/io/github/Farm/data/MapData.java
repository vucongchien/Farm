package io.github.Farm.data;

import java.util.ArrayList;
import java.util.List;

public class MapData {
    public List<TileData> tiles = new ArrayList<>();

    public static class TileData {
        public int x, y;
        public String layerName;
        public int tileId;
        public String tileProperty;
    }
}
