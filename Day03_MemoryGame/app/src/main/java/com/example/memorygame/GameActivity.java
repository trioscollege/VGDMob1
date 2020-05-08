package com.example.memorygame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.res.AssetFileDescriptor;
import android.content.res.AssetManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.io.IOException;
import java.util.Random;

public class GameActivity extends AppCompatActivity implements View.OnClickListener {

    //Sounds we will require.
    private SoundPool soundPool;
    int sound1 = -1;
    int sound2 = -1;
    int sound3 = -1;
    int sound4 = -1;

    //for our UI
    TextView textScore;
    TextView textLevel;
    TextView textInstructions;
    Button buttonOne;
    Button buttonTwo;
    Button buttonThree;
    Button buttonFour;
    Button buttonRestart;

    int diffLevel = 3;
    int[] sequenceToCopy = new int[100];
    private Handler myHandler;
    boolean playSequence = false;
    int elementToPlay = 0;
    int playerResponses;
    int playerScore;
    boolean isResponding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //Get all sounds ready
        getSoundReady();

        //Set all our UI elements
        attachUIElements();

        //Thread stuff
        myHandler = new Handler() {
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                Log.i("info", msg + " ");

                if (playSequence) {
                    //All the thread action will go here
                    //make sure all the buttons are made visible
                    buttonOne.setVisibility(View.VISIBLE);
                    buttonTwo.setVisibility(View.VISIBLE);
                    buttonThree.setVisibility(View.VISIBLE);
                    buttonFour.setVisibility(View.VISIBLE);
                    switch (sequenceToCopy[elementToPlay]) {
                        case 1:
                            //hide a button
                            buttonOne.setVisibility(View.INVISIBLE);
                            //play a sound
                            soundPool.play(sound1, 1, 1, 0, 0, 1);
                            break;
                        case 2:
                            //hide a button
                            buttonTwo.setVisibility(View.INVISIBLE);
                            //play a sound
                            soundPool.play(sound2, 1, 1, 0, 0, 1);
                            break;
                        case 3:
                            //hide a button
                            buttonThree.setVisibility(View.INVISIBLE);
                            //play a sound
                            soundPool.play(sound3, 1, 1, 0, 0, 1);
                            break;
                        case 4:
                            //hide a button
                            buttonFour.setVisibility(View.INVISIBLE);
                            //play a sound
                            soundPool.play(sound4, 1, 1, 0, 0, 1);
                            break;
                        default:
                            Log.i("info", sequenceToCopy[elementToPlay] + " ");
                            break;
                    }

                    if (elementToPlay == diffLevel) {
                        endSequence();
                    }
                    elementToPlay++;
                }

                myHandler.sendEmptyMessageDelayed(0, 900);
            }
        };//End of thread.
        myHandler.sendEmptyMessageDelayed(0, 1000);
        playSequence();
    }

    @Override
    public void onClick(View v) {
        if(!playSequence) {
            switch (v.getId()) {
                case R.id.button_one:
                    //play a sound 1
                    soundPool.play(sound1, 1, 1, 0, 0, 1);
                    checkElement(1);
                    break;
                case R.id.button_two:
                    //play a sound 2
                    soundPool.play(sound2, 1, 1, 0, 0, 1);
                    checkElement(2);
                    break;
                case R.id.button_three:
                    //play a sound 3
                    soundPool.play(sound3, 1, 1, 0, 0, 1);
                    checkElement(3);
                    break;
                case R.id.button_four:
                    //play a sound 4
                    soundPool.play(sound4, 1, 1, 0, 0, 1);
                    checkElement(4);
                    break;
            }
        }
    }

    private void getSoundReady() {
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC,0);
        try{
            //Create objects of the 2 required classes
            AssetManager assetManager = getAssets();
            AssetFileDescriptor descriptor;

            //create our three fx in memory ready for use
            descriptor = assetManager.openFd("sound1.ogg");
            sound1 = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("sound2.ogg");
            sound2 = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("sound3.ogg");
            sound3 = soundPool.load(descriptor, 0);

            descriptor = assetManager.openFd("sound4.ogg");
            sound4 = soundPool.load(descriptor, 0);

        }catch(IOException e){
            //catch exceptions here
        }
    }

    private void attachUIElements() {
        //First the TextViews
        textScore = (TextView)findViewById(R.id.textScore);
        textScore.setText("SCORE: " + playerScore);
        textLevel = (TextView)findViewById(R.id.textDifficulty);
        textLevel.setText("LEVEL: " + diffLevel);
        textInstructions = (TextView)findViewById(R.id.textInstruction);

        //Now the buttons
        buttonOne = (Button)findViewById(R.id.button_one);
        buttonTwo = (Button)findViewById(R.id.button_two);
        buttonThree = (Button)findViewById(R.id.button_three);
        buttonFour = (Button)findViewById(R.id.button_four);
        buttonRestart = (Button)findViewById(R.id.button_restart);

        //Now set all the buttons to listen for clicks
        buttonOne.setOnClickListener(this);
        buttonTwo.setOnClickListener(this);
        buttonThree.setOnClickListener(this);
        buttonFour.setOnClickListener(this);
        buttonRestart.setOnClickListener(this);
    }

    public void newSequence() {
        //For choosing a random button
        Random randInt = new Random();
        int ourRandom;
        for(int i = 0; i < diffLevel; i++){
            //get a random number between 1 and 4
            ourRandom = randInt.nextInt(4);
            ourRandom ++;//make sure it is not zero
            //Save that number to our array

            sequenceToCopy[i] = ourRandom;
        }
    }

    public void playSequence(){
        newSequence();
        isResponding = false;
        elementToPlay = 0;
        playerResponses = 0;
        textInstructions.setText("WATCH THE PATTERN!");
        playSequence = true;
    }

    public void endSequence(){
        playSequence = false;
        //make sure all the buttons are made visible
        buttonOne.setVisibility(View.VISIBLE);
        buttonTwo.setVisibility(View.VISIBLE);
        buttonThree.setVisibility(View.VISIBLE);
        buttonFour.setVisibility(View.VISIBLE);
        textInstructions.setText("ENTER THE CORRECT SEQUENCE!");
        isResponding = true;
    }

    public void checkElement(int thisElement) {
        if (isResponding) {
            playerResponses++;
            if (sequenceToCopy[playerResponses - 1] == thisElement) { //Correct
                playerScore = playerScore + ((thisElement + 1) * 2);
                textScore.setText("Score: " + playerScore);
                if (playerResponses == diffLevel) {//got the wholesequence
                    //don't checkElement anymore
                    isResponding = false;
                    //now raise the difficulty
                    diffLevel++;
                    //and play another sequence
                    playSequence();
                }
            } else {//wrong answer
                textInstructions.setText("FAILED!");
                //don't checkElement anymore
                isResponding = false;
            }
        }
    }
}
