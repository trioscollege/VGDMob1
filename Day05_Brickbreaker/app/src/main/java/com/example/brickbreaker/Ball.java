package com.example.brickbreaker;

import android.graphics.Canvas;
import android.graphics.Point;

public class Ball extends Object {
    private Point velocity;

    public Ball(int _xPos, int _yPos, int _radius) {
        super(_xPos, _yPos, _radius, _radius);

        velocity = new Point(1250, -800);


    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawCircle(position.x, position.y, length, paint);
    }

    @Override
    public void update(float deltaTime) {
        position.x += velocity.x * deltaTime;
        position.y += velocity.y * deltaTime;
        isVisible = false;
    }

    void reverseY() {
        velocity.y *= -1;
    }
    void reverseX() {
        velocity.x *= -1;
    }
}
