package com.mygdx.colouring.Info;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.colouring.Coloring;


/**
 * Created by Yoon on 2017-01-10.
 */

public class MenuCircleInfo {
    private Coloring game;
    private Vector2 start,end,object;
    private int color, blank;
    private float speed,elapsed;
    private float a, b;
    private double distance,directionX,directionY;
    private boolean moving;

    public MenuCircleInfo(){
        game = new Coloring();
        blank = 12;
        a= game.WIDTH/2;
        b= 170;
        color = MathUtils.random(1,4);
        start = new Vector2(a,game.HEIGHT-10);
        end = new Vector2();
        end.x = MathUtils.random(blank,a*2-blank);
        end.y = (float) ((game.HEIGHT-10) - b/a * Math.sqrt(a*a - (end.x-a)*(end.x-a)));
        object = new Vector2();
        object.x = start.x;
        object.y = start.y;

        speed = 150;
        elapsed = 0.01f;
        distance = (Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(end.y - start.y, 2)));
        directionX = ((end.x - start.x) / distance);
        directionY = ((end.y - start.y) / distance);
        moving = true;
    }

    public void moveCircle(){
        if (moving == true){
            object.x += directionX * speed * elapsed;
            object.y += directionY * speed * elapsed;
            if(Math.sqrt(Math.pow(object.x-start.x,2)+Math.pow(object.y-start.y,2)) >= distance)
            {
                object.x = end.x;
                object.y = end.y;
                moving = false;
            }
        }
    }

    public boolean isMoving() {
        return moving;
    }
    public int getColor() {
        return color;
    }
    public Vector2 getObject() {
        return object;
    }
}
