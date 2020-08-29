// Abdelrahman Amr Issawi
// 16P6001
// Photos are got from https://commons.wikimedia.org/
// Version 5.2

package com.example.matchinggame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HighScoreAfterGame extends AppCompatActivity {
    TextView currentScore;
    TextView HighScore;
    int hs;
    Button back;
    Button restart;
    Intent home;
    public static final String PREFS_NAME = " MyHighScore";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score_after_game);

        currentScore = findViewById(R.id.PlayerScorePlaceHolder);
        HighScore = findViewById(R.id.ifHighScore);
        SharedPreferences highScore = getSharedPreferences(PREFS_NAME ,0);

        back = findViewById(R.id.backBtn);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runtime.getRuntime().gc();
                finish();
            }
        });

        home = new Intent(HighScoreAfterGame.this, Game.class);
        restart = findViewById(R.id.restartBtn);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(home);
                Runtime.getRuntime().gc();
                finish();
            }
        });

        Intent time = getIntent();
        int totalSec = Integer.parseInt(time.getStringExtra("MIN")) *60 + Integer.parseInt(time.getStringExtra("SEC"));
        int temp = (18 * 100)/ totalSec;
        String exsitHS = highScore.getString ("HighScore", "NotFound");
        if (!exsitHS.equals("NotFound")) {
            hs = Integer.parseInt(exsitHS);
        }
        Log.d("TIMEAFTER", "current = " + temp + " high = " + hs+ " totalSec " + totalSec);
        //Log.d("TIME", "MIN = " + time.getStringExtra("MIN") + " SEC = " + time.getStringExtra("SEC"));
        if (temp > hs){
            HighScore.setText("HIGHSCORE!!\n The Older Score was " +hs +"%");
            SharedPreferences settings = getSharedPreferences (PREFS_NAME,0);
            SharedPreferences.Editor editor = settings.edit();
            editor.putString("HighScore", temp+"");
            editor.commit();
        }

        //String score = time.getStringExtra("MIN") + ":" +  time.getStringExtra("SEC");
        String score = temp + "%";
        currentScore.setText("You Got " + score);


    }
}