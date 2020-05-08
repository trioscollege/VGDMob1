package com.example.brickbreaker;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;

public class Object {
    protected Point position;
    protected Point startPosition;

    protected RectF rect;

    protected int length;
    protected int height;
    //x and y are the positions closest to the top left corner of the object.
    protected float x;
    protected float y;

    protected Rect srcRect;
    protected Bitmap spreadSheet;

    boolean isVisible;
    Paint paint;

    public Object(Bitmap img, int _xPos, int _yPos, int _length, int _height){
        isVisible = true;
        paint = new Paint();
        paint.setColor(Color.WHITE);
        spreadSheet = img;
        length = _length;
        height = _height;
        position = new Point(_xPos,_yPos);
        startPosition = new Point(_xPos, _yPos);
        setRect();
        srcRect = new Rect(0,340,1010,670);
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(spreadSheet, srcRect, rect, paint);
    }

    public void update(float deltaTime){
        setRect();
    }

    public boolean isColliding(Object otherObject){
        return false;
    }

    protected void onCollisionEnter(Object otherObject){

    }
    protected void onCollisionStay(Object otherObjcet){

    }

    public void restartObject(){
        Point temppos = new Point(startPosition.x, startPosition.y);
        position = temppos;
        setVisible(true);
        setRect();

    }
    public void setRect(){
        rect = new RectF(position.x, position.y,length + position.x,height + position.y);
    }

    public Point getPostion(){
        return position;
    }
    //Two different methods so we can easily set position depending on the type of data we have available.
    public void setPosition(Point _position){
        position = _position;
    }
    public void setPosition(int x, int y){
        position = new Point(x, y);
    }

    public boolean isVisible(){
        return isVisible;
    }
    public void setVisible(boolean visible){
        isVisible = visible;
    }

    public int getLength(){
        return length;
    }
    public int getHeight(){
        return height;
    }

}
