package io.github.Farm;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.maps.Map;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell;

import java.util.LinkedList;
import java.util.Queue;

public class PlayerController {
    private Vector2 position;
    private float speed;
    private boolean facingRight = true;
    private PlayerActType direction = PlayerActType.d_idle;
    private PlayerActType lastDirection = PlayerActType.d_idle;
    private Queue<PlayerActType> inputQueue = new LinkedList<>();
    private TiledMapTileLayer tileLayer;
    private Gamemap map;

    public void setMap(Gamemap map) {
        this.map=map;
    }
    public boolean check(int x, int y){ //tọa độ của thèn nhan vật theo thế giới
        TiledMap tiledMap = map.getTiledMap();
        if (tileLayer == null || !tileLayer.getName().equals("goc")) {
            tileLayer = (TiledMapTileLayer) tiledMap.getLayers().get("goc"); // Khởi tạo lại với lớp "goc"
        }
        float tileWidth = tileLayer.getTileWidth();
        float tileHeight = tileLayer.getTileHeight();
        int tileX = (int) (x / tileWidth)+2;
        int tileY = (int) (y / tileHeight)+1;
        Cell cell= tileLayer.getCell(tileX, tileY);//tọa độ của nhân vật theo map

        if(cell==null)return true;
        else{return false;}
    }
    public PlayerController(Vector2 startPosition, float speed) {
        this.position = startPosition;
        this.speed = speed;
    }
    public Vector2 getPosition() {
        return position;
    }
    public void update(float deltaTime) {
        handleInput();
        moveCharacter(deltaTime);
    }
    private void handleInput() {
        inputQueue.clear();
        if (Gdx.input.isKeyPressed(Input.Keys.W)) inputQueue.add(PlayerActType.up);
        if (Gdx.input.isKeyPressed(Input.Keys.S)) inputQueue.add(PlayerActType.down);
        if (Gdx.input.isKeyPressed(Input.Keys.A)) inputQueue.add(PlayerActType.left);
        if (Gdx.input.isKeyPressed(Input.Keys.D)) inputQueue.add(PlayerActType.right);
        if (!inputQueue.isEmpty()) {
            direction = inputQueue.peek();
            lastDirection = direction;
            facingRight = direction == PlayerActType.right;
        } else {
            direction = getIdleDirection();
        }
    }
    private PlayerActType getIdleDirection() {
        switch (lastDirection) {
            case up: return PlayerActType.u_idle;
            case down: return PlayerActType.d_idle;
            case left: return PlayerActType.l_idle;
            case right: return PlayerActType.r_idle;
            default: return PlayerActType.d_idle;
        }
    }
    private void moveCharacter(float deltaTime) {
        Vector2 movement = new Vector2();

        switch (direction) {
            case up:
                if(check((int) position.x, (int) position.y + 1)){
                    movement.y = 1; break;
                }else{
                    return;
                }

            case down:
                if(check((int) position.x, (int) position.y - 1)){
                movement.y = -1; break;
            }else{
                return;
            }
            case left:
                if(check((int) position.x-1, (int) position.y)){
                movement.x = -1; break;
            }else{
                return;
            }
            case right:
                if(check((int) position.x+1, (int) position.y)){
                movement.x = 1; break;
            }else{
                return;
            }
            default: return; // Không di chuyển nếu ở trạng thái idle
        }
        position.add(movement.scl(speed * deltaTime));
    }
    public boolean isWalking() {
        return direction != PlayerActType.d_idle && direction != PlayerActType.u_idle
            && direction != PlayerActType.l_idle && direction != PlayerActType.r_idle;
    }
    public String getDirection() {
        return direction.toString();
    }
    public boolean isFacingRight() {
        return facingRight;
    }
    public float getDeltaTime() {
        return Gdx.graphics.getDeltaTime();
    }
}

