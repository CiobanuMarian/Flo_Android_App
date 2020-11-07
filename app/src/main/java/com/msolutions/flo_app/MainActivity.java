package com.msolutions.flo_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class MainActivity extends AppCompatActivity {


    private Button btnPlay;
    private Button btnPause;
    private TextView txtLeave;
    private ImageView imgLeave;
    private ImageView imgView;
    private ImageView imgSettings;
    private Chronometer chronometer;
    private TextView txtSettings;
    private Handler handler;
    private long tMilliSec, tStart, tBuff, tUpdate = 0L;
    private int sec, min, milliSec;
    private boolean started = false;
    private int count = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        chronometer = findViewById(R.id.chronometer);
//        chronometer.setVisibility(View.GONE); //disable the timer
        imgView = findViewById(R.id.imgColor);
        btnPause.setVisibility(View.GONE);
        handler = new Handler();

        imgSettings = findViewById(R.id.imgSettings);
        imgSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this
                        , SettingsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        txtSettings = findViewById(R.id.txtSettings);
        txtSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this
                        , SettingsActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });
        imgLeave = findViewById(R.id.imgLeave);
        imgLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });


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
                if (started) {
                    started = false;
                    tBuff += tMilliSec;
                    handler.removeCallbacks(runnable);
                    chronometer.stop();
                    btnPause.setVisibility(View.GONE);
                    btnPlay.setVisibility(View.VISIBLE);
                    ;
                }
            }
        });

        txtLeave = findViewById(R.id.txtLeave);
        txtLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
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
            if (Settings.getInstance().getTimeChange() == 0) {
                if (sec / Settings.DEFAULT_TIME >= count) {
                    imgView.setColorFilter(generateColor());
                    count++;
                }
            }

        }
    };


    //Hard-codded available colors, TBD which the user should have
    public int generateColor() {
        final int random = new Random().nextInt(50);
        List<Integer> availableColors = new ArrayList<>();
        availableColors.add(0xFFFF5722); // orange
        availableColors.add(0xFF03A9F4); // blue
        availableColors.add(0xFF673AB7); //purple
        availableColors.add(0xFFE91E63); //pink
        availableColors.add(0xFF4CAF50); //green
        return availableColors.get(random % 5);

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
