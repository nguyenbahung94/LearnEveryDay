package com.ekakashi.greenhouse.features.weather.object_weather;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ObjectDateTime {
    @SerializedName("dateTime")
    private String dateTime;

    public ObjectDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
