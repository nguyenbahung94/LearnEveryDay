package com.ekakashi.greenhouse.features.weather.object_weather;

/**
 * Created by nbhung on 3/5/2018.
 */

public class ObjectWindDirection {
    private String name;
    private String urlIcon;
    private String content;
    private int NumberOfColumns=1;

    public ObjectWindDirection(String name, String urlIcon, String content) {
        this.name = name;
        this.urlIcon = urlIcon;
        this.content = content;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
