package com.tma.stockmarket.ui.model;

import com.google.gson.annotations.SerializedName;


public class UserGetExchangeRate {

    @SerializedName("value")
    private double value;
    @SerializedName("text")
    private String text;
    @SerializedName("timestamp")
    private int timestamp;

    public double getValue() {
        return value;
    }

    public String getText() {
        return text;
    }

    public int getTimestamp() {
        return timestamp;
    }
}
