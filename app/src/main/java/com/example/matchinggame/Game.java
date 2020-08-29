// Abdelrahman Amr Issawi
// 16P6001
// Photos are got from https://commons.wikimedia.org/
// Version 5.2

package com.example.matchinggame;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.media.Image;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TableLayout;
import android.widget.TextView;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import com.example.matchinggame.R.drawable.*;
enum state
{
    zero, one, two;
}


public class Game extends AppCompatActivity{
    Button[] btns = new Button[12];
    //int click = 0;
    int score = 0;
    boolean exceed = false;
    int lastClick = -1;
    public int seconds = 0;
    public int minutes = 0;
    Timer t;
    ProgressBar pgsBar;
    Intent scoreIntent;
    boolean started = false;
    TableLayout table;
    MediaPlayer kbSound;
    state s = state.zero;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        final TextView scoreLbl = findViewById(R.id.scoretxt);
        pgsBar = findViewById(R.id.progressBar);
        final List<Integer> images = new ArrayList<>();
        scoreIntent = new Intent(Game.this, HighScoreAfterGame.class);
        table = findViewById(R.id.table);

        images.add(R.drawable.camel);
        images.add(R.drawable.coala);
        images.add(R.drawable.fox);
        images.add(R.drawable.lion);
        images.add(R.drawable.monkey);
        images.add(R.drawable.wolf);
        images.add(R.drawable.camel);
        images.add(R.drawable.coala);
        images.add(R.drawable.fox);
        images.add(R.drawable.lion);
        images.add(R.drawable.monkey);
        images.add(R.drawable.wolf);

        btns[0] = findViewById(R.id.b1);
        btns[1] = findViewById(R.id.b2);
        btns[2] = findViewById(R.id.b3);
        btns[3] = findViewById(R.id.b4);
        btns[4] = findViewById(R.id.b5);
        btns[5] = findViewById(R.id.b6);
        btns[6] = findViewById(R.id.b7);
        btns[7] = findViewById(R.id.b8);
        btns[8] = findViewById(R.id.b9);
        btns[9] = findViewById(R.id.b10);
        btns[10] = findViewById(R.id.b11);
        btns[11] = findViewById(R.id.b12);
        Collections.shuffle(images);

