package com.example.brickbreaker;

import android.graphics.Canvas;

enum EState {
    Play,
    Pause,
    Reset
}

public class GameStateManager {
    CanvasLevel currentLevel;
    EState currentState;
    private float timer;
    private float resetDuration = 60;

    public GameStateManager(CanvasLevel level){
        currentLevel = level;
        currentState = EState.Reset;

        timer = 0;
    }

    public void updateState(float deltaTime) {
        if(currentLevel.isCompleted()){
            changeState(EState.Reset);
        }

        if(currentState == EState.Play)
            currentLevel.update(deltaTime);

        if(currentState == EState.Reset){
            timer++;
            if(timer >= resetDuration)
            {
                changeState(EState.Play);
            }
        }
    }

    public void renderState(Canvas canvas) {
        if(currentLevel != null){
            currentLevel.draw(canvas);
        }
    }

    public void changeState(EState newState) {
        if(currentState != newState){
            currentState = newState;

            if(currentState == EState.Reset){
                timer = 0;
                currentLevel.reset();

            }
        }
    }
}
