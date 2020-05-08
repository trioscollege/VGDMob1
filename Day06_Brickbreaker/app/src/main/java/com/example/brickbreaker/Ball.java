package com.example.brickbreaker;

import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.Rect;
import android.util.Log;

public class Ball extends Object {
    private Point velocity;
    int screenLength;
    int screenHeight;
    public Ball(Bitmap img, int _xPos, int _yPos, int _radius, int _screenLength, int _screenHeight) {
        super(img, _xPos, _yPos, _radius, _radius);

        screenLength = _screenLength;
        screenHeight = _screenHeight;
        velocity = new Point(/*1250*/ 10, -10);
        //srcRect = new Rect(0, 0, 1010, 340);
        srcRect = new Rect(3684,1710,3850,1880);      //Ball image location
    }

    @Override
    public void update(float deltaTime) {
        super.update(deltaTime);
        position.x += velocity.x ;//* deltaTime;
        position.y += velocity.y ;//* deltaTime;

        isVisible = false;
        stayWithinWindow();
    }

    private void stayWithinWindow(){

        if(position.x < 0){
            reverseX();
        }
        else if(position.x > screenLength - length){
            reverseX();
        }
        if(position.y < 0){
            reverseY();
        }
        if (position.y > screenHeight - height){
            reverseY();
        }
    }
    @Override
    public boolean isColliding(Object otherObject){
        float DeltaX = getDelta(position.x + length/2, otherObject.rect.left, otherObject.rect.right);
        float DeltaY = getDelta(position.y + height/2, otherObject.rect.top, otherObject.rect.bottom);

        //length and height are both set to the radius for the ball
        boolean colliding = (DeltaX * DeltaX + DeltaY * DeltaY) < (length * height);

        if(colliding){
            onCollisionEnter(otherObject);
            otherObject.onCollisionEnter(this);
        }

        return colliding;
    }

    @Override
    public void onCollisionEnter(Object otherObject){
        float DeltaX = getDelta(position.x + length/2, otherObject.rect.left, otherObject.rect.right);
        float DeltaY = getDelta(position.y + height/2, otherObject.rect.top, otherObject.rect.bottom);

        if(Math.abs(DeltaX) > Math.abs(DeltaY)){
            reverseX();
            Log.i("Collision", "DeltaX: " + DeltaX);
            //position.x += velocity.x * 2;

        }else{
            reverseY();
            Log.i("Collision", "DeltaY: " + DeltaY);
            //position.y += velocity.y * 2;
        }
    }

    public float getDelta(int circleAxis, float squareSide1, float squareSide2){
        return circleAxis - Math.max(squareSide1, Math.min(circleAxis, squareSide2));
    }

    void reverseY() {
        velocity.y *= -1;
    }
    void reverseX() {
        velocity.x *= -1;
    }
}
