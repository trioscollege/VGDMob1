package com.example.brickbreaker;

import android.graphics.Bitmap;
import android.graphics.Rect;

public class Paddle extends Object{
    private int moveSpeed = 3000;
    private int stoppingDistance = 20;
    private int direction = 0;
    private int lastPos;

    public Paddle(Bitmap img, int xPos, int yPos, int length, int height) {
        super(img, xPos, yPos, length, height);

        srcRect = new Rect(3037,1390,3680,1565);
    }

    public void move(int dir, int lastDetectedPos){
        direction = dir;
        lastPos = lastDetectedPos;
    }

    @Override
    public void update(float deltaTime){
        super.update(deltaTime);
        if(direction != 0 && Math.abs((position.x + length/2)- lastPos) > stoppingDistance )
        {
            position.x += (moveSpeed * direction) * deltaTime;
        }
        else{
            direction = 0;
        }
    }


}
