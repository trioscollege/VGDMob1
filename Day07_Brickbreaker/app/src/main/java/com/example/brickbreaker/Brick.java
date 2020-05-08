package com.example.brickbreaker;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Brick extends Object{
    protected int requiredHits;
    private int points;


    public Brick(Bitmap img, int _xPos, int _yPos, int _length, int _height) {
        super(img, _xPos, _yPos, _length, _height);
        points = 3;
        srcRect = new Rect(0,340,1010,670);

        setNumHits(3);
    }

    public void setNumHits(int hits){
        requiredHits = hits;
        setImage(requiredHits);
    }

    private void setImage(int hitsNeeded){
        paint = new Paint();
        switch(hitsNeeded)
        {
            case 1:
                srcRect = new Rect(0,340,1010,670);
                break;
            case 2:
                srcRect = new Rect(1010,1020,2020,1360);
                break;
            case 3:
                srcRect = new Rect(1010,1700,2020,2040);
                break;
            case 4:
                srcRect = new Rect(2020,680,3030,1020);
                break;
            default:
                //Stones
                srcRect = new Rect(2020,1360,3030,1700);
                break;
        }
    }

    @Override
    protected void onCollisionEnter(Object otherObject) {
        super.onCollisionEnter(otherObject);
        requiredHits--;
        if(requiredHits <= 0){
            setVisible(false);

            Ball ball = (otherObject instanceof Ball ? (Ball)otherObject : null);
            if(ball != null)
            {
                UI.addScore(points * ball.getMultiplier());
                ball.incrementMultiplier();

            }
        }
    }
}
