package com.example.brickbreaker;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;

public class GameActivity extends AppCompatActivity {
    private CanvasLevel currentCanvasLevel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        makeFullScreen();

        //GetDisplaySize
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        //Select Level to play - Could have a level select screen class to handle this instead.
        currentCanvasLevel = new CanvasLevel(this, size.x, size.y);
        setContentView(currentCanvasLevel);
    }

    @Override
    protected void onPause(){
        super.onPause();
        if(currentCanvasLevel != null){
            currentCanvasLevel.onPause();
        }
    }

    @Override
    protected void onResume(){
        super.onResume();
        if(currentCanvasLevel != null){
            currentCanvasLevel.onResume();
        }
        makeFullScreen();
    }

    private void makeFullScreen(){
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }
}