        //Declare the timer
        t = new Timer();
        //Set the schedule function and rate
        t.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        TextView timeLbl = findViewById(R.id.time);
                        timeLbl.setText(String.valueOf(minutes)+":"+String.valueOf(seconds));
                        seconds += 1;
                        if(seconds == 60)
                        {
                            timeLbl.setText(String.valueOf(minutes)+":"+String.valueOf(seconds));
                            seconds=0;
                            minutes=minutes+1;
                        }
                    }
                });
            }
        }, 0, 1000);

        // handle game
        for (int i = 0; i < btns.length ; i++) {
            btns[i].setText("back");
            btns[i].setTextSize(0.0F);
            final int tmp = i; //instead of declaring i as final
            btns[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View v) {

                    // Closing UI interaction
                    getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);

                    //Handle touching un opened cards
                    if (btns[tmp].getText().equals("back") && !exceed && (s == state.zero || s == state.one)) {
                        btns[tmp].setBackgroundResource(images.get(tmp)); //replace photo
                        btns[tmp].setText(images.get(tmp)); //update text

                        //Log.d("TestError", "tmp = " + tmp + " Exceed = " + exceed + " state = " + s);
                        if (s == state.zero) {
                            lastClick = tmp;
                            s = state.one;
                        }
                        else if (s == state.one) {
                            s = state.two;
                            exceed = true;
                        }
                        //play sound
                        if(kbSound!=null){
                            if(kbSound.isPlaying()) {
                                kbSound.stop();
                                kbSound.release();
                            }
                        }
                        playTheSound(v, images.get(tmp).toString());
                        kbSound.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                            @Override
                            public void onCompletion(MediaPlayer mp) {
                                kbSound.reset();
                            }
                        });

                        //Open UI after 700 ms
                        Handler handlerSound = new Handler();
                        handlerSound.postDelayed(new Runnable() {
                            public void run() {
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            }
                        }, 500);

                    } else if (!btns[tmp].getText().equals("back") && !(s == state.zero)) { //Handle opened cards
                        btns[tmp].setBackgroundResource(R.drawable.placeholder);
                        btns[tmp].setText("back");
                        lastClick = -1;
                        if (exceed) {
                            exceed = false;
                        }

                        if (s == state.one) {
                            s = state.zero;
                        }
                        else if (s == state.two) {
                            s = state.one;
                        }
                        Handler handler0 = new Handler();
                        handler0.postDelayed(new Runnable() {
                            public void run() {
                                getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            }
                        }, 500);

                    }

                    //Handle when 2 cards are opened
                    if (s == state.two) {
                        exceed = true;
                        if (btns[tmp].getText().equals(btns[lastClick].getText())) {
                            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            final int tmp1 = tmp;
                            final int tmp2 = lastClick;

                            score++;
                            pgsBar.setProgress(score);
                            scoreLbl.setText(score + " / 6");

                            btns[tmp].setEnabled(false);
                            btns[lastClick].setEnabled(false);

                            s = state.zero;
                            lastClick = -1;

                            Handler handler2 = new Handler();
                            handler2.postDelayed(new Runnable() {
                                public void run() {
                                    //btns[tmp].setVisibility(View.GONE);
                                    //btns[lastClick].setVisibility(View.GONE);
                                    btns[tmp1].setAlpha(0);
                                    btns[tmp2].setAlpha(0);
                                    exceed = false;
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                }
                            }, 800);

                            if (score == 6){
                                //getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                stopTimer();
                                scoreIntent.putExtra("MIN", minutes+"");
                                scoreIntent.putExtra("SEC", seconds+"");
                                Handler handler3 = new Handler();
                                handler3.postDelayed(new Runnable() {
                                    public void run() {
                                        //Log.d("TIME", "MIN = " + minutes + " SEC = " + seconds);
                                        minutes = 0;
                                        seconds=0;
                                        Runtime.getRuntime().gc();
                                        finish();
                                        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                        startActivity(scoreIntent);
                                    }
                                }, 500);
                            }
                        }
                        else if (!btns[tmp].getText().equals(btns[lastClick].getText())) {
                            //getWindow().setFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                            final int tmp1 = tmp;
                            final int tmp2 = lastClick;

                            s = state.zero;
                            lastClick = -1;
                            Handler handler4 = new Handler();
                            handler4.postDelayed(new Runnable() {
                                public void run() {
                                    btns[tmp1].setBackgroundResource(R.drawable.placeholder);
                                    btns[tmp1].setText("back");
                                    btns[tmp2].setBackgroundResource(R.drawable.placeholder);
                                    btns[tmp2].setText("back");
                                    exceed = false;
                                    getWindow().clearFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE);
                                }
                            }, 800);
                        }

                    }

                    Log.d("VARi", "State = " + s + " Exceed = " + exceed + " Last Click = " + lastClick);
                }
            });

        }

    }
    public void stopTimer(){
        t.cancel();
    }

    // to create the click sound
    public void playTheSound(View v, String s){
        switch (s){
            case "2131099750":
                kbSound = MediaPlayer.create(this, R.raw.lion);
                kbSound.start();
                break;

            case "2131099745":
                kbSound = MediaPlayer.create(this, R.raw.coala);
                kbSound.start();
                break;

            case "2131099744":
                kbSound = MediaPlayer.create(this, R.raw.camel);
                kbSound.start();
                break;

            case "2131099747":
                kbSound = MediaPlayer.create(this, R.raw.fox);
                kbSound.start();
                break;

            case "2131099751":
                kbSound = MediaPlayer.create(this, R.raw.monkey);
                kbSound.start();
                break;

            case "2131099768":
                kbSound = MediaPlayer.create(this, R.raw.wolf);
                kbSound.start();
                break;

            default:
                kbSound = MediaPlayer.create(this, R.raw.keypressstandard);
                kbSound.start();
                break;
        }

    }
}

//Lion:  2131099750
//Coala: 2131099745
//camel: 2131099744
//fox:   2131099747
//mokey: 2131099751
//wolf:  2131099768
