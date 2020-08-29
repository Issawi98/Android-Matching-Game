// Abdelrahman Amr Issawi
// 16P6001
// Photos are got from https://commons.wikimedia.org/
// Version 5.2

package com.example.matchinggame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    Button newGame;
    Button highScore;
    Button exit;
    Intent highScoreAct;
    Intent game;
    public static final String PREFS_NAME = " MyHighScore";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        highScoreAct = new Intent(MainActivity.this, HighScore.class);
        game = new Intent(MainActivity.this, Game.class);

        newGame = findViewById(R.id.newGameBtn);
        highScore = findViewById(R.id.highScoreBtn);
        exit = findViewById(R.id.exitBtn);

        SharedPreferences hs = getSharedPreferences (PREFS_NAME,0);
        SharedPreferences.Editor editor = hs.edit();
        String tmp = hs.getString ("HighScore", "NotFound");
        if (tmp.equals("NotFound"))
            editor.putString("HighScore", 0+"");
        editor.commit();
        //editor.putString("HighScore", 0+"");
        //editor.commit();


        newGame.setOnClickListener(this);
        highScore.setOnClickListener(this);
        exit.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.newGameBtn:
                startActivity(game);
                break;

            case R.id.highScoreBtn:
                startActivity(highScoreAct);
                break;

            case R.id.exitBtn:
                System.exit(0);
                break;
        }
    }
}