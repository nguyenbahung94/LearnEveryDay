package com.ekakashi.greenhouse.map;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by paduy on 11/9/2017.
 */

public class CustomLatLngObject implements Parcelable {
    private LatLng mLatLng;
    private double x;
    private double y;
    private String markerId;

    public CustomLatLngObject(LatLng latLng, String markerId) {
        setLatLng(latLng);
        this.setX((getLatLng().longitude + 180) * 360);
        this.setY((getLatLng().latitude + 90) * 180);
        this.markerId = markerId;
    }

    public double getDistance(CustomLatLngObject latLng) {
        double dX = latLng.getX() - this.getX();
        double dY = latLng.getY() - this.getY();
        return Math.sqrt((dX * dX) + (dY * dY));
    }

    public double getSlope(CustomLatLngObject latLng) {
        double dX = latLng.getX() - this.getX();
        double dY = latLng.getY() - this.getY();
        return dY / dX;
    }

    public LatLng getLatLng() {
        return mLatLng;
    }

    private void setLatLng(LatLng mLatLng) {
        this.mLatLng = mLatLng;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    private void setY(double y) {
        this.y = y;
    }

    public String getMarkerId() {
        return markerId;
    }

    public void setMarkerId(String markerId) {
        this.markerId = markerId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelable(this.mLatLng, flags);
        dest.writeDouble(this.x);
        dest.writeDouble(this.y);
        dest.writeString(this.markerId);
    }

    protected CustomLatLngObject(Parcel in) {
        this.mLatLng = in.readParcelable(LatLng.class.getClassLoader());
        this.x = in.readDouble();
        this.y = in.readDouble();
        this.markerId = in.readString();
    }

    public static final Creator<CustomLatLngObject> CREATOR = new Creator<CustomLatLngObject>() {
        @Override
        public CustomLatLngObject createFromParcel(Parcel source) {
            return new CustomLatLngObject(source);
        }

        @Override
        public CustomLatLngObject[] newArray(int size) {
            return new CustomLatLngObject[size];
        }
    };
}
