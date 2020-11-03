package com.msolutions.flo_app;

import java.util.List;

public class Settings {

    private static Settings settings = null;

    private int timeChange = 0;

    public static int DEFAULT_TIME = 3;

    private boolean displayNumbers = false;

    private List<String> colorsPredefinedOrder;

    private Settings(){}

    public static Settings getInstance(){
        if(settings == null) {
            settings = new Settings();
            return settings;
        } else {
            return settings;
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
}
