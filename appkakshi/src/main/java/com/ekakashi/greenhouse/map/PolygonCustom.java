package com.ekakashi.greenhouse.map;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Polygon;

import java.util.List;

public class PolygonCustom implements Parcelable {
    private transient Polygon mPolygon;
    private List<LatLng> mlsLatLng;
    private String mZoneId;

    public PolygonCustom() {

    }

    public PolygonCustom(Polygon polygon, List<LatLng> latLng, String zoneId) {
        this.mPolygon = polygon;
        this.mlsLatLng = latLng;
        this.mZoneId = zoneId;
    }

    public void removePolygon() {
        if (mPolygon != null) {
            mPolygon.remove();
            mPolygon = null;
        }
    }

    public void clear() {
        if (mPolygon != null) {
            mPolygon = null;
        }
        if (mlsLatLng != null && !mlsLatLng.isEmpty()) {
            mlsLatLng.clear();
        }
        if (!TextUtils.isEmpty(mZoneId)) mZoneId = null;
    }

    public void setLsLatLng(List<LatLng> mlsLatLng) {
        this.mlsLatLng = mlsLatLng;
    }

    public List<LatLng> getLsLatLng() {
        return mlsLatLng;
    }

    public void setPolygon(com.google.android.gms.maps.model.Polygon mPolygon) {
        this.mPolygon = mPolygon;
    }

    public com.google.android.gms.maps.model.Polygon getPolygon() {
        return mPolygon;
    }

    public void setZoneId(String mZoneId) {
        this.mZoneId = mZoneId;
    }

    public String getZoneId() {
        return mZoneId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.mlsLatLng);
        dest.writeString(this.mZoneId);
    }

    protected PolygonCustom(Parcel in) {
        this.mlsLatLng = in.createTypedArrayList(LatLng.CREATOR);
        this.mZoneId = in.readString();
    }

    public static final Creator<PolygonCustom> CREATOR = new Creator<PolygonCustom>() {
        @Override
        public PolygonCustom createFromParcel(Parcel source) {
            return new PolygonCustom(source);
        }

        @Override
        public PolygonCustom[] newArray(int size) {
            return new PolygonCustom[size];
        }
    };
}