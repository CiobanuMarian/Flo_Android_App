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

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

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
    private int changeColorTimer = 0;
    private int currentDisplayedColor = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
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
                    changeColorTimer = 0;
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
            int allTime = sec;
            sec = sec % 60;
            milliSec = (int) (tUpdate % 100);
            chronometer.setText(String.format("%02d", min) + ":" + String.format("%02d", sec) + ":" + String.format("%02d", milliSec));
            handler.postDelayed(this, 60);
            // Get the color to be displayed, add the time that color has, when the normal timer reaches that, change the color again
            if(changeColorTimer == allTime) {
                currentDisplayedColor = generateColor(currentDisplayedColor);
                changeColorTimer += Settings.getInstance().getAvailableColors().get(currentDisplayedColor);
                imgView.setColorFilter(currentDisplayedColor);
            }

        }
    };

    /*
        Generates a color based on the previous one,
        if 0 or if the color has no nextColor value, generate a random one (except the color itself),
        else return the nextColor specified in the settings
     */
    public int generateColor(int currentDisplayedColor) {
        final int random = new Random().nextInt(50);

        Object[] values = Settings.getInstance().getAvailableColors().keySet().toArray();
        if (currentDisplayedColor == 0) {
            return (int) values[random % Settings.getInstance().getAvailableColors().size()];
        } else {
            String nextColor = Settings.getInstance().getNextColors().get(currentDisplayedColor);
            if (nextColor != null) {
                switch (nextColor) {
                    case "Blue":
                        return Settings.BLUE;
                    case "Green":
                        return Settings.GREEN;
                    case "Orange":
                        return Settings.ORANGE;
                    case "Pink":
                        return Settings.PINK;
                    case "Purple":
                        return Settings.PURPLE;
                }
            }

            Map tempMap = new HashMap();
            for (Map.Entry<Integer, Integer> entry : Settings.getInstance().getAvailableColors().entrySet()) {
               if(entry.getKey() != currentDisplayedColor){
                   tempMap.put(entry.getKey(), entry.getValue());
               }
            }
            values = tempMap.keySet().toArray();
            System.out.println(Settings.getInstance().getAvailableColors());
            return (int) values[random % tempMap.size()];
        }
    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
