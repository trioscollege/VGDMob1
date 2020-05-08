package com.example.day04;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BaseUnit[] myArmy = new BaseUnit[4];

        // Create Object for grunt unit from BaseUnit
        myArmy[0] = new BaseUnit(225);
        myArmy[0] .name = "Grunt";

        // Create Object for HeadHunter unit from BaseUnit
        myArmy[1]  = new BaseUnit(175);
        myArmy[1].name = "HeadHunter";

        // Create Object for Shaman unit from BaseUnit
        myArmy[2] = new BaseUnit(125);
        myArmy[2].name = "Shaman";

        myArmy[3] = new Grunt(225);
        myArmy[3].name = "Actual Grunt";

        //Cycle through each element in the array.
        for(BaseUnit unit : myArmy)
        {
            unit.locateEnemy();
            unit.printHealth();
        }



        //HeadHunter takes damage
        myArmy[1].takeDamage(25);
        myArmy[1].printHealth();

        Flower newFlower = new Flower();


        newFlower.onHighNoon();
        myArmy[1].onHighNoon();
        myArmy[3].onHighNoon();
        /*
        grunt.locateEnemy();
        grunt.printHealth();

        headHunter.locateEnemy();
        headHunter.printHealth();
        headHunter.takeDamage(25);
        headHunter.printHealth();

        shaman.locateEnemy();
        shaman.printHealth();
        */


    }
}
