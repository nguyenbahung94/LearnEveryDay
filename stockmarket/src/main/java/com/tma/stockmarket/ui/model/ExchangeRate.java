package com.tma.stockmarket.ui.model;

import com.google.gson.annotations.SerializedName;


public class ExchangeRate {
    @SerializedName("symbol")
    private String symbol;
    @SerializedName("price")
    private double price;
    @SerializedName("timestamp")
    private int timestamp;

    public String getSymbol() {
        return symbol;
    }


    public double getPrice() {
        return price;
    }


    public int getTimestamp() {
        return timestamp;
    }

    @Override
    public String toString() {
        return "ExchangeRate{" +
                "symbol='" + symbol + '\'' +
                ", price=" + price +
                ", timestamp=" + timestamp +
                '}';
    }
}
