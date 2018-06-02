package com.ekakashi.greenhouse.database.model_server_response;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;


public class myLatLong implements Parcelable {
    @SerializedName("lat")
    private double lat;
    @SerializedName("lng")
    private double lng;


    public myLatLong(double lat, double lng) {
        this.lat = lat;
        this.lng = lng;
    }

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLng() {
        return lng;
    }

    public void setLng(double lng) {
        this.lng = lng;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(this.lat);
        dest.writeDouble(this.lng);
    }

    protected myLatLong(Parcel in) {
        this.lat = in.readDouble();
        this.lng = in.readDouble();
    }

    public static final Creator<myLatLong> CREATOR = new Creator<myLatLong>() {
        @Override
        public myLatLong createFromParcel(Parcel source) {
            return new myLatLong(source);
        }

        @Override
        public myLatLong[] newArray(int size) {
            return new myLatLong[size];
        }
    };
}
