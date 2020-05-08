package com.example.brickbreaker;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class GameThread extends Thread {
    private boolean isGameRunning;
    private SurfaceHolder currentHolder;
    private GameStateManager currentState;

    //FPS
    long startTime;
    long endTime;
    float deltaTimeSeconds;
    public final int TARGET_FPS = 60;
    public static int ACTUAL_FPS = 0;

    public void activateGameThread(SurfaceHolder holder, GameStateManager state) {
        currentState = state;
        isGameRunning = true;
        currentHolder = holder;
        this.start();
    }

    public void run() {
        Canvas gameCanvas = null;
        while (isGameRunning) {
            startTime = System.currentTimeMillis();

            gameCanvas = null;
            gameCanvas = currentHolder.lockCanvas();

            currentState.updateState(deltaTimeSeconds);
            currentState.renderState(gameCanvas);

            currentHolder.unlockCanvasAndPost(gameCanvas);

            endTime = System.currentTimeMillis();
            long deltaTime = endTime - startTime;
            deltaTimeSeconds = (float)deltaTime/1000;


            //hybrid system begins
            if(deltaTime < 1000){
                long interval = (1000 - deltaTime)/TARGET_FPS;
                ACTUAL_FPS = TARGET_FPS;
                try{
                    Thread.sleep(interval);
                }
                catch(Exception ex){

                }
            }
            else{
                ACTUAL_FPS = (int)(1000 / deltaTime);
            }
            //hybrid system ends
        }
    }
    public void onPause(){
        isGameRunning = false;
    }

    public void onResume(){
        isGameRunning = true;
    }


}
