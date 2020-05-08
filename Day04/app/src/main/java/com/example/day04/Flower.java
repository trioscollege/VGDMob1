package com.example.day04;

import android.util.Log;

public class Flower implements TimeOfDay{
    @Override
    public void onHighNoon() {
        Log.i("Flower", " Open blossoms" );
    }
}
