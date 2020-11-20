package com.msolutions.flo_app;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Set;

public class MainActivity extends AppCompatActivity {


    private ImageView btnPlay;
    private ImageView btnPause;
    private ImageView btnReset;
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

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);

        Settings.getInstance().loadData(getApplicationContext()); // load from file the Settings

        btnPlay = findViewById(R.id.btnPlay);
        btnPause = findViewById(R.id.btnPause);
        chronometer = findViewById(R.id.chronometer);
//        chronometer.setVisibility(View.GONE); //disable the timer
        imgView = findViewById(R.id.imgColor);
        imgView.setColorFilter(Color.WHITE);
        btnPause.setVisibility(View.GONE);
        btnReset = findViewById(R.id.btnReset);
        btnReset.setVisibility(View.GONE);
        handler = new Handler();


        imgSettings = findViewById(R.id.imgSettings);
        imgSettings.setColorFilter(Color.WHITE);
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
        txtSettings.setTextColor(Color.WHITE);
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
        imgLeave.setColorFilter(Color.WHITE);
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
                    btnReset.setVisibility(View.GONE);

                } else {
                    started = false;
                    tBuff += tMilliSec;
                    handler.removeCallbacks(runnable);
                    chronometer.stop();
                    btnPause.setVisibility(View.GONE);
                    btnReset.setVisibility(View.VISIBLE);
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
                    btnReset.setVisibility(View.VISIBLE);
                    btnPlay.setVisibility(View.VISIBLE);
                    ;
                }
            }
        });

        txtLeave = findViewById(R.id.txtLeave);
        txtLeave.setTextColor(Color.WHITE);
        txtLeave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                System.exit(0);
            }
        });

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!started) {
                    started = false;
                    handler.removeCallbacks(runnable);
                    chronometer.stop();
                    btnPause.setVisibility(View.GONE);
                    btnReset.setVisibility(View.VISIBLE);
                    btnPlay.setVisibility(View.VISIBLE);
                    min = 0;
                    sec = 0;
                    milliSec = 0;
                    tMilliSec = 0;
                    tUpdate = 0;
                    tStart = 0;
                    tBuff = 0;
                    chronometer.setText(String.format("%02d", min) + ":" + String.format("%02d", sec) + ":" + String.format("%02d", milliSec));
                    count = 1;
                    imgView.setColorFilter(Color.WHITE);
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
            if (sec / Settings.getInstance().getTimeChange() >= count) {
                imgView.setColorFilter(generateColor());
                count++;
            }

        }
    };


    public int generateColor() {
        final int random = new Random().nextInt(50);
        return Settings.getInstance().getAvailableColors().get(random % Settings.getInstance().getAvailableColors().size());

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
