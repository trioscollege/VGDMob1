package com.example.day04;

import android.util.Log;

public class Grunt extends BaseUnit {

    Grunt(int _health){
        super(_health);

    }

    @Override
    public void locateEnemy(){
        super.locateEnemy();
        Log.i(name, " RAWR");
    }

    @Override
    public void onHighNoon() {
        Log.i(name, " Insert Grunt message here" );
    }
}
