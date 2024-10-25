//package io.github.Farm.Way;
//
//import com.badlogic.gdx.maps.tiled.TiledMap;
//import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
//import com.badlogic.gdx.math.Vector2;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class GoogleMap {
//    private int[][] googlemap;
//    private TiledMapTileLayer layer;
//    private Vector2 diemmu;
//    private List<Vector2> thamdo;
//    private ArrayList<Vector2> waytrue;
//    private ArrayList<Vector2> thamdodiemmu;
//
//    public GoogleMap(TiledMap map) {
//        layer = (TiledMapTileLayer) map.getLayers().get(0);
//        googlemap = new int[layer.getWidth()][layer.getHeight()];
//        thamdo = new ArrayList<>();
//        waytrue=new ArrayList<>();
//        thamdodiemmu =new ArrayList<>();
//    }
//
//    public void setGooglemap(TiledMap map) {
//        TiledMapTileLayer block = (TiledMapTileLayer) map.getLayers().get("walls");
//
//        for (int j = 0; j < layer.getHeight(); j++) { // Duyệt theo hàng trước (y)
//            for (int i = 0; i < layer.getWidth(); i++) { // Duyệt theo từng ô trong hàng (x)
//                TiledMapTileLayer.Cell cell = block.getCell(i, j);
//                if (cell != null) {
//                    googlemap[i][j] = 1; // Đánh dấu ô bị chặn
//                } else {
//                    googlemap[i][j] = 0; // Ô trống
//                }
//            }
//        }
//    }
//
//
//    public Vector2 getBlockedCells(int x1, int y1, int x2, int y2) {
//        int dx = Math.abs(x2 - x1);
//        int dy = Math.abs(y2 - y1);
//        int sx = (x1 < x2) ? 1 : -1; // Hướng di chuyển theo x
//        int sy = (y1 < y2) ? 1 : -1; // Hướng di chuyển theo y
//        int err = dx - dy; // Độ lỗi
//
//        while (true) {
//            if (googlemap[x1][y1] == 1) {
//                return new Vector2(x1 * layer.getTileWidth(), y1 * layer.getTileHeight());
//            }
//            if (x1 == x2 && y1 == y2) {
//                break;
//            }
//            int err2 = err * 2;
//            if (err2 > -dy) {
//                err -= dy;
//                x1 += sx;
//            }
//            if (err2 < dx) {
//                err += dx;
//                y1 += sy;
//            }
//        }
//        return null;
//    }
//
//    public int[][] getGooglemap() {
//        return googlemap;
//    }
//
////    public void DFS(int x1, int y1, int x2, int y2) {
////        waytrue.add(new Vector2(x1 * layer.getTileWidth(), y1 * layer.getTileHeight()));
////        while (true) {
////            System.out.println("chúa cu");
////            if (!getBlockedCells(x2, y2, x1, y1)) {
////                System.out.println(getBlockedCells(x2, y2, x1, y1));
////                waytrue.add(new Vector2(x1 * layer.getTileWidth(), y1 * layer.getTileHeight()));
////                break;
////            }
////            if (y1 > 0 && googlemap[x1][y1 - 1] == 0) {
////                System.out.println("chúa cu1");
////                y1 -= 1;
////                thamdo.add(new Vector2(x1 * layer.getTileWidth(), y1 * layer.getTileHeight()));
////
////                if (getBlockedCells(x1, y1, (int) waytrue.get(waytrue.size() - 1).x / layer.getWidth(),
////                    (int) waytrue.get(waytrue.size() - 1).y / layer.getHeight())) {
////                    waytrue.add(new Vector2(x1 * layer.getTileWidth(), (y1 + 1) * layer.getTileHeight()));
////                }
////            } else if (x1 > 0 && googlemap[x1 - 1][y1] == 0) {
////                System.out.println("chúa cu2");
////                x1 -= 1;
////                thamdo.add(new Vector2(x1 * layer.getTileWidth(), y1 * layer.getTileHeight()));
////
////                if (getBlockedCells(x1, y1, (int) waytrue.get(waytrue.size() - 1).x / layer.getWidth(),
////                    (int) waytrue.get(waytrue.size() - 1).y / layer.getHeight())) {
////                    waytrue.add(new Vector2((x1 + 1) * layer.getTileWidth(), y1 * layer.getTileHeight()));
////                }
////            } else if (y1 < layer.getHeight() - 1 && googlemap[x1][y1 + 1] == 0) {
////                System.out.println("chúa cu4");
////                y1 += 1;
////                thamdo.add(new Vector2(x1 * layer.getTileWidth(), y1 * layer.getTileHeight()));
////
////                if (getBlockedCells(x1, y1, (int) waytrue.get(waytrue.size() - 1).x / layer.getWidth(),
////                    (int) waytrue.get(waytrue.size() - 1).y / layer.getHeight())) {
////                    waytrue.add(new Vector2(x1 * layer.getTileWidth(), (y1 - 1) * layer.getTileHeight()));
////                }
////            } else if (x1 < layer.getWidth() - 1 && googlemap[x1 + 1][y1] == 0) {
////                System.out.println("chúa cu5");
////                x1 += 1;
////                thamdo.add(new Vector2(x1 * layer.getTileWidth(), y1 * layer.getTileHeight()));
////
////                if (getBlockedCells(x1, y1, (int) waytrue.get(waytrue.size() - 1).x / layer.getWidth(),
////                    (int) waytrue.get(waytrue.size() - 1).y / layer.getHeight())) {
////                    waytrue.add(new Vector2(x1 * layer.getTileWidth(), (y1 - 1) * layer.getTileHeight()));
////                }
////            } else {
////                System.out.println("chúa cu6");
////                thamdo.remove(thamdo.size() - 1);
////                if (!thamdo.isEmpty()) {
////                    Vector2 last = thamdo.get(thamdo.size() - 1);
////                    x1 = (int) last.x / layer.getTileWidth();
////                    y1 = (int) last.y / layer.getTileHeight();
////                }
////            }
////        }
////        for(Vector2 a:waytrue){
////            System.out.println(a+" ngu");
////        }
////    }
//
//
//    public void takepoinbuffalo(int x,int y){
//        int checkxuong=0;
//        while(true){
//                if(checkxuong==0 && y > 0 && googlemap[x][y-1] == 0){
//                    y-=1;
//                    thamdodiemmu.add(new Vector2(x * layer.getTileWidth(), y * layer.getTileHeight()));
//                }else if(x < layer.getWidth() && googlemap[x+1][y] == 0){
//                    x+=1;
//                    thamdodiemmu.add(new Vector2(x * layer.getTileWidth(), y * layer.getTileHeight()));
//                    checkxuong=1;
//                }
//                if(checkxuong==1 && y > 0 && googlemap[x][y-1] == 0) {
//                    y-=1;
//                    diemmu=new Vector2(x * layer.getTileWidth(), y * layer.getTileHeight());
//                    break;
//                }
//        }
//    }
//
//    public void DFSbiendi(int x1, int y1) {
//        waytrue.add(new Vector2(x1 * layer.getTileWidth(), y1 * layer.getTileHeight()));
//
//        while (true) {
//            System.out.println("Current Position - x1: " + x1 + ", y1: " + y1);
//
//            // Kiểm tra nếu đã đến đích
//            if (getBlockedCells(x1, y1, (int) diemmu.x / layer.getTileWidth(), (int) diemmu.y / layer.getTileHeight()) == null) {
//                waytrue.add(new Vector2(x1 * layer.getTileWidth(), y1 * layer.getTileHeight()));
//                break;
//            }
//
//            int nextX = x1;
//            int nextY = y1;
//
//            if (y1 > 0 && googlemap[x1][y1 - 1] == 0) {
//                nextY = y1 - 1;
//                thamdo.add(new Vector2(nextX * layer.getTileWidth(), nextY * layer.getTileHeight()));
//                if (getBlockedCells(nextX, nextY, (int) waytrue.get(waytrue.size() - 1).x / layer.getTileWidth(), (int) waytrue.get(waytrue.size() - 1).y / layer.getTileHeight()) != null) {
//                    waytrue.add(new Vector2(x1 * layer.getTileWidth(), y1 * layer.getTileHeight()));
//                }
//            } else if (x1 > 0 && googlemap[x1 - 1][y1] == 0) {
//                System.out.println("Moving left");
//                nextX = x1 - 1;
//                thamdo.add(new Vector2(nextX * layer.getTileWidth(), y1 * layer.getTileHeight()));
//                if (getBlockedCells(nextX, nextY, (int) waytrue.get(waytrue.size() - 1).x / layer.getTileWidth(), (int) waytrue.get(waytrue.size() - 1).y / layer.getTileHeight()) != null) {
//                    waytrue.add(new Vector2((x1 + 1) * layer.getTileWidth(), y1 * layer.getTileHeight()));
//                }
//            } else if (y1 < layer.getHeight() - 1 && googlemap[x1][y1 + 1] == 0) {
//                System.out.println("Moving up");
//                nextY = y1 + 1;
//                thamdo.add(new Vector2(nextX * layer.getTileWidth(), nextY * layer.getTileHeight()));
//                if (getBlockedCells(nextX, nextY, (int) waytrue.get(waytrue.size() - 1).x / layer.getTileWidth(), (int) waytrue.get(waytrue.size() - 1).y / layer.getTileHeight()) != null) {
//                    waytrue.add(new Vector2(x1 * layer.getTileWidth(), (y1 - 1) * layer.getTileHeight()));
//                }
//            } else if (x1 < layer.getWidth() - 1 && googlemap[x1 + 1][y1] == 0) {
//                System.out.println("Moving right");
//                nextX = x1 + 1;
//                thamdo.add(new Vector2(nextX * layer.getTileWidth(), y1 * layer.getTileHeight()));
//                if (getBlockedCells(nextX, nextY, (int) waytrue.get(waytrue.size() - 1).x / layer.getTileWidth(), (int) waytrue.get(waytrue.size() - 1).y / layer.getTileHeight()) != null) {
//                    waytrue.add(new Vector2(x1 * layer.getTileWidth(), (y1 - 1) * layer.getTileHeight()));
//                }
//            } else {
//                System.out.println("Backtracking");
//                if (!thamdo.isEmpty()) {
//                    thamdo.remove(thamdo.size() - 1);
//                    if (!thamdo.isEmpty()) {
//                        Vector2 last = thamdo.get(thamdo.size() - 1);
//                        nextX = (int) last.x / layer.getTileWidth();
//                        nextY = (int) last.y / layer.getTileHeight();
//                    } else {
//                        break;
//                    }
//                }
//            }
//
//            // Cập nhật x1, y1 sau khi kiểm tra tất cả các hướng
//            x1 = nextX;
//            y1 = nextY;
//        }
//    }
//
//
//}
