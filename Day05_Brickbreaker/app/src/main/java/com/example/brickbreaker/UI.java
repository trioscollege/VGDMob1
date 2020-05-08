package com.example.brickbreaker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;

public class UI {
    int currentScore;
    Point scorePostion;
    int highScore;
    Point highScorePosition;
    int currentlives;
    Point livesPosition;

    private Paint paintText;
    public UI(Context contex){

        setCurrentScore(0000);
        setHighScore(0000);
        setLives(0000);

        paintText = new Paint();
        paintText.setColor(Color.WHITE);
        paintText.setTextSize(60);
    }

    public void draw(Canvas canvas){

        canvas.drawText("Score: " + currentScore,       canvas.getWidth()/8 - 100,  80, paintText);
        canvas.drawText("High Score: " + highScore,     canvas.getWidth() - 400,  80, paintText);
        canvas.drawText("Lives: " + currentlives,              canvas.getWidth()/2 - 100,  canvas.getHeight() - 10, paintText);
    }

    public void setHighScore(int score){
        highScore = score;
    }
    public void setCurrentScore(int score){
        currentScore = score;
    }
    public void setLives(int lives){
        currentlives = lives;
    }
}
