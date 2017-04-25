package com.mygdx.colouring.Info;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.mygdx.colouring.Coloring;


/**
 * Created by Yoon on 2017-01-10.
 */

public class CircleInfo {
    private Coloring game;

    private Vector2 start,end,object;
    private int choice, color, blank, MAX;
    private float a;
    private float elapsed;
    private double distance,directionX, directionY;
    private boolean moving;

    public CircleInfo(int i){
        game = new Coloring();
        a = game.WIDTH;
        blank = 12;
        MAX = i;
        choice = MathUtils.random(1,2);
        color = MathUtils.random(1,MAX);
        start = new Vector2(a/2,a/2+240);
        end = new Vector2();
        end.x = MathUtils.random(blank,a-blank);
        if(choice == 1) end.y = (float) ((a/2+240) + Math.sqrt((a/2-blank)*(a/2-blank)-(end.x-a/2)*(end.x-a/2)));
        else if(choice == 2) end.y = (float) ((a/2+240) - Math.sqrt((a/2-blank)*(a/2-blank)-(end.x-a/2)*(end.x-a/2)));
        object = new Vector2();
        object.x = start.x;
        object.y = start.y;

        distance = (Math.sqrt(Math.pow(end.x - start.x, 2) + Math.pow(end.y - start.y, 2)));
        directionX = ((end.x - start.x) / distance);
        directionY = ((end.y - start.y) / distance);
        moving = true;
    }

    public void moveCircle(float speed, float elapsed){
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

    public Vector2 getEnd() {
        return end;
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
