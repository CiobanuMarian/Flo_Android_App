package com.msolutions.flo_app;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Settings {

    // Colors declarations
    public static final Integer ORANGE = 0xFFFF5722;
    public static final Integer BLUE = 0xFF03A9F4;
    public static final  Integer PURPLE = 0xFF673AB7;
    public static final  Integer PINK = 0xFFE91E63;
    public static final  Integer GREEN = 0xFF4CAF50;

    private static Settings settings = null;

    private int timeChange = 0;

    public static int DEFAULT_TIME = 3;

    private boolean displayNumbers = false;

    private List<String> colorsPredefinedOrder;

    private List<Integer> availableColors = new ArrayList<>();

    private Settings(){}

    public static Settings getInstance(){
        if(settings == null) {
            settings = new Settings();
            return settings;
        } else {
            return settings;
        }
    }

    public void loadData(Context context){
        try {
            File config = new File(context.getFilesDir(), "Settings.txt");
            if(config.exists()) {
//                config.delete();
                availableColors = new ArrayList<>();
                FileReader fReader = new FileReader(config);
                BufferedReader bReader = new BufferedReader(fReader);
                this.timeChange = Integer.parseInt(bReader.readLine());
//              this.displayNumbers = Boolean.parseBoolean(bReader.readLine()); TODO:implement
                String availableColorsAsString = bReader.readLine();
                String[] colors = availableColorsAsString.split(" ");
                for (String color : colors) {
                    availableColors.add(Integer.parseInt(color));
                }
            }

        } catch (IOException e){
            Log.e("Error:", e.toString());
        }

    }

    public static void setSettings(Settings settings) {
        Settings.settings = settings;
    }

    public int getTimeChange() {
        return timeChange;
    }

    public void setTimeChange(int timeChange) {
        this.timeChange = timeChange;
    }

    public boolean isDisplayNumbers() {
        return displayNumbers;
    }

    public void setDisplayNumbers(boolean displayNumbers) {
        this.displayNumbers = displayNumbers;
    }

    public List<String> getColorsPredefinedOrder() {
        return colorsPredefinedOrder;
    }

    public void setColorsPredefinedOrder(List<String> colorsPredefinedOrder) {
        this.colorsPredefinedOrder = colorsPredefinedOrder;
    }

    public List<Integer> getAvailableColors() {
        return availableColors;
    }

    public void setAvailableColors(List<Integer> availableColors) {
        this.availableColors = availableColors;
    }
}
