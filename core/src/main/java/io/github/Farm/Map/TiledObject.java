package io.github.Farm.Map;

import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.PolylineMapObject;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.util.Vector;

public class TiledObject {
    public static void parseTiledObject(World world, MapObjects objects){
        for(MapObject object:objects){
            Shape shape;
            if(object instanceof PolylineMapObject){
                shape=createPolyLine((PolylineMapObject) object);
            }
            else {
                continue;
            }
            Body body;
            BodyDef bodyDef=new BodyDef();
            bodyDef.type=BodyDef.BodyType.StaticBody;
            body= world.createBody(bodyDef);
            body.createFixture(shape,1.0f);
            shape.dispose();
        }
    }
    private static ChainShape createPolyLine(PolylineMapObject polyLine){
        float[] vertices=polyLine.getPolyline().getTransformedVertices();
        Vector2[] worldVertices=new Vector2[vertices.length/2];
        for (int i=0;i<worldVertices.length;i++){
            worldVertices[i]=new Vector2(vertices[i*2],vertices[i*2+1]);
        }
        ChainShape cs=new ChainShape();
        cs.createChain(worldVertices);
        return cs;
    }
}
