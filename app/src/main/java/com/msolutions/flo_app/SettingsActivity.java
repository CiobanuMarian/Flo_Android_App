package com.msolutions.flo_app;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class SettingsActivity extends AppCompatActivity {


    private Button btnBack;
    private Button btnSave;
    private Switch switchGreen;
    private Switch switchOrange;
    private Switch switchBlue;
    private Switch switchPink;
    private Switch switchPurple;
    private Switch switchRed;
    private Switch switchYellow;
    private EditText txtGreen;
    private EditText txtOrange;
    private EditText txtBlue;
    private EditText txtPink;
    private EditText txtPurple;
    private EditText txtRed;
    private EditText txtYellow;
    private Spinner spinnerBlue;
    private Spinner spinnerGreen;
    private Spinner spinnerOrange;
    private Spinner spinnerPink;
    private Spinner spinnerPurple;
    private Spinner spinnerRed;
    private Spinner spinnerYellow;
    Toast toast;

    private ArrayAdapter<CharSequence> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);
        btnBack = findViewById(R.id.btnBack);
        btnSave = findViewById(R.id.btnSave);
        switchBlue = findViewById(R.id.switchBlue);
        switchGreen = findViewById(R.id.switchGreen);
        switchOrange = findViewById(R.id.switchOrange);
        switchPink = findViewById(R.id.switchPink);
        switchPurple = findViewById(R.id.switchPurple);
        switchRed = findViewById(R.id.switchRed);
        switchYellow = findViewById(R.id.switchYellow);


        txtBlue = findViewById(R.id.txtBlue);
        txtGreen = findViewById(R.id.txtGreen);
        txtPink = findViewById(R.id.txtPink);
        txtPurple = findViewById(R.id.txtPurple);
        txtOrange = findViewById(R.id.txtOrange);
        txtRed = findViewById(R.id.txtRed);
        txtYellow = findViewById(R.id.txtYellow);

        spinnerBlue = findViewById(R.id.spinnerBlue);
        spinnerGreen = findViewById(R.id.spinnerGreen);
        spinnerOrange = findViewById(R.id.spinnerOrange);
        spinnerPink = findViewById(R.id.spinnerPink);
        spinnerPurple = findViewById(R.id.spinnerPurple);
        spinnerRed = findViewById(R.id.spinnerRed);
        spinnerYellow = findViewById(R.id.spinnerYellow);

        // initialize spinners

        adapter = ArrayAdapter.createFromResource(this,
                R.array.colorsArray, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerBlue.setAdapter(adapter);
        spinnerGreen.setAdapter(adapter);
        spinnerOrange.setAdapter(adapter);
        spinnerPink.setAdapter(adapter);
        spinnerPurple.setAdapter(adapter);
        spinnerYellow.setAdapter(adapter);
        spinnerRed.setAdapter(adapter);

        Window window = getWindow();
        window.setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.TYPE_STATUS_BAR);


        // set fields on load
        setSwitches();

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

                try {
                    // create the file, add the content
                    File config = new File(getApplicationContext().getFilesDir(), "Settings.txt");
                    BufferedWriter bw = new BufferedWriter(new FileWriter(config));
                    writeColor(switchBlue, txtBlue, spinnerBlue, Settings.BLUE, bw);
                    writeColor(switchGreen, txtGreen, spinnerGreen, Settings.GREEN, bw);
                    writeColor(switchOrange, txtOrange, spinnerOrange, Settings.ORANGE, bw);
                    writeColor(switchPink, txtPink, spinnerPink, Settings.PINK, bw);
                    writeColor(switchPurple, txtPurple, spinnerPurple, Settings.PURPLE, bw);
                    writeColor(switchRed, txtRed, spinnerRed, Settings.RED, bw);
                    writeColor(switchYellow, txtYellow, spinnerYellow, Settings.YELLOW, bw);

                    bw.close();
                    toast = Toast.makeText(getApplicationContext(), "Saved!", Toast.LENGTH_SHORT);
                    toast.show();
                } catch (IOException e) {
                    Log.e("ERROR: ", e.toString());
                    toast = Toast.makeText(getApplicationContext(), "Error while saving!", Toast.LENGTH_SHORT);
                    toast.show();
                }


            }
        });

    }

    public void writeColor(Switch colorSwitch, EditText colorText, Spinner spinner, Integer color, BufferedWriter bw) throws IOException {
        if (colorSwitch.isChecked()) {
            bw.write(color + " ");
            if (colorText.getText().toString().equals("")) {
                bw.write(Settings.DEFAULT_TIME + " ");
            } else {
                bw.write(colorText.getText().toString() + " ");
            }
            bw.write(spinner.getSelectedItem() + " ");
        }
    }

    @Override
    public void onBackPressed() {
    }

    /*
    Set the displayed switches based on the available colors vector from the Settings
     */
    public void setSwitches() {
        Map<Integer, Integer> availableColors = Settings.getInstance().getAvailableColors();
        Map<Integer, String> nextColors = Settings.getInstance().getNextColors();

        if (availableColors.containsKey(Settings.BLUE)) {
            switchBlue.setChecked(true);
            txtBlue.setText(availableColors.get(Settings.BLUE).toString());
            spinnerBlue.setSelection(adapter.getPosition(nextColors.get(Settings.BLUE).toString()));
        } else {
            txtBlue.setText(String.valueOf(Settings.DEFAULT_TIME));
        }
        if (availableColors.containsKey(Settings.GREEN)) {
            switchGreen.setChecked(true);
            txtGreen.setText(availableColors.get(Settings.GREEN).toString());
            spinnerGreen.setSelection(adapter.getPosition(nextColors.get(Settings.GREEN).toString()));
        } else {
            txtGreen.setText(String.valueOf(Settings.DEFAULT_TIME));
        }
        if (availableColors.containsKey(Settings.ORANGE)) {
            switchOrange.setChecked(true);
            txtOrange.setText(availableColors.get(Settings.ORANGE).toString());
            spinnerOrange.setSelection(adapter.getPosition(nextColors.get(Settings.ORANGE).toString()));
        } else {
            txtOrange.setText(String.valueOf(Settings.DEFAULT_TIME));
        }
        if (availableColors.containsKey(Settings.PINK)) {
            switchPink.setChecked(true);
            txtPink.setText(availableColors.get(Settings.PINK).toString());
            spinnerPink.setSelection(adapter.getPosition(nextColors.get(Settings.PINK).toString()));
        } else {
            txtPink.setText(String.valueOf(Settings.DEFAULT_TIME));
        }
        if (availableColors.containsKey(Settings.PURPLE)) {
            switchPurple.setChecked(true);
            txtPurple.setText(availableColors.get(Settings.PURPLE).toString());
            spinnerPurple.setSelection(adapter.getPosition(nextColors.get(Settings.PURPLE).toString()));
        } else {
            txtPurple.setText(String.valueOf(Settings.DEFAULT_TIME));
        }
        if (availableColors.containsKey(Settings.RED)) {
            switchRed.setChecked(true);
            txtRed.setText(availableColors.get(Settings.RED).toString());
            spinnerRed.setSelection(adapter.getPosition(nextColors.get(Settings.RED).toString()));
        } else {
            txtRed.setText(String.valueOf(Settings.DEFAULT_TIME));
        }
        if (availableColors.containsKey(Settings.YELLOW)) {
            switchYellow.setChecked(true);
            txtYellow.setText(availableColors.get(Settings.YELLOW).toString());
            spinnerYellow.setSelection(adapter.getPosition(nextColors.get(Settings.YELLOW).toString()));
        } else {
            txtYellow.setText(String.valueOf(Settings.DEFAULT_TIME));
        }

    }
}
