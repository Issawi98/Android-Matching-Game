// Abdelrahman Amr Issawi
// 16P6001
// Photos are got from https://commons.wikimedia.org/
// Version 5.2

package com.example.matchinggame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HighScore extends AppCompatActivity {
    Button back;
    TextView highScoreLbl;
    TextView label;

    public static final String PREFS_NAME = " MyHighScore";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        back = findViewById(R.id.backBtn);
        highScoreLbl = findViewById(R.id.highScorePlace);
        label = findViewById(R.id.placeHolder);


        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Runtime.getRuntime().gc();
                finish();
            }
        });

        SharedPreferences highScore = getSharedPreferences(PREFS_NAME ,0);
        String exsitHS = highScore.getString("HighScore", "NotFound");
        highScoreLbl.setText( exsitHS + "%");
    }
}