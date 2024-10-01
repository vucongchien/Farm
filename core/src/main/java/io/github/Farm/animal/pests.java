package io.github.Farm.animal;

import com.badlogic.gdx.math.Vector2;

import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Circle;

public class pests {
    private Vector2 location;
    private int hp;
    private Rectangle box;
    private Circle rada;
    private boolean trangthaitancong=false;

    public pests(Vector2 location, int hp) {
        this.location = location;
        this.hp = hp;
        box=new Rectangle(location.x, location.y, 15,15);
        rada=new Circle(location.x, location.y, 50);
    }
    public static boolean checkCircleRectangleCollision(Circle circle, Rectangle rectangle) {
        float nearestX = Math.max(rectangle.x, Math.min(circle.x, rectangle.x + rectangle.width));
        float nearestY = Math.max(rectangle.y, Math.min(circle.y, rectangle.y + rectangle.height));
        float deltaX = circle.x - nearestX;
        float deltaY = circle.y - nearestY;

        return (deltaX * deltaX + deltaY * deltaY) < (circle.radius * circle.radius);
    }
    public static boolean isPointInCircle(Vector2 point, Circle circle) {
        float deltaX = point.x - circle.x;
        float deltaY = point.y - circle.y;

        return (deltaX * deltaX + deltaY * deltaY) <= (circle.radius * circle.radius);
    }
    public Rectangle getbox(){return box;};
    public void setBox(float x,float y){box.setPosition(x,y);};
    public Vector2 getlocation(){return location;}
    public void setTrangthaitancong(boolean a){trangthaitancong=a;};
    public void setLocation(float tmpx,float tmpy){this.location=new Vector2(tmpx,tmpy);}
    public  boolean gettrangthaitancon(){return trangthaitancong;};

}

