package com.example.brickbreaker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.LeaderboardsClient;
import com.google.android.gms.games.leaderboard.ScoreSubmissionData;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

import static com.google.android.gms.auth.api.signin.GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button Play;
    Button SignIn;
    Button LeaderBoard;
    Button Achievement;
    int RC_SIGN_IN = 1;
    private static final int RC_LEADERBOARD_UI = 9004;
    private static final int RC_ACHIEVEMENT_UI = 9003;
    int HighScore; //Java doesn't have unsigned int :s

    GoogleSignInAccount signedInAccount;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        makeFullScreen();
        setContentView(R.layout.activity_main);

        Play = (Button)findViewById(R.id.buttonPlay);
        Play.setOnClickListener(this);
        Play.setVisibility(View.INVISIBLE);

        LeaderBoard = (Button)findViewById(R.id.buttonLeaderBoard);
        LeaderBoard.setOnClickListener(this);
        LeaderBoard.setVisibility(View.INVISIBLE);

        Achievement = (Button)findViewById(R.id.buttonAchievement);
        Achievement.setOnClickListener(this);

        SignIn = findViewById(R.id.buttonSignIn);
        SignIn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        //If we clicked the Play button - Activate the GameActivity
        if (v.getId() == Play.getId()) {
            Intent i;
            i = new Intent(this, GameActivity.class);
            i.putExtra("HighScoreKey", HighScore);
            startActivity(i);

        } else if (v.getId() == R.id.buttonSignIn) {
            // start the asynchronous sign in flow
            startSignInIntent();
            onSignedIn(true);
        } else if(v.getId() == R.id.buttonLeaderBoard){
            showLeaderboard();
        }else if(v.getId() == R.id.buttonAchievement){
            showAchievements();
        }
    }
    @Override
    protected void onPause(){
        super.onPause();
    }

    @Override
    protected void onResume(){
        super.onResume();
        makeFullScreen();
        signInSilently();
       // Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
               // .submitScore(getString(R.string.leaderboard_id), 1337);
    }

    private void onSignedIn(boolean hasSignedIn){
        if(hasSignedIn) {
            SignIn.setVisibility(View.INVISIBLE);

            Play.setVisibility(View.VISIBLE);
            LeaderBoard.setVisibility(View.VISIBLE);

        }else{
            SignIn.setVisibility(View.VISIBLE);

            Play.setVisibility(View.INVISIBLE);
            LeaderBoard.setVisibility(View.INVISIBLE);

        }
    }

    public void  makeFullScreen(){
        getWindow().getDecorView().setSystemUiVisibility(
                View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                        | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                        | View.SYSTEM_UI_FLAG_FULLSCREEN
                        | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
    }

    private void signInSilently() {

        GoogleSignInOptions signInOptions = GoogleSignInOptions.DEFAULT_GAMES_SIGN_IN;
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
        if (GoogleSignIn.hasPermissions(account, signInOptions.getScopeArray())) {
            // Already signed in.
            // The signed in account is stored in the 'account' variable.
            signedInAccount = account;
            onSignedIn(true);
        } else {
            // Haven't been signed-in before. Try the silent sign-in first.
            final GoogleSignInClient signInClient = GoogleSignIn.getClient(this, signInOptions);
            signInClient
                    .silentSignIn()
                            .addOnCompleteListener(
                            this,
                            new OnCompleteListener<GoogleSignInAccount>() {
                                @Override
                                public void onComplete(@NonNull Task<GoogleSignInAccount> task) {

                                    if (task.isSuccessful()) {
                                        // The signed in account is stored in the task's result.
                                        GoogleSignInAccount signedInAccount = task.getResult();

                                        onSignedIn(true);
                                    } else {
                                        // Player will need to sign-in explicitly using via UI.

                                        onSignedIn(false);
                                        Log.i("Sign in", "FAILED");
                                    }
                                }
                            });
        }
    }
    private void startSignInIntent() {
        GoogleSignInClient signInClient = GoogleSignIn.getClient(this,
                DEFAULT_GAMES_SIGN_IN);
        Intent intent = signInClient.getSignInIntent();
        startActivityForResult(intent, RC_SIGN_IN);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                // The signed in account is stored in the result.
                GoogleSignInAccount signedInAccount = result.getSignInAccount();

                onSignedIn(true);
            } else {
                String message = result.getStatus().getStatusMessage();
                if (message == null || message.isEmpty()) {
                    message = getString(R.string.signin_other_error);
                }
                new AlertDialog.Builder(this).setMessage(message)
                        .setNeutralButton(android.R.string.ok, null).show();

                onSignedIn(false);
            }
        }
    }
    //LeaderBoard
    private void showLeaderboard() {
        Games.getLeaderboardsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .getLeaderboardIntent(getString(R.string.leaderboard_highscoreID))
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, RC_LEADERBOARD_UI);
                    }
                });
    }
    private void showAchievements() {
        Games.getAchievementsClient(this, GoogleSignIn.getLastSignedInAccount(this))
                .getAchievementsIntent()
                .addOnSuccessListener(new OnSuccessListener<Intent>() {
                    @Override
                    public void onSuccess(Intent intent) {
                        startActivityForResult(intent, RC_ACHIEVEMENT_UI);
                    }
                });
    }
}