package com.example.brickbreaker;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.Log;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;

public class UI {
    static int currentScore;
    static int highScore;
    static int currentlives;
    int maxLives;

    private Paint paintText;
    public UI(Context _contex, int _maxLives, int _highScore){
        setCurrentScore(0000);
        setHighScore(highScore);
        maxLives = _maxLives;
        setLives(maxLives);

        paintText = new Paint();
        paintText.setColor(Color.WHITE);
        paintText.setTextSize(60);
    }

    public void draw(Canvas canvas){

        canvas.drawText("Score: " + currentScore,       canvas.getWidth()/8 - 100,  80, paintText);
        canvas.drawText("High Score: " + highScore,     canvas.getWidth() - 400,  80, paintText);
        canvas.drawText("Lives: " + currentlives,       canvas.getWidth()/2 - 100,  canvas.getHeight() - 10, paintText);
    }

    public void setHighScore(int score){
        highScore = score;
    }
    public void setCurrentScore(int score){
        currentScore = score;

        if(currentScore < highScore){
            highScore = currentScore;
        }
    }
    static public void addScore(int score) {
        currentScore += score;
    }
    public void setLives(int lives){
        currentlives = lives;
    }
}
