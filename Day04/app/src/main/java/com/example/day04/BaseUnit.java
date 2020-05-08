package com.example.day04;

import android.util.Log;

public class BaseUnit implements TimeOfDay{
    private int health;
    String name;

    public BaseUnit(int _health){
        health = _health;
    }

    void locateEnemy() {
        Log.i(name, " is locating enemies");
    }

    void printHealth() {
        Log.i(name, " has " + health +" health" );
    }

    void takeDamage(int damage) {
        health -= damage;
    }

    @Override
    public void onHighNoon() {
        Log.i(name, " The Sun is at its highest point of the day" );
    }
}
