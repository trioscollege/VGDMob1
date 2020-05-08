package com.example.brickbreaker;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.games.Game;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.leaderboard.ScoreSubmissionData;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class CanvasLevel extends SurfaceView implements SurfaceHolder.Callback {
    //Declare thread
    GameThread gameThread;
    //Declare gameState
    GameStateManager gameState;
    //Declare ui
    UI ui;
    //Declare paddle & ball
    Paddle paddle;
    Object ball;
    //Declare the random object - gives us the ability to have random functionality
    Random rand;
    ArrayList<Object> Objects = new ArrayList<Object>();
    Bitmap spriteSheet;
    GameActivity gameActivity;

    public CanvasLevel(Context context, int screenLength, int screenHeight, int highScore, GameActivity _gameActivity) {
        super(context);
        //Constructor - Defaults will be useful here
        gameActivity = _gameActivity;

        //Instantiate the spritesheet for the game.
        spriteSheet = BitmapFactory.decodeResource(context.getResources(), R.drawable.brickbreaker_spritesheet);
        rand = new Random();
        getHolder().addCallback(this);

        ui = new UI(context, 3, highScore);

        //Places Paddle and Ball at the bottom portion of the screen.
        int ballRadius = 50;
        ball = new Ball(spriteSheet, screenLength /2 - ballRadius/2 , (screenHeight - screenHeight / 6), ballRadius, screenLength, screenHeight);

        int paddleLength = 300;
        int paddleHeight = 75;
        paddle = new Paddle(spriteSheet, (screenLength /2) - paddleLength/2 , screenHeight - screenHeight / 8, paddleLength, paddleHeight);
        
        //Two methods of setting up the bricks!
        int pick = rand.nextInt(2);

        if(pick == 1){
            setBricksInBox(screenLength,250, 5, 5);
        }else{
            setBricksInTriangle(screenLength, 250, 5);
        }



    }

    public void update(float deltaTime) {
        if(ball.position.y > paddle.position.y + paddle.getHeight())
            gameState.changeState(EState.Reset);

        paddle.update(deltaTime);

        if(ball.isColliding(paddle)){
            paddle.onCollisionEnter(ball);
            //Log.i("Collision", "Collision Detected" + paddle);
        }
        ball.update(deltaTime);
        //Log.i("Collision", "COLLISION CHECKING");

        for(Object obj : Objects){
            if(obj.isVisible()) {
                if (ball.isColliding(obj)) {
                    //Log.i("Collision", "Collision Detected" + obj);
                    break;
                }
            }
        }
    }
    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        //Set background to blue
        canvas.drawColor(Color.BLUE);

        //Draw UI
        ui.draw(canvas);

        //Draw Object
        paddle.draw(canvas);
        ball.draw(canvas);

        for(Object obj : Objects){
            if(obj.isVisible()){
                obj.draw(canvas);
            }
        }
    }

    public void reset(){
        //Draw Object
        paddle.restartObject();
        ball.restartObject();

        for(Object obj : Objects){
                obj.restartObject();
        }
        ui.setHighScore(10);

        UI.currentlives--;
        if(UI.currentlives <=0){
            setNewLeaderboard(UI.highScore);
            Context new1 = getContext();
            gameActivity.finish();
            //((Activity)getContext()).finishActivity(1);
        }
    }

    public Boolean isCompleted(){
        return false;
    }

    public void onPause(){
        if(gameThread != null){
            gameThread.onPause();
        }
    }
    public void onResume(){
        if(gameThread != null){
            gameThread.onResume();
        }
    }

    @Override
    public void surfaceCreated(SurfaceHolder holder) {
        //Initialize thread
        gameThread = new GameThread();
        //Initialize gameState
        gameState = new GameStateManager(this);
        //Start thread
        gameThread.activateGameThread(this.getHolder(), gameState);
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {
    }
    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
    }

    @Override
    public boolean onTouchEvent(MotionEvent motionEvent) {

        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN || motionEvent.getAction() == MotionEvent.ACTION_MOVE){
            if (motionEvent.getX() < paddle.getPostion().x + (paddle.getLength()/2)) {
                Log.i("Log", "Move Left");
                paddle.move(-1, (int)motionEvent.getX());
            } else {
                Log.i("Log", "Move Right");
                paddle.move(1, (int)motionEvent.getX());
            }
        }
        else if(motionEvent.getAction() == MotionEvent.ACTION_UP) {
            paddle.move(0, (int)motionEvent.getX());
            Log.i("Log", "Stop Movement");

        }

        return true;
    }

    public void setBricksInTriangle(int screenLength, int startY, int maxRows){
        int maxNum = maxRows * 2;
        int brickSpacing = 25;
        int brickLength = 75;
        int brickHeight = 75;
        int startX = (screenLength/2) - (brickLength+brickSpacing)/2;
        int x = startX, y = startY;
        int curRow = 0;

        for (int i = 1; i < maxNum; i += 2) {
            curRow++;
            for (int j = 0; j < i; j++) {
                addBrick(x, y, brickLength, brickHeight);
                x += brickSpacing + brickLength;
            }
            x = startX - (brickSpacing + brickLength) * curRow;
            y += brickSpacing + brickHeight;
        }
    }

    public void setBricksInBox(int screenLength, int startY, int maxRows, int maxCol){
        int brickSpacing = 25;
        int brickLength = 150;
        int brickHeight = 75;
        int startX = (screenLength - ((brickSpacing+brickLength)*maxCol) - ((brickSpacing + brickLength)/2) );

        int x = startX, y = startY;

        for (int i = 0; i < maxRows; i++) {
            for (int j = 0; j < maxCol; j++) {
                addBrick(x, y, brickLength, brickHeight);
                x += brickSpacing + brickLength;
            }
            y += brickSpacing + brickHeight;
            x = startX;
        }
    }

    public void setNewLeaderboard(int newHighScore){
        Games.getLeaderboardsClient(getContext(), GoogleSignIn.getLastSignedInAccount(getContext()))
                .submitScore(getContext().getString(R.string.leaderboard_highscoreID), newHighScore);
    }

    private void addBrick(int x, int y, int length, int height){
        Brick brick = new Brick(spriteSheet, x, y, length, height);
        brick.setNumHits(rand.nextInt(5));
        Objects.add(brick);
    }
}
