package com.ekakashi.greenhouse.database.model_server_response;

import com.google.gson.annotations.SerializedName;

/**
 * Created by nbhung on 1/8/2018.
 */

public class Sys {
    @SerializedName("type")
    private int type;
    @SerializedName("id")
    private int id;
    @SerializedName("message")
    private double message;
    @SerializedName("country")
    private String country;
    @SerializedName("sunrise")
    private double sunrise;
    @SerializedName("sunset")
    private double sunset;

    public int getType() {
        return type;
    }

    public int getId() {
        return id;
    }

    public double getMessage() {
        return message;
    }

    public String getCountry() {
        return country;
    }

    public double getSunrise() {
        return sunrise;
    }

    public double getSunset() {
        return sunset;
    }
}
