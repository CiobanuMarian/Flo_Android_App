package com.msolutions.flo_app;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Settings {

    // Colors declarations
    public static final Integer ORANGE = 0xFFFF5722;
    public static final Integer BLUE = 0xFF03A9F4;
    public static final Integer PURPLE = 0xFF673AB7;
    public static final Integer PINK = 0xFFE91E63;
    public static final Integer GREEN = 0xFF4CAF50;
    public static final Integer YELLOW = 0xfffdd31d;
    public static final Integer RED = 0xffce0018;

    private static Settings settings = null;

    private int timeChange = 0;

    public static int DEFAULT_TIME = 3;

    private boolean displayNumbers = false;

    private List<String> colorsPredefinedOrder;

    private Map<Integer, Integer> availableColors = new HashMap<>();

    private Map<Integer, String> nextColors = new HashMap<>();


    private Settings() {
    }

    public static Settings getInstance() {
        if (settings == null) {
            settings = new Settings();
            return settings;
        } else {
            return settings;
        }
    }

    public void loadData(Context context) {
        try {
            File config = new File(context.getFilesDir(), "Settings.txt");
            if (config.exists()) {
//                config.delete();
                availableColors = new HashMap<>();
                FileReader fReader = new FileReader(config);
                BufferedReader bReader = new BufferedReader(fReader);
                String availableColorsAsString = bReader.readLine();
                if (availableColorsAsString == null) {
                    Log.w("Warn", "File is empty");
                    return;
                }
                String[] colors = availableColorsAsString.split(" ");
                for (int i = 0; i < colors.length; i += 3) {
                    availableColors.put(Integer.parseInt(colors[i]), Integer.parseInt(colors[i + 1]));
                    nextColors.put(Integer.parseInt(colors[i]), colors[i + 2]);
                }
            }

        } catch (IOException e) {
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

    public Map<Integer, Integer> getAvailableColors() {
        return availableColors;
    }

    public void setAvailableColors(Map<Integer, Integer> availableColors) {
        this.availableColors = availableColors;
    }

    public Map<Integer, String> getNextColors() {
        return nextColors;
    }

    public void setNextColors(Map<Integer, String> nextColors) {
        this.nextColors = nextColors;
    }
}
