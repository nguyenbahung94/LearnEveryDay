package com.ekakashi.greenhouse.features.weather.object_weather;

/**
 * Created by nbhung on 3/5/2018.
 */

public class ObjectSunriseSunset {
    private String name;
    private String urlIcon1;
    private String UrlIcon2;
    private String content1;
    private String content2;
    private int NumberOfColumns = 1;

    public ObjectSunriseSunset(String name, String urlIcon1, String urlIcon2, String content1, String content2) {
        this.name = name;
        this.urlIcon1 = urlIcon1;
        UrlIcon2 = urlIcon2;
        this.content1 = content1;
        this.content2 = content2;
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

    public String getUrlIcon1() {
        return urlIcon1;
    }

    public void setUrlIcon1(String urlIcon1) {
        this.urlIcon1 = urlIcon1;
    }

    public String getUrlIcon2() {
        return UrlIcon2;
    }

    public void setUrlIcon2(String urlIcon2) {
        UrlIcon2 = urlIcon2;
    }

    public String getContent1() {
        return content1;
    }

    public void setContent1(String content1) {
        this.content1 = content1;
    }

    public String getContent2() {
        return content2;
    }

    public void setContent2(String content2) {
        this.content2 = content2;
    }
}
