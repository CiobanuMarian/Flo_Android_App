package com.msolutions.flo_app;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {


    private Button btnPlay;
    private Button btnPause;
    private ImageView imgView;
    private Chronometer chronometer;
    private Handler handler;
    private long tMilliSec, tStart, tBuff, tUpdate = 0L;
    private int sec, min, milliSec;
    private boolean started = false;
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        chronometer = findViewById(R.id.chronometer);
        chronometer.setVisibility(View.GONE);
        imgView = findViewById(R.id.imgColor);
//        btnPause.setVisibility(View.GONE);
        handler = new Handler();

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!started) {
                    started = true;
                    tStart = SystemClock.uptimeMillis();
                    handler.postDelayed(runnable, 0);
                    chronometer.start();
                    btnPlay.setVisibility(View.GONE);
                    btnPause.setVisibility(View.VISIBLE);

                } else {
                    started = false;
                    tBuff += tMilliSec;
                    handler.removeCallbacks(runnable);
                    chronometer.stop();
                    btnPause.setVisibility(View.GONE);
                    btnPlay.setVisibility(View.VISIBLE);
                }

            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(started){
                    started = false;
                    tBuff += tMilliSec;
                    handler.removeCallbacks(runnable);
                    chronometer.stop();
                    btnPause.setVisibility(View.GONE);
                    btnPlay.setVisibility(View.VISIBLE);;
                }
            }
        });
    }

    public Runnable runnable = new Runnable() {
        @Override
        public void run() {
            tMilliSec = SystemClock.uptimeMillis() - tStart;
            tUpdate = tBuff + tMilliSec;
            sec = (int) (tUpdate / 1000);
            min = sec / 60;
            sec = sec % 60;
            milliSec = (int) (tUpdate % 100);
            chronometer.setText(String.format("%02d", min) + ":" + String.format("%02d", sec) + ":" + String.format("%02d", milliSec));
            handler.postDelayed(this, 60);
            if(Settings.getInstance().getTimeChange() == 0) {
                if(sec/Settings.DEFAULT_TIME >= count){
                    imgView.setColorFilter(generateColor());
                    count++;
                }
            }

        }
    };


    //Hard-codded available colors, TBD which the user should have
    public int generateColor(){
        final int random = new Random().nextInt(5);
        List<Integer> availableColors = new ArrayList<>();
        availableColors.add(Color.BLUE);
        availableColors.add(Color.MAGENTA);
        availableColors.add(Color.RED);
        availableColors.add(Color.GREEN);
        availableColors.add(Color.YELLOW);
        return availableColors.get(random);

    }
}
