package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.Console;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    //Create variables to hold onto our button and textview
    Button submit;
    TextView response;
    EditText NumberGuessed;

    Random rand = new Random();

    int low = 10;
    int high = 100;
    int randomNumber;
    int playerGuess = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Initialize buttons and bind the OnClickListener
        submit = (Button) findViewById(R.id.button_Submit);
        submit.setOnClickListener(this);
        //submit.setVisibility(View.INVISIBLE);

        //Initialize the Response
        response = (TextView) findViewById(R.id.Response);

        NumberGuessed = (EditText) findViewById(R.id.NumberGuessed);
        randomNumber = rand.nextInt(high-low) + low;
    }

    @Override
    public void onClick(View v) {
        String numberGuessedString = NumberGuessed.getText().toString();
        playerGuess++;

        if (!numberGuessedString.equals("")) {
            int playerAnswer = Integer.parseInt(numberGuessedString);

            if(playerAnswer == randomNumber){
                response.setText("Congradulations, you guessed it after" + playerGuess +" time(s)!");
            }
            else if (playerAnswer < randomNumber){
                response.setText(playerAnswer + " is to low!");
            }
            else {
            response.setText(playerAnswer + " is to high!");
            }
        }
        else{
            response.setText("Please Enter A NUMBER between " + low + " and " + high);
        }
    }
}
