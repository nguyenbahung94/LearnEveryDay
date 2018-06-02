package com.ekakashi.greenhouse.features.weather.object_weather;

/**
 * Created by nbhung on 3/5/2018.
 */

public class ObjectWindVelocity {
    private String name;
    private double speed;
    private String urlIcon;
    private int NumberOfColumns = 1;

    public ObjectWindVelocity(String name, double speed, String urlIcon) {
        this.name = name;
        this.speed = speed;
        this.urlIcon = urlIcon;
    }

    public int getNumberOfColumns() {
        return NumberOfColumns;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public String getUrlIcon() {
        return urlIcon;
    }

    public void setUrlIcon(String urlIcon) {
        this.urlIcon = urlIcon;
    }
}
