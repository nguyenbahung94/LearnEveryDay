package com.ekakashi.greenhouse.features.weather.object_weather;

/**
 * Created by nbhung on 3/5/2018.
 */

public class ObjectHumidity {
    private String name;
    private int persent;
    private String urlIcon;
    private int NumberOfColumns = 1;

    public ObjectHumidity(String name, int persent, String urlIcon) {
        this.name = name;
        this.persent = persent;
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

    public int getPersent() {
        return persent;
    }

    public void setPersent(int persent) {
        this.persent = persent;
    }

    public String getUrlIcon() {
        return urlIcon;
    }

    public void setUrlIcon(String urlIcon) {
        this.urlIcon = urlIcon;
    }
}
