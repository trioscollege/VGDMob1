package com.example.brickbreaker;

public class Paddle extends Object{
    private int moveSpeed = 3000;
    private int stoppingDistance = 20;
    private int direction = 0;
    private int lastPos;

    public Paddle(int xPos, int yPos, int length, int height) {
        super(xPos, yPos, length, height);
    }

    public void move(int dir, int lastDetectedPos){
        direction = dir;
        lastPos = lastDetectedPos;
    }

    @Override
    public void update(float deltaTime){
        if(direction != 0 && Math.abs((position.x + length/2)- lastPos) > stoppingDistance )
        {
            position.x += (moveSpeed * direction) * deltaTime;
            rect.top = position.y;
            rect.left = position.x;
            rect.bottom = height + position.y;
            rect.right = length + position.x;
        }
        else{
            direction = 0;
        }
    }


}
