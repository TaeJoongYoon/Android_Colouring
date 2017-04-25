package com.mygdx.colouring.Info;

import com.badlogic.gdx.math.Vector2;
import com.mygdx.colouring.Coloring;


/**
 * Created by Yoon on 2017-01-10.
 */

public class BonusInfo {
    private Coloring game;

    private Vector2 start,end,object;
    private float speed,elapsed;
    private double distance,directionX,directionY;
    private boolean moving,bonus;

    public BonusInfo(HeartInfo heartInfo){
        game = new Coloring();
        start = new Vector2(heartInfo.getObject().x,heartInfo.getObject().y);
        if(heartInfo.getEnd().x >= game.WIDTH/2) start = new Vector2(heartInfo.getEnd().x-120,heartInfo.getEnd().y);
        end = new Vector2(start.x, start.y+50);
        object = new Vector2();
        object.x = start.x;
        object.y = start.y;

        speed = 100;
        elapsed = 0.015f;
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
    public Vector2 getObject() {
        return object;
    }
    public void setBonus(boolean bonus) {this.bonus = bonus;}
    public boolean isBonus() {return bonus;}
}
