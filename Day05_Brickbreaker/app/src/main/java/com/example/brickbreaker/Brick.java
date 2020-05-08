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
    protected int timesHit;
    Rect Sourceimage;


    public Brick(int _xPos, int _yPos, int _length, int _height) {
        super(_xPos, _yPos, _length, _height);
        timesHit = 0;
        Sourceimage = new Rect(0,0,1010,335);

        setNumHits(3);
    }

    public void setNumHits(int hits){
        requiredHits = hits;
        setPaint(requiredHits);
    }

    private void setPaint(int hitsNeeded){
        paint = new Paint();
        switch(hitsNeeded)
        {
            case 1:
                paint.setColor(Color.GREEN);
                break;
            case 2:
                paint.setColor(Color.YELLOW);
                break;
            case 3:
                //Orange - Google Orange in hex.
                paint.setColor(Color.CYAN);
                break;
            case 4:
                paint.setColor(Color.RED);
                break;
            default:
                //Stones
                paint.setColor(Color.LTGRAY);
                break;
        }
    }

    @Override
    protected void onCollisionEnter(Object otherObject) {
        super.onCollisionEnter(otherObject);

        timesHit--;
        if(timesHit <= 0){
            setVisible(false);
        }
    }
}
