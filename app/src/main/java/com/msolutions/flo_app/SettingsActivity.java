package com.msolutions.flo_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Struct;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class SettingsActivity extends AppCompatActivity {


    private Button btnBack;
    private Button btnSave;
    private EditText editTextIntervalTime;
    private Switch switchGreen;
    private Switch switchOrange;
    private Switch switchBlue;
    private Switch switchPink;
    private Switch switchPurple;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        editTextIntervalTime = findViewById(R.id.editTextIntervalTime);
        switchBlue = findViewById(R.id.switchBlue);
        switchGreen = findViewById(R.id.switchGreen);
        switchOrange = findViewById(R.id.switchOrange);
        switchPink = findViewById(R.id.switchPink);
        switchPurple = findViewById(R.id.switchPurple);


        // set fields on load
        setSwitches();
        editTextIntervalTime.setText(String.valueOf(Settings.getInstance().getTimeChange()));

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingsActivity.this
                        , MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
            }
        });

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // add the checked colors to a vector and print them after
                List<Integer> availableColors = new ArrayList<>();
                if (switchBlue.isChecked()) {
                    availableColors.add(Settings.BLUE);
                }
                if (switchGreen.isChecked()) {
                    availableColors.add(Settings.GREEN);
                }
                if (switchOrange.isChecked()) {
                    availableColors.add(Settings.ORANGE);
                }
                if (switchPink.isChecked()) {
                    availableColors.add(Settings.PINK);
                }
                if (switchPurple.isChecked()) {
                    availableColors.add(Settings.PURPLE);
                }
                try {
                    // create the file, add the content
                    File config = new File(getApplicationContext().getFilesDir(), "Settings.txt");
                    BufferedWriter bw = new BufferedWriter(new FileWriter(config));
                    if(editTextIntervalTime.getText() != null) {
                        if(editTextIntervalTime.getText().toString().equals("")) {
                            bw.write("3"); // if not filed for the first time when the file is created set it as the default one
                            bw.newLine();
                        } else {
                            bw.write(editTextIntervalTime.getText().toString());
                            bw.newLine();
                        }
                    }
                    for(Integer color : availableColors) {
                        bw.write(color.toString());
                        bw.write(" ");
                    }
                    bw.close();
                    Toast toast = Toast.makeText(getApplicationContext(),"Saved!",Toast.LENGTH_SHORT);
                    toast.show();
                } catch (IOException e) {
                    Log.e("ERROR: ", e.toString());
                    Toast toast = Toast.makeText(getApplicationContext(),"Error while saving!",Toast.LENGTH_SHORT);
                    toast.show();
                }

            }
        });

    }


    @Override
    public void onBackPressed() {
    }

    /*
    Set the displayed switches based on the available colors vector from the Settings
     */
    public void setSwitches() {
        List<Integer> availableColors = Settings.getInstance().getAvailableColors();
        if(availableColors.contains(Settings.BLUE)) {
            switchBlue.setChecked(true);
        }
        if(availableColors.contains(Settings.GREEN)) {
            switchGreen.setChecked(true);
        }
        if(availableColors.contains(Settings.ORANGE)) {
            switchOrange.setChecked(true);
        }
        if(availableColors.contains(Settings.PINK)) {
            switchPink.setChecked(true);
        }
        if(availableColors.contains(Settings.PURPLE)) {
            switchPurple.setChecked(true);
        }

    }
}
