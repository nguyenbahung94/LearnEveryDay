package com.ekakashi.greenhouse.features.weather.object_weather;

/**
 * Created by nbhung on 3/5/2018.
 */

public class ObjectPrecipitation {
    private String name;
    private String urlIcon;
    private double speed;
    private int NumberOfColumns=1;

    public ObjectPrecipitation(String name, String urlIcon, double speed) {
        this.name = name;
        this.urlIcon = urlIcon;
        this.speed = speed;
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

    public String getUrlIcon() {
        return urlIcon;
    }

    public void setUrlIcon(String urlIcon) {
        this.urlIcon = urlIcon;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }
}
